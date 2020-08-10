package me.shenderov.website;

import me.shenderov.website.common.http.HttpResponse;
import me.shenderov.website.common.http.HttpResponseJson;
import me.shenderov.website.dao.MessageWrapper;
import me.shenderov.website.entities.Message;
import me.shenderov.website.entities.MessageStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

public class MessageTests extends WebsiteApplicationTests {

    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;
    private Message message6;
    private Message message7;
    private Message message8;

    @BeforeClass
    public void setup() {
        message1 = new Message();
        message1.setEmail("kostya@company.com");
        message1.setMessage(UUID.randomUUID().toString());
        message1.setName("Kostya");
        message1.setPhone("123-4567890");

        message2 = new Message();
        message2.setEmail("kostya@company.com");
        message2.setMessage(UUID.randomUUID().toString());
        message2.setName("Kostya");
        message2.setPhone("123-4567890");

        message3 = new Message();
        message3.setEmail("kostya@company.com");
        message3.setMessage(UUID.randomUUID().toString());
        message3.setName("Kostya");
        message3.setPhone("123-4567890");

        message4 = new Message();
        message4.setEmail("kostya@company.com");
        message4.setMessage(UUID.randomUUID().toString());
        message4.setName("Kostya");
        message4.setPhone("123-4567890");

        message5 = new Message();
        message5.setEmail("kostya@company.com");
        message5.setMessage(UUID.randomUUID().toString());
        message5.setName("Kostya");
        message5.setPhone("123-4567890");

        message6 = new Message();
        message6.setEmail("kostya@company.com");
        message6.setMessage(UUID.randomUUID().toString());
        message6.setName("Kostya");
        message6.setPhone("123-4567890");

        message7 = new Message();
        message7.setEmail("kostya@company.com");
        message7.setMessage(UUID.randomUUID().toString());
        message7.setName("Kostya");
        message7.setPhone("123-4567890");

        message8 = new Message();
        message8.setEmail("kostya@company.com");
        message8.setMessage(UUID.randomUUID().toString());
        message8.setName("Kostya");
        message8.setPhone("123-4567890");

        publicRestControllerConnectorHelper.sendMessage(message4);
        publicRestControllerConnectorHelper.sendMessage(message5);
        publicRestControllerConnectorHelper.sendMessage(message6);
        publicRestControllerConnectorHelper.sendMessage(message7);
        publicRestControllerConnectorHelper.sendMessage(message8);
    }

    @AfterClass
    public void teardown() {
        messageRepository.deleteById(UUID.fromString(message1.getMessage()));
        messageRepository.deleteById(UUID.fromString(message2.getMessage()));
        messageRepository.deleteById(UUID.fromString(message3.getMessage()));
        messageRepository.deleteById(UUID.fromString(message4.getMessage()));
        messageRepository.deleteById(UUID.fromString(message5.getMessage()));
        messageRepository.deleteById(UUID.fromString(message6.getMessage()));
        messageRepository.deleteById(UUID.fromString(message7.getMessage()));
        messageRepository.deleteById(UUID.fromString(message8.getMessage()));
    }

    @Test
    public void sendMessagePublicTest() {
        HttpResponse response = publicRestControllerConnectorHelper.sendMessage(message1);
        Message messageRes = (Message) response.getObject();
        Message messageDb = getMessageByMessage(message1.getMessage());
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(messageRes, messageDb);
        assertEquals(messageRes, message1);
    }

    @Test
    public void sendMessageNonAdminTest() {
        HttpResponse response = adminRestControllerConnectorHelper.sendMessagePublic(nonAdminUserCreds, message2);
        Message messageRes = (Message) response.getObject();
        Message messageDb = getMessageByMessage(message2.getMessage());
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(messageRes, messageDb);
        assertEquals(messageRes, message2);
    }

    @Test(dependsOnMethods = "sendMessagePublicTest")
    public void sendMessageAdminTest() {
        HttpResponse response = adminRestControllerConnectorHelper.sendMessagePublic(defaultAdminCreds, message3);
        Message messageRes = (Message) response.getObject();
        Message messageDb = getMessageByMessage(message3.getMessage());
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(messageRes, messageDb);
        assertEquals(messageRes, message3);
    }

    @Test
    public void getMessagesUnauthorizedNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getAllMessagesNegative().convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertEquals(response.getObject().get("message").getAsString(), "Unauthorized");
    }

    @Test
    public void getMessagesNonAdminNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getAllMessagesNegative(nonAdminUserCreds).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 403);
        assertEquals(response.getObject().get("error").getAsString(), "Forbidden");
        assertEquals(response.getObject().get("message").getAsString(), "Forbidden");
    }

    @Test(dependsOnMethods = {"sendMessagePublicTest", "sendMessageNonAdminTest", "sendMessageAdminTest"})
    public void getAllMessagesTest() {
        HttpResponse response = adminRestControllerConnectorHelper.getAllMessages(defaultAdminCreds);
        List<MessageWrapper> list = (List<MessageWrapper>) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(list.contains(getMessageWrapperByMessage(message1.getMessage())));
        assertTrue(list.contains(getMessageWrapperByMessage(message2.getMessage())));
        assertTrue(list.contains(getMessageWrapperByMessage(message3.getMessage())));
    }

    @Test(dependsOnMethods = "sendMessagePublicTest")
    public void getMessageUnauthorizedNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getMessageNegative(Objects.requireNonNull(getMessageWrapperByMessage(message1.getMessage())).getUuid().toString()).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertEquals(response.getObject().get("message").getAsString(), "Unauthorized");
    }

    @Test(dependsOnMethods = "sendMessagePublicTest")
    public void getMessageNonAdminNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getMessageNegative(nonAdminUserCreds, Objects.requireNonNull(getMessageWrapperByMessage(message1.getMessage())).getUuid().toString()).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 403);
        assertEquals(response.getObject().get("error").getAsString(), "Forbidden");
        assertEquals(response.getObject().get("message").getAsString(), "Forbidden");
    }

    @Test
    public void getMessageNullIdNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getMessageNegative(defaultAdminCreds,null).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Required String parameter 'uuid' is not present");
    }

    @Test
    public void getMessageNotExistingIdNegativeTest() {
        String uuid = UUID.randomUUID().toString();
        HttpResponseJson response = adminRestControllerConnectorHelper.getMessageNegative(defaultAdminCreds, uuid).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), String.format("Message with uuid '%s' not found", uuid));
    }

    @Test(dependsOnMethods = {"getAllMessagesTest", "getMessageUnauthorizedNegativeTest", "getMessageNonAdminNegativeTest"})
    public void getMessageTest() {
        MessageWrapper wrapperRes;
        MessageWrapper wrapper = getMessageWrapperByMessage(message3.getMessage());
        assert wrapper != null;
        HttpResponse response = adminRestControllerConnectorHelper.getMessage(defaultAdminCreds, wrapper.getUuid().toString());
        wrapperRes = (MessageWrapper) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertNotNull(wrapperRes);
        assertEquals(wrapperRes.getUuid(), wrapper.getUuid());
        assertEquals(wrapperRes.getMessage().getMessage(), wrapper.getMessage().getMessage());
    }

    @Test(dependsOnMethods = "getMessageTest")
    public void getMessageStatusChangeTest() {
        MessageWrapper wrapperRes;
        MessageWrapper wrapper = getMessageWrapperByMessage(message3.getMessage());
        assert wrapper != null;
        HttpResponse response = adminRestControllerConnectorHelper.getMessage(defaultAdminCreds, wrapper.getUuid().toString());
        wrapperRes = (MessageWrapper) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(wrapperRes.getStatus(), MessageStatus.READ);
    }

    @Test(dependsOnMethods = "getMessageStatusChangeTest")
    public void readMessageTest() {
        MessageWrapper wrapperRes;
        MessageWrapper wrapper = getMessageWrapperByMessage(message6.getMessage());
        assert wrapper != null;
        HttpResponse response = adminRestControllerConnectorHelper.readMessage(defaultAdminCreds, wrapper.getUuid().toString());
        assertEquals(response.getHttpStatusCode(), 200);
        wrapper = getMessageWrapperByMessage(message6.getMessage());
        assert wrapper != null;
        assertEquals(wrapper.getStatus(), MessageStatus.READ);
    }

    @Test
    public void readMessageNotExistingIdNegativeTest() {
        String uuid = UUID.randomUUID().toString();
        HttpResponseJson response = adminRestControllerConnectorHelper.readMessage(defaultAdminCreds, uuid).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), String.format("Message with uuid '%s' not found", uuid));
    }


    //readMessage positive
    //readMessage negative - not existing id

    //readMessages positive
    //readMessages negative - one not existing id


    @Test(dependsOnMethods = "getMessageStatusChangeTest")
    public void deleteMessageUnauthorizedNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteMessageNegative(Objects.requireNonNull(getMessageWrapperByMessage(message1.getMessage())).getUuid().toString()).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertEquals(response.getObject().get("message").getAsString(), "Unauthorized");
    }

    @Test(dependsOnMethods = "getMessageStatusChangeTest")
    public void deleteMessageNonAdminNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteMessageNegative(nonAdminUserCreds, Objects.requireNonNull(getMessageWrapperByMessage(message1.getMessage())).getUuid().toString()).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 403);
        assertEquals(response.getObject().get("error").getAsString(), "Forbidden");
        assertEquals(response.getObject().get("message").getAsString(), "Forbidden");
    }

    @Test
    public void deleteMessageNullIdNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteMessageNegative(defaultAdminCreds,null).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Required String parameter 'uuid' is not present");
    }

    @Test
    public void deleteMessageNotExistingIdNegativeTest() {
        String uuid = UUID.randomUUID().toString();
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteMessageNegative(defaultAdminCreds, uuid).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), String.format("Message with uuid '%s' not found", uuid));
    }

    @Test(dependsOnMethods = { "deleteMessageNonAdminNegativeTest", "deleteMessageUnauthorizedNegativeTest" })
    public void deleteMessageTest(){
        UUID uuid = Objects.requireNonNull(getMessageWrapperByMessage(message1.getMessage())).getUuid();
        assertTrue(messageRepository.existsById(uuid));
        HttpResponse response = adminRestControllerConnectorHelper.deleteMessage(defaultAdminCreds, uuid.toString());
        Boolean res = (Boolean) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(res);
        assertFalse(messageRepository.existsById(uuid));
        HttpResponseJson response1 = adminRestControllerConnectorHelper.getMessageNegative(defaultAdminCreds, uuid.toString()).convertToHttpResponseJson();
        System.out.println(response1.toString());
        assertEquals(response1.getHttpStatusCode(), 500);
        assertEquals(response1.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response1.getObject().get("message").getAsString(), String.format("Message with uuid '%s' not found", uuid.toString()));
    }

    @Test(dependsOnMethods = "deleteMessageTest")
    public void deleteMessagesUnauthorizedNegativeTest() {
        UUID uuid2 = getMessageWrapperByMessage(message2.getMessage()).getUuid();
        UUID uuid3 = getMessageWrapperByMessage(message3.getMessage()).getUuid();
        List<String> uuids = new ArrayList<>();
        uuids.add(uuid2.toString());
        uuids.add(uuid3.toString());
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteMessagesNegative(uuids).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertEquals(response.getObject().get("message").getAsString(), "Unauthorized");
    }

    @Test(dependsOnMethods = "deleteMessageTest")
    public void deleteMessagesNonAdminNegativeTest() {
        UUID uuid2 = getMessageWrapperByMessage(message2.getMessage()).getUuid();
        UUID uuid3 = getMessageWrapperByMessage(message3.getMessage()).getUuid();
        List<String> uuids = new ArrayList<>();
        uuids.add(uuid2.toString());
        uuids.add(uuid3.toString());
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteMessagesNegative(nonAdminUserCreds, uuids).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 403);
        assertEquals(response.getObject().get("error").getAsString(), "Forbidden");
        assertEquals(response.getObject().get("message").getAsString(), "Forbidden");
    }

    @Test
    public void deleteMessagesNullIdNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteMessagesNegative(defaultAdminCreds,null).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Required List parameter 'uuids' is not present");
    }

    @Test
    public void deleteMessagesNotExistingIdNegativeTest() {
        List<String> uuids = new ArrayList<>();
        uuids.add(UUID.randomUUID().toString());
        uuids.add(UUID.randomUUID().toString());
        HttpResponse response = adminRestControllerConnectorHelper.deleteMessages(defaultAdminCreds, uuids);
        assertEquals(response.getHttpStatusCode(), 200);
        Boolean res = (Boolean) response.getObject();
        assertFalse(res);
    }

    @Test(dependsOnMethods = { "deleteMessagesUnauthorizedNegativeTest", "deleteMessagesNonAdminNegativeTest" })
    public void deleteMessagesTest(){
        UUID uuid2 = getMessageWrapperByMessage(message2.getMessage()).getUuid();
        UUID uuid3 = getMessageWrapperByMessage(message3.getMessage()).getUuid();
        assertTrue(messageRepository.existsById(uuid2));
        assertTrue(messageRepository.existsById(uuid3));
        List<String> uuids = new ArrayList<>();
        uuids.add(uuid2.toString());
        uuids.add(uuid3.toString());
        HttpResponse response = adminRestControllerConnectorHelper.deleteMessages(defaultAdminCreds, uuids);
        Boolean res = (Boolean) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(res);
        assertFalse(messageRepository.existsById(uuid2));
        assertFalse(messageRepository.existsById(uuid3));
        HttpResponseJson response1 = adminRestControllerConnectorHelper.getMessageNegative(defaultAdminCreds, uuid2.toString()).convertToHttpResponseJson();
        assertEquals(response1.getHttpStatusCode(), 500);
        assertEquals(response1.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response1.getObject().get("message").getAsString(), String.format("Message with uuid '%s' not found", uuid2.toString()));
        HttpResponseJson response2 = adminRestControllerConnectorHelper.getMessageNegative(defaultAdminCreds, uuid3.toString()).convertToHttpResponseJson();
        assertEquals(response2.getHttpStatusCode(), 500);
        assertEquals(response2.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response2.getObject().get("message").getAsString(), String.format("Message with uuid '%s' not found", uuid3.toString()));
    }

    @Test(dependsOnMethods = "deleteMessagesTest")
    public void deleteAllMessageUnauthorizedNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteAllMessagesNegative().convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertEquals(response.getObject().get("message").getAsString(), "Unauthorized");
    }

    @Test(dependsOnMethods = "deleteMessagesTest")
    public void deleteAllMessageNonAdminNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteAllMessagesNegative(nonAdminUserCreds).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 403);
        assertEquals(response.getObject().get("error").getAsString(), "Forbidden");
        assertEquals(response.getObject().get("message").getAsString(), "Forbidden");
    }

    @Test(dependsOnMethods = { "deleteAllMessageUnauthorizedNegativeTest", "deleteAllMessageNonAdminNegativeTest", "deleteMessagesTest" })
    public void deleteAllMessagesTest(){
        UUID uuid4 = getMessageWrapperByMessage(message4.getMessage()).getUuid();
        UUID uuid5 = getMessageWrapperByMessage(message5.getMessage()).getUuid();
        assertTrue(messageRepository.existsById(uuid4));
        assertTrue(messageRepository.existsById(uuid5));
        HttpResponse response = adminRestControllerConnectorHelper.deleteAllMessages(defaultAdminCreds);
        Boolean res = (Boolean) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(res);
        assertFalse(messageRepository.existsById(uuid4));
        assertFalse(messageRepository.existsById(uuid5));
        assertEquals(messageRepository.count(), 0);
        HttpResponseJson response1 = adminRestControllerConnectorHelper.getMessageNegative(defaultAdminCreds, uuid4.toString()).convertToHttpResponseJson();
        assertEquals(response1.getHttpStatusCode(), 500);
        assertEquals(response1.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response1.getObject().get("message").getAsString(), String.format("Message with uuid '%s' not found", uuid4.toString()));
        HttpResponseJson response2 = adminRestControllerConnectorHelper.getMessageNegative(defaultAdminCreds, uuid5.toString()).convertToHttpResponseJson();
        assertEquals(response2.getHttpStatusCode(), 500);
        assertEquals(response2.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response2.getObject().get("message").getAsString(), String.format("Message with uuid '%s' not found", uuid5.toString()));
        HttpResponse response3 = adminRestControllerConnectorHelper.getAllMessages(defaultAdminCreds);
        List<MessageWrapper> list = (List<MessageWrapper>) response3.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(list.size(), 0);
    }

    private Message getMessageByMessage(String message){
        MessageWrapper wrapper = getMessageWrapperByMessage(message);
        if(wrapper != null)
            return wrapper.getMessage();
        return null;
    }

    private MessageWrapper getMessageWrapperByMessage(String message){
        List<MessageWrapper> list = messageRepository.findAll();
        for(MessageWrapper wrapper : list){
            if(wrapper.getMessage().getMessage().equals(message))
                return wrapper;
        }
        return null;
    }
}
