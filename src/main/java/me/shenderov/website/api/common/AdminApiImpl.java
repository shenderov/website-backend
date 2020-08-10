package me.shenderov.website.api.common;

import me.shenderov.website.dao.*;
import me.shenderov.website.entities.BlockPreview;
import me.shenderov.website.entities.MessageStatus;
import me.shenderov.website.entities.MessageStatusWrapper;
import me.shenderov.website.security.dao.Authority;
import me.shenderov.website.security.entities.NewUserWrapper;
import me.shenderov.website.security.entities.UsernameAuthorityWrapper;
import me.shenderov.website.security.entities.UsernameValueWrapper;
import me.shenderov.website.interfaces.IAdminRequestHandler;
import me.shenderov.website.security.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestMapping(path = "/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RestController
public class AdminApiImpl {

    private final static Logger LOGGER = Logger.getLogger(AdminApiImpl.class.getName());

    private final IAdminRequestHandler requestHandler;

    @Autowired
    public AdminApiImpl(IAdminRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public User addUser(@RequestBody @Valid NewUserWrapper user, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/addUser|");
        return requestHandler.addUser(user);
    }

    @RequestMapping(value = "/getUserDetails", method = RequestMethod.GET)
    public User getUserDetails(@RequestParam("username") String username, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/getUserDetails|");
        return requestHandler.getUserDetails(username);
    }

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public List<User> getAllUsers(HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/getAllUsers|");
        return requestHandler.getAllUsers();
    }

    @RequestMapping(value = "/getAuthorities", method = RequestMethod.GET)
    public List<Authority> getAuthorities(HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/getAuthorities|");
        return requestHandler.getAuthorities();
    }

    @RequestMapping(value = "/changeUserName", method = RequestMethod.POST)
    public User changeUserName(@RequestBody @Valid UsernameValueWrapper wrapper, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/changeUserName|");
        return requestHandler.changeUserName(wrapper.getUsername(), (String) wrapper.getValue());
    }

    @RequestMapping(value = "/changeUserPassword", method = RequestMethod.POST)
    public User changeUserPassword(@RequestBody @Valid UsernameValueWrapper wrapper, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/changeUserPassword|");
        return requestHandler.changeUserPassword(wrapper.getUsername(), (String) wrapper.getValue());
    }

    @RequestMapping(value = "/setUserAuthorities", method = RequestMethod.POST)
    public User setUserAuthorities(@RequestBody @Valid UsernameAuthorityWrapper wrapper, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/setUserAuthorities|");
        return requestHandler.setUserAuthorities(wrapper.getUsername(), wrapper.getAuthorities());
    }

    @RequestMapping(value = "/setUserEnabled", method = RequestMethod.POST)
    public User setUserEnabled(@RequestBody @Valid UsernameValueWrapper wrapper, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/setUserEnabled|");
        return requestHandler.setUserEnabled(wrapper.getUsername(), (Boolean) wrapper.getValue());
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public boolean deleteUser(@RequestParam("username") String username, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/deleteUser|");
        return requestHandler.deleteUser(username);
    }

    @RequestMapping(value = "/getAllBlocks", method = RequestMethod.GET)
    public Map<String, BlockPreview> getAllBlocks(HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/getAllBlocks|");
        return requestHandler.getAllBlocks();
    }

    @RequestMapping(value = "/addBlock", method = RequestMethod.POST)
    public Block addBlock(@RequestBody Block block, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/addBlock|");
        return requestHandler.addBlock(block);
    }

    @RequestMapping(value = "/updateBlock", method = RequestMethod.POST)
    public Block updateBlock(@RequestBody Block block, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/updateBlock|");
        return requestHandler.updateBlock(block);
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteBlock", method = RequestMethod.DELETE)
    public boolean deleteBlock(@RequestParam("id") String id, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/deleteBlock|");
        return requestHandler.deleteBlock(id);
    }

    @RequestMapping(value = "/addSeoInfo", method = RequestMethod.POST)
    public SeoInfo addSeoInfo(@RequestBody SeoInfo seoInfo, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/addSeoInfo|");
        return requestHandler.addSeoInfo(seoInfo);
    }

    @RequestMapping(value = "/updateSeoInfo", method = RequestMethod.POST)
    public SeoInfo updateSeoInfo(@RequestBody SeoInfo seoInfo, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/updateSeoInfo|");
        return requestHandler.updateSeoInfo(seoInfo);
    }

    @RequestMapping(value = "/deleteSeoInfo", method = RequestMethod.DELETE)
    public Boolean deleteSeoInfo(@RequestParam("id") Integer id, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/deleteSeoInfo|");
        return requestHandler.deleteSeoInfo(id);
    }

    @RequestMapping(value = "/getSeoInfo", method = RequestMethod.GET)
    public SeoInfo getSeoData(@RequestParam(name = "id") Integer id, HttpServletRequest request) throws Exception {
        LOGGER.info(request.getRemoteAddr()+"/getSeoInfo|");
        return requestHandler.getSeoInfo(id);
    }

    @RequestMapping(value = "/getAllMessages", method = RequestMethod.GET)
    public List<MessageWrapper> getAllMessages(HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/getAllMessages|");
        return requestHandler.getAllMessages();
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public MessageWrapper getMessage(@RequestParam("uuid") String uuid, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/getMessage|");
        return requestHandler.getMessage(UUID.fromString(uuid));
    }

    @RequestMapping(value = "/readMessages", method = RequestMethod.POST)
    public void readMessages(@RequestBody List<UUID> uuids, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/readMessages|");
        requestHandler.readMessages(uuids);
    }

    @RequestMapping(value = "/readAllMessages", method = RequestMethod.GET)
    public void readAllMessages(HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/readAllMessages|");
        requestHandler.readAllMessages();
    }

    @RequestMapping(value = "/readMessage", method = RequestMethod.GET)
    public MessageWrapper readMessage(@RequestParam("uuid") String uuid, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/readMessage|");
        return requestHandler.readMessage(UUID.fromString(uuid));
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteMessage", method = RequestMethod.DELETE)
    public boolean deleteMessage(@RequestParam("uuid") String uuid, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/deleteMessage|");
        return requestHandler.deleteMessage(UUID.fromString(uuid));
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteMessages", method = RequestMethod.POST)
    public boolean deleteMessages(@RequestBody List<String> uuids, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/deleteMessages|");
        List<UUID> list = uuids.stream().map(UUID::fromString).collect(Collectors.toList());
        return requestHandler.deleteMessages(list);
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteAllMessages", method = RequestMethod.DELETE)
    public boolean deleteAllMessages(HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/deleteAllMessages|");
        return requestHandler.deleteAllMessages();
    }

    @CrossOrigin
    @RequestMapping(value = "/getNewMessagesCount", method = RequestMethod.GET)
    public Integer getNewMessagesCount(HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/getNewMessagesCount|");
        return requestHandler.getNewMessagesCount();
    }
}
