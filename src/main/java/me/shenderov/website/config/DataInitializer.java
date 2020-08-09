package me.shenderov.website.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.shenderov.website.dao.Block;
import me.shenderov.website.dao.SeoInfo;
import me.shenderov.website.dao.settings.ApplicationSettings;
import me.shenderov.website.dao.settings.EmailSettings;
import me.shenderov.website.repositories.*;
import me.shenderov.website.security.dao.Authority;
import me.shenderov.website.security.dao.AuthorityName;
import me.shenderov.website.security.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class DataInitializer {

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private SeoRepository seoRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    private ObjectMapper mapper = new ObjectMapper();
    private ClassLoader classLoader = getClass().getClassLoader();
    private final static Logger LOGGER = Logger.getLogger(DataInitializer.class.getName());

    private static final String BLOCKS_JSON = "blocks.json";
    private static final String SEO_JSON = "seo.json";
    private static final String AUTHORITIES_JSON = "authorities.json";

    @Value( "${application.data-initializer.run-on-starup}" )
    private boolean initiateOnStartup;

    @Value( "${application.data-initializer.update-from-json-on-starup}" )
    private boolean updateOnStartup;

    @PostConstruct
    public void initiateData() throws Exception {
        if(initiateOnStartup){
            LOGGER.info("Starting to run initiate methods...");
            insertDefaultBlocks();
            insertSeoData();
        }
        insertAuthorities();
        createDefaultAdmin();
        checkDefaultAdminPassword();
    }

//    private void setSettings() throws Exception {
//        //ApplicationSettings applicationSettings = (ApplicationSettings) settingsRepository.findById("application").orElseThrow(() -> new Exception("Settings entry with id: 'application' is not found"));
//        //EmailSettings emailSettings = (EmailSettings) settingsRepository.findById("email").orElseThrow(() -> new Exception("Settings entry with id: 'email' is not found"));
//        setEmailSettings(null);
//    }
//
//    private void setEmailSettings(EmailSettings emailSettings){
//        System.out.println("emailSettings");
//        //System.setProperty("application.mailer.enable-mailer", "false");
//
//    }

    private void insertAuthorities() throws IOException{
        Set <Authority> authorities = mapper.readValue(new File(Objects.requireNonNull(classLoader.getResource(AUTHORITIES_JSON)).getFile()), new TypeReference<Set<Authority>>() {});
        if (authorityRepository.count() == 0) {
            LOGGER.info(String.format("There are no authorities found in the DB. Creating from %s", AUTHORITIES_JSON));
            for(Authority authority : authorities){
                authorityRepository.save(authority);
                LOGGER.info(String.format("Authority '%s' was created", authority.getName().name()));
            }
        }
    }

    private void createDefaultAdmin() {
        if (userRepository.findUserByUsername("admin") == null) {
            LOGGER.info("Default admin does not exist in the database. Creating default admin");
            Set<Authority> adminRole = new HashSet<>();
            adminRole.add(authorityRepository.findAuthorityByName(AuthorityName.ROLE_ADMIN));
            String pass = passwordEncoder.encode("password");
            User admin = new User(1L, "admin", "Admin", pass, adminRole, true, new Date());
            userRepository.save(admin);
            LOGGER.info("Default admin is created. Default credentials are 'admin/password'. Change password ASAP");
        }
    }

    private void checkDefaultAdminPassword() {
        User admin = userRepository.findUserByUsername("admin");
        if(passwordEncoder.matches("password", admin.getPassword())){
            LOGGER.warning("User admin has default password. Should be changed ASAP");
        }
    }

    private void insertDefaultBlocks() throws IOException {
        LOGGER.info("Insert or upgrade default blocs");
        Map<String, Block> blocks = mapper.readValue(new File(Objects.requireNonNull(classLoader.getResource(BLOCKS_JSON)).getFile()), new TypeReference<Map<String, Block>>() {});
        if(blockRepository.count() > 0 && updateOnStartup){
            for(String s : blocks.keySet()){
                LOGGER.info(String.format("Block '%s' exists in the database, updating from %s", s, BLOCKS_JSON));
                blockRepository.save(blocks.get(s));
            }
        }else if (blockRepository.count() == 0) {
            for(String s : blocks.keySet()){
                LOGGER.info(String.format("Block '%s' does not exist in the DB. Inserting from %s", s, BLOCKS_JSON));
                blockRepository.insert(blocks.get(s));
            }
        }
    }

    private void insertSeoData() throws IOException {
        LOGGER.info("Insert or upgrade SEO data");
        SeoInfo seoData = mapper.readValue(new File(Objects.requireNonNull(classLoader.getResource(SEO_JSON)).getFile()), new TypeReference<SeoInfo>() {});
        if(seoRepository.count() > 0 && updateOnStartup){
            LOGGER.info(String.format("SEO data exists in the database, updating from %s", SEO_JSON));
            seoRepository.save(seoData);
        }else if (seoRepository.count() == 0) {
            LOGGER.info(String.format("SEO data does not exist in the DB. Inserting from %s", BLOCKS_JSON));
            seoRepository.insert(seoData);
        }
    }
}
