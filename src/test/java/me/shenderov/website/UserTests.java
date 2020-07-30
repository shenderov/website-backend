package me.shenderov.website;

import me.shenderov.website.common.http.HttpResponse;
import me.shenderov.website.common.http.HttpResponseJson;
import me.shenderov.website.dao.SeoInfo;
import me.shenderov.website.security.entities.LoginWrapper;
import me.shenderov.website.security.dao.Authority;
import me.shenderov.website.security.dao.AuthorityName;
import me.shenderov.website.security.dao.User;
import me.shenderov.website.security.entities.NewUserWrapper;
import me.shenderov.website.security.entities.UsernameAuthorityWrapper;
import me.shenderov.website.security.entities.UsernameValueWrapper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;

public class UserTests extends WebsiteApplicationTests {

    private LoginWrapper customAdminCreds;
    private LoginWrapper customAdmin1Creds;
    private LoginWrapper userCreds;
    private LoginWrapper userAdminCreds;
    private LoginWrapper user2Creds;
    private LoginWrapper user3Creds;
    private LoginWrapper user4Creds;
    private LoginWrapper user5Creds;
    private LoginWrapper user6Creds;
    private Set<Authority> adminAuthorities;
    private Set<Authority> userAuthorities;
    private Set<Authority> userAndAdminAuthorities;
    private Set<Authority> emptyAuthorities;

    @BeforeClass
    public void setup() {
        customAdminCreds = new LoginWrapper("administrator", "password123");
        customAdmin1Creds = new LoginWrapper("john", "password123");
        userCreds = new LoginWrapper("user", "password123");
        userAdminCreds = new LoginWrapper("useradmin", "password123");
        user2Creds = new LoginWrapper("joe", "password123");
        user3Creds = new LoginWrapper("moshe", "password123");
        user4Creds = new LoginWrapper("jane", "password123");
        user5Creds = new LoginWrapper("bill", "password123");
        user6Creds = new LoginWrapper("steve", "password123");
        adminAuthorities = new HashSet<>();
        userAuthorities = new HashSet<>();
        userAndAdminAuthorities = new HashSet<>();
        emptyAuthorities = new HashSet<>();
        Authority admin = authorityRepository.findAuthorityByName(AuthorityName.ROLE_ADMIN);
        Authority user = authorityRepository.findAuthorityByName(AuthorityName.ROLE_USER);
        adminAuthorities.add(admin);
        userAuthorities.add(user);
        userAndAdminAuthorities.add(admin);
        userAndAdminAuthorities.add(user);
        userRepository.save(new User(100L, user6Creds.getUsername(), "Steve", passwordEncoder.encode(user6Creds.getPassword()), userAuthorities));
        userRepository.save(new User(101L, customAdmin1Creds.getUsername(), "John", passwordEncoder.encode(customAdmin1Creds.getPassword()), adminAuthorities));
    }

    @AfterClass
    public void teardown() {
        defaultAdminCreds = new LoginWrapper("admin", "password");
        customAdminCreds = new LoginWrapper("administrator", "password123");
        userCreds = new LoginWrapper("user", "password123");
        userRepository.deleteUserByUsername(customAdminCreds.getUsername());
        userRepository.deleteUserByUsername(customAdmin1Creds.getUsername());
        userRepository.deleteUserByUsername(userCreds.getUsername());
        userRepository.deleteUserByUsername(userAdminCreds.getUsername());
        userRepository.deleteUserByUsername(user2Creds.getUsername());
        userRepository.deleteUserByUsername(user3Creds.getUsername());
        userRepository.deleteUserByUsername(user4Creds.getUsername());
        userRepository.deleteUserByUsername(user5Creds.getUsername());
        userRepository.deleteUserByUsername(user6Creds.getUsername());
    }

    @Test
    public void addUserDuplicateDefaultAdminNegativeTest() {
        NewUserWrapper admin = new NewUserWrapper(2L, defaultAdminCreds.getUsername(),"Admin", defaultAdminCreds.getPassword(), adminAuthorities);
        HttpResponseJson response = adminRestControllerConnectorHelper.addUserNegative(defaultAdminCreds, admin).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "Cannot create a user. Username 'admin' is reserved.");
    }

    @Test
    public void addUserCustomAdminTest() {
        NewUserWrapper admin = new NewUserWrapper(3L, customAdminCreds.getUsername(),"Admin", customAdminCreds.getPassword(), adminAuthorities);
        HttpResponse response = adminRestControllerConnectorHelper.addUser(defaultAdminCreds, admin);
        User user = (User) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(user.getName(), admin.getName());
        assertEquals(user.getUsername(), customAdminCreds.getUsername());
        assertNull(user.getPassword());
        assertTrue(user.getAuthorities().containsAll(adminAuthorities));
        assertTrue(userRepository.existsByUsername(customAdminCreds.getUsername()));
    }

    @Test
    public void addUserTest() {
        NewUserWrapper newUser = new NewUserWrapper(4L, userCreds.getUsername(),"User", userCreds.getPassword(), userAuthorities);
        HttpResponse response = adminRestControllerConnectorHelper.addUser(defaultAdminCreds, newUser);
        User user = (User) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(user.getName(), newUser.getName());
        assertEquals(user.getUsername(), userCreds.getUsername());
        assertNull(user.getPassword());
        assertTrue(user.getAuthorities().containsAll(userAuthorities));
        assertTrue(userRepository.existsByUsername(userCreds.getUsername()));
    }

    @Test
    public void addUserAdminAndUserRoleTest() {
        NewUserWrapper newUser = new NewUserWrapper(5L, userAdminCreds.getUsername(),"User Admin", userAdminCreds.getPassword(), userAndAdminAuthorities);
        HttpResponse response = adminRestControllerConnectorHelper.addUser(defaultAdminCreds, newUser);
        User user = (User) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(user.getName(), newUser.getName());
        assertEquals(user.getUsername(), userAdminCreds.getUsername());
        assertNull(user.getPassword());
        assertTrue(user.getAuthorities().containsAll(userAndAdminAuthorities));
        assertTrue(userRepository.existsByUsername(userAdminCreds.getUsername()));
    }

    @Test
    public void addUserWithoutIdNegativeTest() {
        NewUserWrapper newUser = new NewUserWrapper(null, user2Creds.getUsername(),"User", user2Creds.getPassword(), userAuthorities);
        HttpResponseJson response = adminRestControllerConnectorHelper.addUserNegative(defaultAdminCreds, newUser).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Validation failed for object='newUserWrapper'. Error count: 1");
        assertFalse(userRepository.existsByUsername(user2Creds.getUsername()));
    }

    @Test
    public void addUserWithoutUsernameNegativeTest() {
        NewUserWrapper newUser = new NewUserWrapper(6L, null,"User", user2Creds.getPassword(), userAuthorities);
        HttpResponseJson response = adminRestControllerConnectorHelper.addUserNegative(defaultAdminCreds, newUser).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Validation failed for object='newUserWrapper'. Error count: 1");
        assertFalse(userRepository.existsByUsername(user2Creds.getUsername()));
    }

    @Test
    public void addUserWithoutNameNegativeTest() {
        NewUserWrapper newUser = new NewUserWrapper(6L, user2Creds.getUsername(),null, user2Creds.getPassword(), userAuthorities);
        HttpResponseJson response = adminRestControllerConnectorHelper.addUserNegative(defaultAdminCreds, newUser).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Validation failed for object='newUserWrapper'. Error count: 1");
        assertFalse(userRepository.existsByUsername(user2Creds.getUsername()));
    }

    @Test
    public void addUserWithoutPasswordNegativeTest() {
        NewUserWrapper newUser = new NewUserWrapper(6L, user2Creds.getUsername(),"User", null, userAuthorities);
        HttpResponseJson response = adminRestControllerConnectorHelper.addUserNegative(defaultAdminCreds, newUser).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Validation failed for object='newUserWrapper'. Error count: 1");
        assertFalse(userRepository.existsByUsername(user2Creds.getUsername()));
    }

    @Test
    public void addUserWithoutAuthoritiesNegativeTest() {
        NewUserWrapper newUser = new NewUserWrapper(6L, user2Creds.getUsername(),"User", user2Creds.getPassword(), null);
        HttpResponseJson response = adminRestControllerConnectorHelper.addUserNegative(defaultAdminCreds, newUser).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Validation failed for object='newUserWrapper'. Error count: 1");
        assertFalse(userRepository.existsByUsername(user2Creds.getUsername()));
    }

    @Test(dependsOnMethods = "addUserTest")
    public void addUserNotUniqueIdNegativeTest() {
        NewUserWrapper newUser = new NewUserWrapper(4L, user3Creds.getUsername(),"User", user3Creds.getPassword(), userAuthorities);
        HttpResponseJson response = adminRestControllerConnectorHelper.addUserNegative(defaultAdminCreds, newUser).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertTrue(response.getObject().get("message").getAsString().contains("duplicate key error collection"));
        assertFalse(userRepository.existsByUsername(user3Creds.getUsername()));
    }

    @Test(dependsOnMethods = "addUserTest")
    public void addUserNotUniqueUsernameNegativeTest() {
        NewUserWrapper newUser = new NewUserWrapper(7L, userCreds.getUsername(),"User", userCreds.getPassword(), userAuthorities);
        HttpResponseJson response = adminRestControllerConnectorHelper.addUserNegative(defaultAdminCreds, newUser).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertTrue(response.getObject().get("message").getAsString().contains("duplicate key error collection"));
        assertFalse(userRepository.existsByUsername(user2Creds.getUsername()));
    }

    @Test
    public void addUserAsRoleUserNegativeTest() {
        NewUserWrapper newUser = new NewUserWrapper(8L, user4Creds.getUsername(),"User", user4Creds.getPassword(), userAuthorities);
        HttpResponse response = adminRestControllerConnectorHelper.addUserNegative(userCreds, newUser);
        assertEquals(response.getHttpStatusCode(), 401);
        assertFalse(userRepository.existsByUsername(user4Creds.getUsername()));
    }

    @Test
    public void addUserAsUnauthorizedNegativeTest() {
        NewUserWrapper newUser = new NewUserWrapper(9L, user5Creds.getUsername(),"User", user5Creds.getPassword(), userAuthorities);
        HttpResponseJson response = adminRestControllerConnectorHelper.addUserNegative(newUser).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertTrue(response.getObject().get("message").getAsString().contains("Unauthorized"));
        assertFalse(userRepository.existsByUsername(user5Creds.getUsername()));
    }

    @Test
    public void getUserDetailsDefaultAdminTest() {
        assertTrue(userRepository.existsByUsername(defaultAdminCreds.getUsername()));
        HttpResponse response = adminRestControllerConnectorHelper.getUserDetails(defaultAdminCreds, defaultAdminCreds.getUsername());
        User user = (User) response.getObject();
        User userDb = userRepository.findUserByUsername(defaultAdminCreds.getUsername());
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(user, userDb);
    }

    @Test(dependsOnMethods = "addUserTest")
    public void getUserDetailsTest() {
        HttpResponse response = adminRestControllerConnectorHelper.getUserDetails(defaultAdminCreds, userCreds.getUsername());
        User user = (User) response.getObject();
        User userDb = userRepository.findUserByUsername(userCreds.getUsername());
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(user, userDb);
    }

    @Test
    public void getUserDetailsNotExistingUserNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getUserDetailsNegative(defaultAdminCreds, "blabla").convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "User 'blabla' not found");
    }

    @Test(dependsOnMethods = "addUserTest")
    public void getUserDetailsAsNonAdminUserNegativeTest() {
        HttpResponse response = adminRestControllerConnectorHelper.getUserDetailsNegative(userCreds, userCreds.getUsername());
        assertEquals(response.getHttpStatusCode(), 403);
    }

    @Test(dependsOnMethods = "addUserTest")
    public void getUserDetailsAsUnauthorizedUserNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getUserDetailsNegative(userCreds.getUsername()).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertTrue(response.getObject().get("message").getAsString().contains("Unauthorized"));
    }

    @Test(dependsOnMethods = "addUserCustomAdminTest")
    public void changeUserNameTest() {
        UsernameValueWrapper wrapper = new UsernameValueWrapper(customAdminCreds.getUsername(), "Custom Admin");
        HttpResponse response = adminRestControllerConnectorHelper.changeUserName(customAdminCreds, wrapper);
        User user = (User) response.getObject();
        User userDb = userRepository.findUserByUsername(customAdminCreds.getUsername());
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(user.getName(), "Custom Admin");
        assertEquals(userDb.getName(), "Custom Admin");
    }

    @Test
    public void changeUserNameNotExistingUserNegativeTest() {
        UsernameValueWrapper wrapper = new UsernameValueWrapper("blabla", "Name");
        HttpResponseJson response = adminRestControllerConnectorHelper.changeUserNameNegative(defaultAdminCreds, wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "User 'blabla' not found");
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changeUserNameAsNonAdminUserNegativeTest() {
        UsernameValueWrapper wrapper = new UsernameValueWrapper(userCreds.getUsername(), "Custom User");
        HttpResponse response = adminRestControllerConnectorHelper.changeUserNameNegative(userCreds, wrapper);
        assertEquals(response.getHttpStatusCode(), 403);
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changeUserNameAsUnauthorizedUserNegativeTest() {
        UsernameValueWrapper wrapper = new UsernameValueWrapper(userCreds.getUsername(), "Custom User");
        HttpResponseJson response = adminRestControllerConnectorHelper.changeUserNameNegative(wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertTrue(response.getObject().get("message").getAsString().contains("Unauthorized"));
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changeUserNameToNullNegativeTest() {
        UsernameValueWrapper wrapper = new UsernameValueWrapper(userCreds.getUsername(), null);
        HttpResponseJson response = adminRestControllerConnectorHelper.changeUserNameNegative(defaultAdminCreds, wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Validation failed for object='usernameValueWrapper'. Error count: 1");
    }

    @Test(dependsOnMethods = "addUserCustomAdminTest")
    public void changeUserNameToTheSameNameTest() {
        User userDbBefore = userRepository.findUserByUsername(customAdminCreds.getUsername());
        UsernameValueWrapper wrapper = new UsernameValueWrapper(customAdminCreds.getUsername(), userDbBefore.getName());
        HttpResponse response = adminRestControllerConnectorHelper.changeUserName(customAdminCreds, wrapper);
        User user = (User) response.getObject();
        User userDbAfter = userRepository.findUserByUsername(customAdminCreds.getUsername());
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(userDbBefore.getName(), user.getName());
        assertEquals(userDbBefore.getName(), userDbAfter.getName());
    }

    @Test(dependsOnMethods = "addUserCustomAdminTest")
    public void changePasswordTest() {
        String newPassword = "newPassword";
        User userDbBefore = userRepository.findUserByUsername(customAdminCreds.getUsername());
        assertFalse(passwordEncoder.matches(newPassword, userDbBefore.getPassword()));
        UsernameValueWrapper wrapper = new UsernameValueWrapper(customAdminCreds.getUsername(), newPassword);
        HttpResponse response = adminRestControllerConnectorHelper.changeUserPassword(customAdminCreds, wrapper);
        User user = (User) response.getObject();
        User userDbAfter = userRepository.findUserByUsername(customAdminCreds.getUsername());
        assertEquals(response.getHttpStatusCode(), 200);
        assertNull(user.getPassword());
        assertTrue(passwordEncoder.matches(newPassword, userDbAfter.getPassword()));
        userRepository.save(userDbBefore);
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changePasswordAsNonAdminUserNegativeTest() {
        String newPassword = "newPassword";
        UsernameValueWrapper wrapper = new UsernameValueWrapper(userCreds.getUsername(), newPassword);
        HttpResponse response = adminRestControllerConnectorHelper.changeUserPasswordNegative(userCreds, wrapper);
        assertEquals(response.getHttpStatusCode(), 403);
    }

    @Test
    public void changePasswordOfNotExistingNegativeTest() {
        UsernameValueWrapper wrapper = new UsernameValueWrapper("blabla", "password");
        HttpResponseJson response = adminRestControllerConnectorHelper.changeUserPasswordNegative(defaultAdminCreds, wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "User 'blabla' not found");
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changePasswordAsUnauthorizedUserNegativeTest() {
        String newPassword = "newPassword";
        UsernameValueWrapper wrapper = new UsernameValueWrapper(userCreds.getUsername(), newPassword);
        HttpResponseJson response = adminRestControllerConnectorHelper.changeUserPasswordNegative(wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertTrue(response.getObject().get("message").getAsString().contains("Unauthorized"));
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changePasswordToNullNegativeTest() {
        UsernameValueWrapper wrapper = new UsernameValueWrapper(userCreds.getUsername(), null);
        HttpResponseJson response = adminRestControllerConnectorHelper.changeUserPasswordNegative(defaultAdminCreds, wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Validation failed for object='usernameValueWrapper'. Error count: 1");
    }

    @Test(dependsOnMethods = "changePasswordTest")
    public void changePasswordToTheSameTest() {
        User userDbBefore = userRepository.findUserByUsername(customAdminCreds.getUsername());
        UsernameValueWrapper wrapper = new UsernameValueWrapper(customAdminCreds.getUsername(), customAdminCreds.getPassword());
        HttpResponse response = adminRestControllerConnectorHelper.changeUserPassword(customAdminCreds, wrapper);
        User user = (User) response.getObject();
        User userDbAfter = userRepository.findUserByUsername(customAdminCreds.getUsername());
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(passwordEncoder.matches(customAdminCreds.getPassword(), userDbAfter.getPassword()));
        assertEquals(userDbBefore.getLastPasswordResetDate(), user.getLastPasswordResetDate());
        assertEquals(userDbBefore.getLastPasswordResetDate(), userDbAfter.getLastPasswordResetDate());
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changeAuthoritiesTest() {
        User userDbBefore = userRepository.findUserByUsername(userCreds.getUsername());
        UsernameAuthorityWrapper wrapper = new UsernameAuthorityWrapper(userCreds.getUsername(), userAndAdminAuthorities);
        HttpResponse response = adminRestControllerConnectorHelper.changeUserAuthorities(defaultAdminCreds, wrapper);
        User user = (User) response.getObject();
        User userDbAfter = userRepository.findUserByUsername(userCreds.getUsername());
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(user.getAuthorities(), userAndAdminAuthorities);
        assertEquals(userDbAfter.getAuthorities(), userAndAdminAuthorities);
        userRepository.save(userDbBefore);
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changeAuthoritiesAsNonAdminUserNegativeTest() {
        UsernameAuthorityWrapper wrapper = new UsernameAuthorityWrapper(userCreds.getUsername(), userAndAdminAuthorities);
        HttpResponse response = adminRestControllerConnectorHelper.changeUserAuthoritiesNegative(userCreds, wrapper);
        assertEquals(response.getHttpStatusCode(), 403);
    }

    @Test
    public void changeAuthoritiesFogNotExistingUserNegativeTest() {
        UsernameAuthorityWrapper wrapper = new UsernameAuthorityWrapper("blabla", userAndAdminAuthorities);
        HttpResponseJson response = adminRestControllerConnectorHelper.changeUserAuthoritiesNegative(defaultAdminCreds, wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "User 'blabla' not found");
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changeAuthoritiesAsUnauthorizedUserNegativeTest() {
        UsernameAuthorityWrapper wrapper = new UsernameAuthorityWrapper(userCreds.getUsername(), userAndAdminAuthorities);
        HttpResponseJson response = adminRestControllerConnectorHelper.changeUserAuthoritiesNegative(wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertTrue(response.getObject().get("message").getAsString().contains("Unauthorized"));
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changeAuthoritiesToNullNegativeTest() {
        UsernameAuthorityWrapper wrapper = new UsernameAuthorityWrapper(userCreds.getUsername(), null);
        HttpResponseJson response = adminRestControllerConnectorHelper.changeUserAuthoritiesNegative(defaultAdminCreds, wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Validation failed for object='usernameAuthorityWrapper'. Error count: 1");
    }

    @Test(dependsOnMethods = "changePasswordTest")
    public void changeAuthoritiesToTheSameTest() {
        User userDbBefore = userRepository.findUserByUsername(customAdminCreds.getUsername());
        UsernameAuthorityWrapper wrapper = new UsernameAuthorityWrapper(customAdminCreds.getUsername(), userDbBefore.getAuthorities());
        HttpResponse response = adminRestControllerConnectorHelper.changeUserAuthorities(customAdminCreds, wrapper);
        User user = (User) response.getObject();
        User userDbAfter = userRepository.findUserByUsername(customAdminCreds.getUsername());
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(userDbBefore.getAuthorities(), user.getAuthorities());
        assertEquals(userDbBefore.getAuthorities(), userDbAfter.getAuthorities());
    }

    @Test
    public void changeAuthoritiesForDefaultAdminNegativeTest() {
        UsernameAuthorityWrapper wrapper = new UsernameAuthorityWrapper(defaultAdminCreds.getUsername(), userAuthorities);
        HttpResponseJson response = adminRestControllerConnectorHelper.changeUserAuthoritiesNegative(defaultAdminCreds, wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "Cannot change authorities for default admin user");
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changeIsEnabledTest() {
        User userDbBefore = userRepository.findUserByUsername(userCreds.getUsername());
        UsernameValueWrapper wrapper = new UsernameValueWrapper(userCreds.getUsername(), false);
        HttpResponse response = adminRestControllerConnectorHelper.setUserEnabled(defaultAdminCreds, wrapper);
        User user = (User) response.getObject();
        User userDbAfter = userRepository.findUserByUsername(userCreds.getUsername());
        assertEquals(response.getHttpStatusCode(), 200);
        assertFalse(user.isEnabled());
        assertFalse(userDbAfter.isEnabled());
        userRepository.save(userDbBefore);
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changeIsEnabledAsNonAdminNegativeTest() {
        UsernameValueWrapper wrapper = new UsernameValueWrapper(userCreds.getUsername(), false);
        HttpResponse response = adminRestControllerConnectorHelper.setUserEnabledNegative(userCreds, wrapper);
        assertEquals(response.getHttpStatusCode(), 403);
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changeIsEnabledAsUnauthorizedNegativeTest() {
        UsernameValueWrapper wrapper = new UsernameValueWrapper(userCreds.getUsername(), false);
        HttpResponseJson response = adminRestControllerConnectorHelper.setUserEnabledNegative(wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertTrue(response.getObject().get("message").getAsString().contains("Unauthorized"));
    }

    @Test(dependsOnMethods = "addUserTest")
    public void changeIsEnabledToNullNegativeTest() {
        UsernameValueWrapper wrapper = new UsernameValueWrapper(userCreds.getUsername(), null);
        HttpResponseJson response = adminRestControllerConnectorHelper.setUserEnabledNegative(defaultAdminCreds, wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Validation failed for object='usernameValueWrapper'. Error count: 1");
    }

    @Test(dependsOnMethods = "changeAuthoritiesToTheSameTest")
    public void changeIsEnabledToTheSameTest() {
        User userDbBefore = userRepository.findUserByUsername(customAdminCreds.getUsername());
        UsernameValueWrapper wrapper = new UsernameValueWrapper(customAdminCreds.getUsername(), userDbBefore.isEnabled());
        HttpResponse response = adminRestControllerConnectorHelper.setUserEnabled(customAdminCreds, wrapper);
        User user = (User) response.getObject();
        User userDbAfter = userRepository.findUserByUsername(customAdminCreds.getUsername());
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(userDbBefore.isEnabled(), user.isEnabled());
        assertEquals(userDbBefore.isEnabled(), userDbAfter.isEnabled());
    }

    @Test
    public void changeIsEnabledDefaultAdminNegativeTest() {
        UsernameValueWrapper wrapper = new UsernameValueWrapper(defaultAdminCreds.getUsername(), false);
        HttpResponseJson response = adminRestControllerConnectorHelper.setUserEnabledNegative(defaultAdminCreds, wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "Cannot change isEnabled for default admin user");
    }

    @Test
    public void changeIsEnabledNotExistingUserNegativeTest() {
        UsernameValueWrapper wrapper = new UsernameValueWrapper("blabla", false);
        HttpResponseJson response = adminRestControllerConnectorHelper.setUserEnabledNegative(defaultAdminCreds, wrapper).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "User 'blabla' not found");
    }

    @Test
    public void deleteUserAsUnauthorizedNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteUserNegative(user6Creds.getUsername()).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertTrue(response.getObject().get("message").getAsString().contains("Unauthorized"));
    }

    @Test
    public void deleteUserDefaultAdminNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteUserNegative(defaultAdminCreds, defaultAdminCreds.getUsername()).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "Cannot delete default admin user");
    }

    @Test
    public void deleteUserAsNonAdminNegativeTest() {
        HttpResponse response = adminRestControllerConnectorHelper.deleteUserNegative(userCreds, user6Creds.getUsername());
        assertEquals(response.getHttpStatusCode(), 403);
    }

    @Test
    public void deleteUserTest() {
        assertTrue(userRepository.existsByUsername(customAdmin1Creds.getUsername()));
        adminRestControllerConnectorHelper.getUserDetails(customAdmin1Creds, customAdmin1Creds.getUsername());
        HttpResponse response = adminRestControllerConnectorHelper.deleteUser(defaultAdminCreds, customAdmin1Creds.getUsername());
        HttpResponse response1 = adminRestControllerConnectorHelper.getUserDetailsNegative(customAdmin1Creds, customAdmin1Creds.getUsername());
        Boolean res = (Boolean) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(res);
        assertFalse(userRepository.existsByUsername(customAdmin1Creds.getUsername()));
        assertEquals(response1.getHttpStatusCode(), 401);
    }

    @Test(dependsOnMethods = "deleteUserTest")
    public void cannotLoginWithDeletedUserNegativeTest() {
        HttpResponse response = adminRestControllerConnectorHelper.getUserDetailsNegative(customAdmin1Creds, user6Creds.getUsername());
        assertEquals(response.getHttpStatusCode(), 401);
    }

    @Test
    public void deleteNotExistingUserNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteUserNegative(defaultAdminCreds, "blabla").convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "User 'blabla' not found");
    }

    @Test
    public void publicApiCallAsAdminTest() {
        HttpResponse response = adminRestControllerConnectorHelper.getSeoDataPublic(defaultAdminCreds);
        SeoInfo seoInfo = (SeoInfo) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertNotNull(seoInfo);
    }

    @Test(dependsOnMethods = "addUserTest")
    public void publicApiCallAsUserTest() {
        HttpResponse response = adminRestControllerConnectorHelper.getSeoDataPublic(userCreds);
        SeoInfo seoInfo = (SeoInfo) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertNotNull(seoInfo);
    }

}
