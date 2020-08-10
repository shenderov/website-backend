package me.shenderov.website.handlers;

import me.shenderov.website.dao.Block;
import me.shenderov.website.dao.MessageWrapper;
import me.shenderov.website.dao.SeoInfo;
import me.shenderov.website.entities.BlockPreview;
import me.shenderov.website.entities.MessageDeliveryStatus;
import me.shenderov.website.entities.MessageStatus;
import me.shenderov.website.helpers.SequenceGeneratorService;
import me.shenderov.website.interfaces.IAdminRequestHandler;
import me.shenderov.website.repositories.*;
import me.shenderov.website.security.dao.Authority;
import me.shenderov.website.security.dao.User;
import me.shenderov.website.security.entities.NewUserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.logging.Logger;

public class AdminRequestHandler implements IAdminRequestHandler {

    private final static Logger LOGGER = Logger.getLogger(AdminRequestHandler.class.getName());

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeoRepository seoRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected SequenceGeneratorService sequenceGenerator;

    public User addUser(NewUserWrapper user) {
        if(!user.getUsername().equals("admin")){
            LOGGER.info(String.format("Creating user %s", user.getUsername()));
            User u = new User(sequenceGenerator.generateSequence(User.SEQUENCE_NAME), user.getUsername(), user.getName(), passwordEncoder.encode(user.getPassword()), user.getAuthorities(), true, new Date());
            return userRepository.insert(u);
        }else {
            LOGGER.warning("Cannot create a user. Username 'admin' is reserved.");
            throw new RuntimeException("Cannot create a user. Username 'admin' is reserved.");
        }
    }

    public User getUserDetails(String username) {
        if(!userRepository.existsByUsername(username)){
            LOGGER.info(String.format("User '%s' not found", username));
            throw new RuntimeException(String.format("User '%s' not found", username));
        }
        return userRepository.findUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Authority> getAuthorities() {
        return authorityRepository.findAll();
    }

    public User changeUserName(String username, String newName) {
        if(!userRepository.existsByUsername(username)){
            LOGGER.info(String.format("User '%s' not found", username));
            throw new RuntimeException(String.format("User '%s' not found", username));
        }
        User user = userRepository.findUserByUsername(username);
        if(!user.getName().equals(newName)){
            user.setName(newName);
            return userRepository.save(user);
        }else {
            LOGGER.info(String.format("Nothing to change for user %s", user.getUsername()));
            return user;
        }
    }

    public User changeUserPassword(String username, String password) {
        if(!userRepository.existsByUsername(username)){
            LOGGER.info(String.format("User '%s' not found", username));
            throw new RuntimeException(String.format("User '%s' not found", username));
        }
        User user = userRepository.findUserByUsername(username);
        if(!passwordEncoder.matches(password, user.getPassword())){
            user.setPassword(passwordEncoder.encode(password));
            user.setLastPasswordResetDate(new Date());
            return userRepository.save(user);
        }else {
            LOGGER.info(String.format("Nothing to change for user %s", user.getUsername()));
            return user;
        }
    }

    public User setUserAuthorities(String username, Set<Authority> authorities) {
        if(!userRepository.existsByUsername(username)){
            LOGGER.info(String.format("User '%s' not found", username));
            throw new RuntimeException(String.format("User '%s' not found", username));
        }
        if(username.equals("admin")){
            LOGGER.info("Cannot change authorities for default admin user");
            throw new RuntimeException("Cannot change authorities for default admin user");
        }
        User user = userRepository.findUserByUsername(username);
        if(user.getAuthorities().size() != authorities.size() || !user.getAuthorities().containsAll(authorities)){
            user.setAuthorities(authorities);
            return userRepository.save(user);
        } else {
            LOGGER.info(String.format("Nothing to change for user %s", user.getUsername()));
            return user;
        }
    }

    public User setUserEnabled(String username, Boolean isEnabled) {
        if(!userRepository.existsByUsername(username)){
            LOGGER.info(String.format("User '%s' not found", username));
            throw new RuntimeException(String.format("User '%s' not found", username));
        }
        if(username.equals("admin")){
            LOGGER.info("Cannot change isEnabled for default admin user");
            throw new RuntimeException("Cannot change isEnabled for default admin user");
        }
        User user = userRepository.findUserByUsername(username);
        if(user.isEnabled() != isEnabled){
            user.setEnabled(isEnabled);
            return userRepository.save(user);
        }else {
            LOGGER.info(String.format("Nothing to change for user %s", user.getUsername()));
            return user;
        }
    }

    public Boolean deleteUser(String username) {
        if(!userRepository.existsByUsername(username)){
            LOGGER.info(String.format("User '%s' not found", username));
            throw new RuntimeException(String.format("User '%s' not found", username));
        }
        if(username.equals("admin")){
            LOGGER.info("Cannot delete default admin user");
            throw new RuntimeException("Cannot delete default admin user");
        }
            userRepository.deleteUserByUsername(username);
            return !userRepository.existsByUsername(username);
    }

    public Map<String, BlockPreview> getAllBlocks() {
        Map<String, BlockPreview> blocks = new HashMap<>();
        for(Block b : blockRepository.findAll()){
            BlockPreview preview = new BlockPreview();
            preview.setId(b.getId());
            preview.setPosition(b.getPosition());
            preview.setTitle(b.getTitle());
            blocks.put(b.getId(), preview);
        }
        return blocks;
    }

    @CachePut("blocks")
    public Block addBlock(Block block) {
        if(!blockRepository.existsById(block.getId())){
            return blockRepository.insert(block);
        }else {
            LOGGER.info(String.format("Block with id '%s' already exist", block.getId()));
            throw new RuntimeException(String.format("Block with id '%s' already exist", block.getId()));
        }
    }

    @CacheEvict("blocks")
    public Block updateBlock(Block block) {
        if(blockRepository.existsById(block.getId())){
            return blockRepository.save(block);
        }else {
            LOGGER.info(String.format("Block with id '%s' does not exist", block.getId()));
            throw new RuntimeException(String.format("Block with id '%s' does not exist", block.getId()));
        }
    }

    @CacheEvict("blocks")
    public Boolean deleteBlock(String id) {
        if(blockRepository.existsById(id)){
            blockRepository.deleteById(id);
            return !blockRepository.existsById(id);
        }else {
            LOGGER.info(String.format("Block with id '%s' does not exist", id));
            throw new RuntimeException(String.format("Block with id '%s' does not exist", id));
        }
    }

    @CachePut("seo")
    public SeoInfo addSeoInfo(SeoInfo seoInfo) {
        if(!seoRepository.existsById(seoInfo.getId())){
            return seoRepository.insert(seoInfo);
        }else {
            LOGGER.info(String.format("SEO info with id '%d' already exist", seoInfo.getId()));
            throw new RuntimeException(String.format("SEO info with id '%d' already exist", seoInfo.getId()));
        }
    }

    @CacheEvict("seo")
    public SeoInfo updateSeoInfo(SeoInfo seoInfo) {
        if(seoRepository.existsById(seoInfo.getId())){
            return seoRepository.save(seoInfo);
        }else {
            LOGGER.info(String.format("SEO info with id '%d' does not exist", seoInfo.getId()));
            throw new RuntimeException(String.format("SEO info with id '%d' does not exist", seoInfo.getId()));
        }
    }

    @CacheEvict("seo")
    public Boolean deleteSeoInfo(Integer id) {
        if(seoRepository.existsById(id)){
            seoRepository.deleteById(id);
            return !seoRepository.existsById(id);
        }else {
            LOGGER.info(String.format("SEO info with id '%d' does not exist", id));
            throw new RuntimeException(String.format("SEO info with id '%d' does not exist", id));
        }
    }

    @Cacheable({"seo"})
    public SeoInfo getSeoInfo(Integer id) throws Exception {
        return seoRepository.findById(id).orElseThrow(() -> new Exception(String.format("Seo Data with id: %d is not found", id)));
    }

    @Cacheable({"messages"})
    public List<MessageWrapper> getAllMessages() {
        return messageRepository.findAll();
    }

    @CacheEvict(value = {"messages"}, beforeInvocation = true)
    public MessageWrapper getMessage(UUID uuid) {
        MessageWrapper res = messageRepository.findById(uuid).orElseThrow(() -> new RuntimeException(String.format("Message with uuid '%s' not found", uuid.toString())));
        if(res.getStatus().equals(MessageStatus.NEW) || res.getStatus().equals(MessageStatus.UNREAD)){
            readMessage(uuid);
        }
        return res;
    }

    @CacheEvict(value = {"messages"}, beforeInvocation = true)
    public void readMessages(List<UUID> uuids){
        for(UUID uuid : uuids){
            MessageWrapper wrapper = messageRepository.findById(uuid).orElseThrow(() -> new RuntimeException(String.format("Message with uuid '%s' not found", uuid.toString())));
            if(wrapper.getStatus() == MessageStatus.NEW){
                wrapper.setStatus(MessageStatus.READ);
                messageRepository.save(wrapper);
            }
        }
    }

    @CacheEvict(value = {"messages"}, beforeInvocation = true)
    public void readAllMessages(){
        List<MessageDeliveryStatus> statuses = new ArrayList<>();
        statuses.add(MessageDeliveryStatus.NEW);
        List<MessageWrapper> messages = messageRepository.findByDeliveryStatusIsIn(statuses);
        for(MessageWrapper wrapper : messages){
            wrapper.setStatus(MessageStatus.READ);
            messageRepository.save(wrapper);
        }
    }

    @CacheEvict(value = {"messages"}, beforeInvocation = true)
    public MessageWrapper readMessage(UUID uuid){
        MessageWrapper wrapper = messageRepository.findById(uuid).orElseThrow(() -> new RuntimeException(String.format("Message with uuid '%s' not found", uuid.toString())));
        wrapper.setStatus(MessageStatus.READ);
        messageRepository.save(wrapper);
        return messageRepository.findById(uuid).orElseThrow(() -> new RuntimeException(String.format("Message with uuid '%s' not found", uuid.toString())));
    }

    @CacheEvict(value = {"messages"}, beforeInvocation = true)
    public Boolean deleteMessage(UUID uuid) {
        if(messageRepository.existsById(uuid)){
            messageRepository.deleteById(uuid);
            return !messageRepository.existsById(uuid);
        }else {
            LOGGER.info(String.format("Message with uuid '%s' not found", uuid));
            throw new RuntimeException(String.format("Message with uuid '%s' not found", uuid));
        }
    }

    @CacheEvict(value = {"messages"}, beforeInvocation = true)
    public Boolean deleteMessages(List<UUID> uuids) {
        List<String> res = new LinkedList<>();
        for(UUID uuid : uuids){
            boolean b = true;
            try {
                b = deleteMessage(uuid);
            } catch (Exception e) {
                b = false;
            } finally {
                if(!b)
                    res.add(uuid.toString());
            }
        }
        if(res.size() > 0){
            String failedUuids = String.join(", ", res);
            LOGGER.info(String.format("Following messages weren't deleted: '%s'", failedUuids));
        }
        return res.size() == 0;
    }

    @CacheEvict(value = {"messages"}, allEntries = true, beforeInvocation = true)
    public Boolean deleteAllMessages() {
        messageRepository.deleteAll();
        return messageRepository.count() == 0;
    }

    @Cacheable(value = {"messages"})
    public Integer getNewMessagesCount() {
        return messageRepository.countAllByStatus(MessageStatus.NEW);
    }
}
