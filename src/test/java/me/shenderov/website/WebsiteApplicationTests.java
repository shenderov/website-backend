package me.shenderov.website;

import com.mongodb.annotations.NotThreadSafe;
import com.mongodb.annotations.ThreadSafe;
import me.shenderov.website.common.TestConfiguration;
import me.shenderov.website.common.http.AdminRestControllerConnectorHelper;
import me.shenderov.website.common.http.PublicRestControllerConnectorHelper;
import me.shenderov.website.config.ApplicationConfig;
import me.shenderov.website.repositories.*;
import me.shenderov.website.security.dao.Authority;
import me.shenderov.website.security.dao.AuthorityName;
import me.shenderov.website.security.dao.User;
import me.shenderov.website.security.entities.LoginWrapper;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
@ContextConfiguration(classes = {WebsiteApplication.class, ApplicationConfig.class, TestConfiguration.class})
@TestPropertySource(locations="classpath:test.properties")
public abstract class WebsiteApplicationTests extends AbstractTestNGSpringContextTests {

	@LocalServerPort
	protected int port = 0;

	@Value(value = "${test.env.hostname}")
	protected String hostname;

	@Value(value = "${test.env.base_uri}")
	protected String baseUri;

	@Autowired
	protected BlockRepository blockRepository;

	@Autowired
	protected SeoRepository seoRepository;

	@Autowired
	protected AuthorityRepository authorityRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected MessageRepository messageRepository;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	protected AdminRestControllerConnectorHelper adminRestControllerConnectorHelper;
	protected PublicRestControllerConnectorHelper publicRestControllerConnectorHelper;

	protected LoginWrapper defaultAdminCreds;
	protected LoginWrapper nonAdminUserCreds;

	@BeforeClass
	public void init() {
		defaultAdminCreds = new LoginWrapper("admin", "password");
		nonAdminUserCreds = new LoginWrapper("nonadmin", "password");
		if(!userRepository.existsById(200L)){
			Set<Authority> authorities = new HashSet<>();
			authorities.add(authorityRepository.findAuthorityByName(AuthorityName.ROLE_USER));
			userRepository.insert(new User(200L, nonAdminUserCreds.getUsername(), "Block User", passwordEncoder.encode(nonAdminUserCreds.getPassword()), authorities));
		}
		adminRestControllerConnectorHelper = new AdminRestControllerConnectorHelper(hostname, port, baseUri);
		publicRestControllerConnectorHelper = new PublicRestControllerConnectorHelper(hostname, port, baseUri);
	}

	@AfterClass
	public void cleanup() {
		userRepository.deleteUserByUsername(nonAdminUserCreds.getUsername());
	}

}
