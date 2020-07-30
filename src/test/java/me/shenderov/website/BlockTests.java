package me.shenderov.website;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.shenderov.website.common.http.HttpResponse;
import me.shenderov.website.common.http.HttpResponseJson;
import me.shenderov.website.dao.*;
import me.shenderov.website.entities.GenericElement;
import me.shenderov.website.entities.TimelineElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

public class BlockTests extends WebsiteApplicationTests {

    private ObjectMapper mapper = new ObjectMapper();

    private String aboutBlockId = "aboutTestBlock";
    private String contactBlockId = "contactTestBlock";
    private String expertiseBlockId = "expertiseTestBlock";
    private String skillsBlockId = "skillTestBlock";
    private String timelineBlockId = "timelineTestBlock";
    private String negativeBlockId = "negativeTestBlock";
    private List<GenericElement> genericElements;
    private List<TimelineElement> timelineElements;

    @BeforeClass
    public void setup() {
        genericElements = new ArrayList<>();
        genericElements.add(new GenericElement(1, "class1", "label1", "value1"));
        genericElements.add(new GenericElement(2, "class2", "label2", "value2"));
        timelineElements = new ArrayList<>();
        timelineElements.add(new TimelineElement(null, null, true, "Developer", "Google", "USA", "Description"));
        timelineElements.add(new TimelineElement(null, null, false, "DevOps", "Facebook", "USA", "Description"));
    }

    @AfterClass
    public void teardown() {
        blockRepository.deleteById(aboutBlockId);
        blockRepository.deleteById(contactBlockId);
        blockRepository.deleteById(expertiseBlockId);
        blockRepository.deleteById(skillsBlockId);
        blockRepository.deleteById(timelineBlockId);
        blockRepository.deleteById(negativeBlockId);
    }

    @Test
    public void addAboutBlockTest() throws Exception {
        AboutBlock block = new AboutBlock();
        block.setId(aboutBlockId);
        block.setTitle("AboutBlockTest");
        block.setPosition(1);
        block.setContacts(genericElements);
        block.setDescription("Description");
        block.setMediaLinks(genericElements);
        block.setName("Joe Doe");
        block.setPhoto("/home/joe/image.jpg");
        block.setPositionName("Developer");
        HttpResponse response = adminRestControllerConnectorHelper.addBlock(defaultAdminCreds, block);
        Block newBlock = (Block) response.getObject();
        System.out.println(response.toString());
        Block blockDb = blockRepository.findById(aboutBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(block, newBlock);
        assertTrue(blockRepository.existsById(aboutBlockId));
        assertEquals(block, blockDb);
    }

    @Test
    public void addContactBlockTest() throws Exception {
        ContactBlock block = new ContactBlock();
        block.setId(contactBlockId);
        block.setTitle("ContactBlockTest");
        block.setPosition(1);
        block.setFormLabels(genericElements);
        HttpResponse response = adminRestControllerConnectorHelper.addBlock(defaultAdminCreds, block);
        Block newBlock = (Block) response.getObject();
        Block blockDb = blockRepository.findById(contactBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(block, newBlock);
        assertTrue(blockRepository.existsById(contactBlockId));
        assertEquals(block, blockDb);
    }

    @Test
    public void addExpertiseBlockTest() throws Exception {
        ExpertiseBlock block = new ExpertiseBlock();
        block.setId(expertiseBlockId);
        block.setTitle("ExpertiseBlockTest");
        block.setPosition(1);
        block.setBlocks(new HashSet<>(genericElements));
        HttpResponse response = adminRestControllerConnectorHelper.addBlock(defaultAdminCreds, block);
        Block newBlock = (Block) response.getObject();
        Block blockDb = blockRepository.findById(expertiseBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(block, newBlock);
        assertTrue(blockRepository.existsById(expertiseBlockId));
        assertEquals(block, blockDb);
    }

    @Test
    public void addSkillsBlockTest() throws Exception {
        SkillsBlock block = new SkillsBlock();
        block.setId(skillsBlockId);
        block.setTitle("SkillsBlockTest");
        block.setPosition(1);
        block.setSkills(genericElements);
        HttpResponse response = adminRestControllerConnectorHelper.addBlock(defaultAdminCreds, block);
        Block newBlock = (Block) response.getObject();
        Block blockDb = blockRepository.findById(skillsBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(block, newBlock);
        assertTrue(blockRepository.existsById(skillsBlockId));
        assertEquals(block, blockDb);
    }

    @Test
    public void addTimelineBlockTest() throws Exception {
        TimelineBlock block = new TimelineBlock();
        block.setId(timelineBlockId);
        block.setTitle("TimelineBlockTest");
        block.setPosition(1);
        block.setElements(timelineElements);
        HttpResponse response = adminRestControllerConnectorHelper.addBlock(defaultAdminCreds, block);
        System.out.println(response.toString());
        Block newBlock = (Block) response.getObject();
        Block blockDb = blockRepository.findById(timelineBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(block, newBlock);
        assertTrue(blockRepository.existsById(timelineBlockId));
        assertEquals(block, blockDb);
    }

    @Test(dependsOnMethods = "addAboutBlockTest")
    public void addNewBlockWithExistingIdNegativeTest() {
        AboutBlock block = new AboutBlock();
        block.setId(aboutBlockId);
        block.setTitle("AboutBlockTest");
        block.setPosition(1);
        HttpResponseJson response = adminRestControllerConnectorHelper.addBlockNegative(defaultAdminCreds, block).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "Block with id 'aboutTestBlock' already exist");
    }

    @Test
    public void addBlockNullIdNegativeTest() {
        AboutBlock block = new AboutBlock();
        block.setTitle("AboutBlockTest");
        block.setPosition(1);
        HttpResponseJson response = adminRestControllerConnectorHelper.addBlockNegative(defaultAdminCreds, block).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "The given id must not be null!");
    }

    @Test
    public void addBlockAsUnauthorizedUserNegativeTest() {
        AboutBlock block = new AboutBlock();
        block.setId(negativeBlockId);
        block.setTitle("AboutBlockTest");
        block.setPosition(1);
        HttpResponseJson response = adminRestControllerConnectorHelper.addBlockNegative(block).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertEquals(response.getObject().get("message").getAsString(), "Unauthorized");
    }

    @Test
    public void addBlockAsNonAdminUserNegativeTest() {
        AboutBlock block = new AboutBlock();
        block.setId(negativeBlockId);
        block.setTitle("AboutBlockTest");
        block.setPosition(1);
        HttpResponseJson response = adminRestControllerConnectorHelper.addBlockNegative(nonAdminUserCreds, block).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 403);
        assertEquals(response.getObject().get("error").getAsString(), "Forbidden");
        assertEquals(response.getObject().get("message").getAsString(), "Forbidden");
    }

    @Test(dependsOnMethods = "addAboutBlockTest")
    public void getAboutBlockTest() throws Exception {
        Block block = new AboutBlock();
        HttpResponse response = adminRestControllerConnectorHelper.getBlock(defaultAdminCreds, aboutBlockId, block);
        block = (Block) response.getObject();
        Block blockDb = blockRepository.findById(aboutBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertNotNull(block);
        assertEquals(block, blockDb);
    }

    @Test(dependsOnMethods = "addContactBlockTest")
    public void getContactsBlockTest() throws Exception {
        Block block = new ContactBlock();
        HttpResponse response = adminRestControllerConnectorHelper.getBlock(defaultAdminCreds, contactBlockId, block);
        block = (Block) response.getObject();
        Block blockDb = blockRepository.findById(contactBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertNotNull(block);
        assertEquals(block, blockDb);
    }

    @Test(dependsOnMethods = "addExpertiseBlockTest")
    public void getExpertiseBlockTest() throws Exception {
        Block block = new ExpertiseBlock();
        HttpResponse response = adminRestControllerConnectorHelper.getBlock(defaultAdminCreds, expertiseBlockId, block);
        block = (Block) response.getObject();
        Block blockDb = blockRepository.findById(expertiseBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertNotNull(block);
        assertEquals(block, blockDb);
    }

    @Test(dependsOnMethods = "addSkillsBlockTest")
    public void getSkillsBlockTest() throws Exception {
        Block block = new SkillsBlock();
        HttpResponse response = adminRestControllerConnectorHelper.getBlock(defaultAdminCreds, skillsBlockId, block);
        block = (Block) response.getObject();
        Block blockDb = blockRepository.findById(skillsBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertNotNull(block);
        assertEquals(block, blockDb);
    }

    @Test(dependsOnMethods = "addTimelineBlockTest")
    public void getTimelineBlockTest() throws Exception {
        Block block = new TimelineBlock();
        HttpResponse response = adminRestControllerConnectorHelper.getBlock(defaultAdminCreds, timelineBlockId, block);
        block = (Block) response.getObject();
        Block blockDb = blockRepository.findById(timelineBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertNotNull(block);
        assertEquals(block, blockDb);
    }

    @Test
    public void getBlockNullIdNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getBlockNegative(null).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "Block with id:  not found");
    }

    @Test
    public void getBlockNotExistingIdNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getBlockNegative(negativeBlockId).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "Block with id: negativeTestBlock not found");
    }

    @Test(dependsOnMethods = "getAboutBlockTest")
    public void updateAboutBlockTest() throws Exception {
        AboutBlock block = (AboutBlock) blockRepository.findById(aboutBlockId).orElseThrow(Exception::new);
        block.setDescription("Changed Title");
        HttpResponse response = adminRestControllerConnectorHelper.updateBlock(defaultAdminCreds, block);
        AboutBlock updatedBlock = (AboutBlock) response.getObject();
        Block blockDb = blockRepository.findById(aboutBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(block.getDescription(), updatedBlock.getDescription());
        assertEquals(block, blockDb);
    }

    @Test(dependsOnMethods = "getContactsBlockTest")
    public void updateContactsBlockTest() throws Exception {
        ContactBlock block = (ContactBlock) blockRepository.findById(contactBlockId).orElseThrow(Exception::new);
        assertNotNull(block.getFormLabels());
        block.setFormLabels(null);
        HttpResponse response = adminRestControllerConnectorHelper.updateBlock(defaultAdminCreds, block);
        ContactBlock updatedBlock = (ContactBlock) response.getObject();
        Block blockDb = blockRepository.findById(contactBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(block.getFormLabels(), updatedBlock.getFormLabels());
        assertEquals(block, blockDb);
    }

    @Test(dependsOnMethods = "getExpertiseBlockTest")
    public void updateExpertiseBlockTest() throws Exception {
        ExpertiseBlock block = (ExpertiseBlock) blockRepository.findById(expertiseBlockId).orElseThrow(Exception::new);
        assertNotNull(block.getBlocks());
        block.setBlocks(null);
        HttpResponse response = adminRestControllerConnectorHelper.updateBlock(defaultAdminCreds, block);
        ExpertiseBlock updatedBlock = (ExpertiseBlock) response.getObject();
        Block blockDb = blockRepository.findById(expertiseBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(block.getBlocks(), updatedBlock.getBlocks());
        assertEquals(block, blockDb);
    }

    @Test(dependsOnMethods = "getSkillsBlockTest")
    public void updateSkillsBlockTest() throws Exception {
        SkillsBlock block = (SkillsBlock) blockRepository.findById(skillsBlockId).orElseThrow(Exception::new);
        assertNotNull(block.getSkills());
        block.setSkills(null);
        HttpResponse response = adminRestControllerConnectorHelper.updateBlock(defaultAdminCreds, block);
        SkillsBlock updatedBlock = (SkillsBlock) response.getObject();
        Block blockDb = blockRepository.findById(skillsBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(block.getSkills(), updatedBlock.getSkills());
        assertEquals(block, blockDb);
    }

    @Test(dependsOnMethods = "getTimelineBlockTest")
    public void updateTimelineBlockTest() throws Exception {
        TimelineBlock block = (TimelineBlock) blockRepository.findById(timelineBlockId).orElseThrow(Exception::new);
        assertNotNull(block.getElements());
        block.setElements(null);
        HttpResponse response = adminRestControllerConnectorHelper.updateBlock(defaultAdminCreds, block);
        TimelineBlock updatedBlock = (TimelineBlock) response.getObject();
        Block blockDb = blockRepository.findById(timelineBlockId).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(block.getElements(), updatedBlock.getElements());
        assertEquals(block, blockDb);
    }

    @Test
    public void updateBlockNullIdNegativeTest() throws Exception {
        AboutBlock block = (AboutBlock) blockRepository.findById(aboutBlockId).orElseThrow(Exception::new);
        block.setId(null);
        HttpResponseJson response = adminRestControllerConnectorHelper.updateBlockNegative(defaultAdminCreds, block).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "The given id must not be null!");
    }

    @Test
    public void updateBlockNotExistingIdNegativeTest() throws Exception {
        AboutBlock block = (AboutBlock) blockRepository.findById(aboutBlockId).orElseThrow(Exception::new);
        block.setId(negativeBlockId);
        HttpResponseJson response = adminRestControllerConnectorHelper.updateBlockNegative(defaultAdminCreds, block).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "Block with id 'negativeTestBlock' does not exist");
    }

    @Test
    public void updateBlockAsUnauthorizedUserNegativeTest() throws Exception {
        AboutBlock block = (AboutBlock) blockRepository.findById(aboutBlockId).orElseThrow(Exception::new);
        block.setDescription("updateBlockAsUnauthorizedUserNegativeTest");
        HttpResponseJson response = adminRestControllerConnectorHelper.updateBlockNegative(block).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertEquals(response.getObject().get("message").getAsString(), "Unauthorized");
    }

    @Test
    public void updateBlockAsNonAdminUserNegativeTest() throws Exception {
        AboutBlock block = (AboutBlock) blockRepository.findById(aboutBlockId).orElseThrow(Exception::new);
        block.setDescription("updateBlockAsUnauthorizedUserNegativeTest");
        HttpResponseJson response = adminRestControllerConnectorHelper.updateBlockNegative(nonAdminUserCreds, block).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 403);
        assertEquals(response.getObject().get("error").getAsString(), "Forbidden");
        assertEquals(response.getObject().get("message").getAsString(), "Forbidden");
    }

    @Test(dependsOnMethods = {"getAboutBlockTest", "addNewBlockWithExistingIdNegativeTest", "updateBlockNullIdNegativeTest", "updateBlockNotExistingIdNegativeTest", "updateBlockAsUnauthorizedUserNegativeTest", "updateBlockAsNonAdminUserNegativeTest", "getBlocksTest", "getBlocksOneOfIdsNullNegativeTest", "getBlocksOneOfIdsNotExistingNegativeTest"})
    public void deleteAboutBlockTest() {
        assertTrue(blockRepository.existsById(aboutBlockId));
        HttpResponse response = adminRestControllerConnectorHelper.deleteBlock(defaultAdminCreds, aboutBlockId);
        Boolean res = (Boolean) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(res);
        assertFalse(blockRepository.existsById(aboutBlockId));
        HttpResponseJson response1 = adminRestControllerConnectorHelper.getBlockNegative(aboutBlockId).convertToHttpResponseJson();
        assertEquals(response1.getHttpStatusCode(), 500);
        assertEquals(response1.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response1.getObject().get("message").getAsString(), String.format("Block with id: %s not found", aboutBlockId));
    }

    @Test(dependsOnMethods = {"getContactsBlockTest", "getBlocksTest", "getBlocksOneOfIdsNullNegativeTest", "getBlocksOneOfIdsNotExistingNegativeTest", "updateContactsBlockTest"})
    public void deleteContactBlockTest() {
        assertTrue(blockRepository.existsById(contactBlockId));
        HttpResponse response = adminRestControllerConnectorHelper.deleteBlock(defaultAdminCreds, contactBlockId);
        Boolean res = (Boolean) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(res);
        assertFalse(blockRepository.existsById(contactBlockId));
        HttpResponseJson response1 = adminRestControllerConnectorHelper.getBlockNegative(contactBlockId).convertToHttpResponseJson();
        assertEquals(response1.getHttpStatusCode(), 500);
        assertEquals(response1.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response1.getObject().get("message").getAsString(), String.format("Block with id: %s not found", contactBlockId));
    }

    @Test(dependsOnMethods = {"getExpertiseBlockTest", "getBlocksTest", "getBlocksOneOfIdsNullNegativeTest", "getBlocksOneOfIdsNotExistingNegativeTest", "updateExpertiseBlockTest"})
    public void deleteExpertiseBlockTest() {
        assertTrue(blockRepository.existsById(expertiseBlockId));
        HttpResponse response = adminRestControllerConnectorHelper.deleteBlock(defaultAdminCreds, expertiseBlockId);
        Boolean res = (Boolean) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(res);
        assertFalse(blockRepository.existsById(expertiseBlockId));
        HttpResponseJson response1 = adminRestControllerConnectorHelper.getBlockNegative(expertiseBlockId).convertToHttpResponseJson();
        assertEquals(response1.getHttpStatusCode(), 500);
        assertEquals(response1.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response1.getObject().get("message").getAsString(), String.format("Block with id: %s not found", expertiseBlockId));
    }

    @Test(dependsOnMethods = {"getSkillsBlockTest", "getBlocksTest", "getBlocksOneOfIdsNullNegativeTest", "getBlocksOneOfIdsNotExistingNegativeTest", "updateSkillsBlockTest"})
    public void deleteSkillsBlockTest() {
        assertTrue(blockRepository.existsById(skillsBlockId));
        HttpResponse response = adminRestControllerConnectorHelper.deleteBlock(defaultAdminCreds, skillsBlockId);
        Boolean res = (Boolean) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(res);
        assertFalse(blockRepository.existsById(skillsBlockId));
        HttpResponseJson response1 = adminRestControllerConnectorHelper.getBlockNegative(skillsBlockId).convertToHttpResponseJson();
        assertEquals(response1.getHttpStatusCode(), 500);
        assertEquals(response1.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response1.getObject().get("message").getAsString(), String.format("Block with id: %s not found", skillsBlockId));
    }

    @Test(dependsOnMethods = {"getTimelineBlockTest", "getBlocksTest", "getBlocksOneOfIdsNullNegativeTest", "getBlocksOneOfIdsNotExistingNegativeTest", "updateTimelineBlockTest"})
    public void deleteTimelineBlockTest() {
        assertTrue(blockRepository.existsById(timelineBlockId));
        HttpResponse response = adminRestControllerConnectorHelper.deleteBlock(defaultAdminCreds, timelineBlockId);
        Boolean res = (Boolean) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(res);
        assertFalse(blockRepository.existsById(timelineBlockId));
        HttpResponseJson response1 = adminRestControllerConnectorHelper.getBlockNegative(timelineBlockId).convertToHttpResponseJson();
        assertEquals(response1.getHttpStatusCode(), 500);
        assertEquals(response1.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response1.getObject().get("message").getAsString(), String.format("Block with id: %s not found", timelineBlockId));
    }

    @Test
    public void deleteBlockNullIdNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteBlockNegative(defaultAdminCreds, null).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "Block with id '' does not exist");
    }

    @Test
    public void deleteBlockNotExistingIdNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteBlockNegative(defaultAdminCreds, "blablabla").convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "Block with id 'blablabla' does not exist");
    }

    @Test
    public void deleteBlockAsUnauthorizedUserNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteBlockNegative("blablabla").convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertEquals(response.getObject().get("message").getAsString(), "Unauthorized");
    }

    @Test
    public void deleteBlockAsNonAdminUserNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteBlockNegative(nonAdminUserCreds, "blablabla").convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 403);
        assertEquals(response.getObject().get("error").getAsString(), "Forbidden");
        assertEquals(response.getObject().get("message").getAsString(), "Forbidden");
    }

    @Test(dependsOnMethods = {"addAboutBlockTest", "addContactBlockTest", "addExpertiseBlockTest", "addSkillsBlockTest", "addTimelineBlockTest"})
    public void getBlocksTest() throws JsonProcessingException {
        List<String> ids = Arrays.asList(aboutBlockId, contactBlockId, expertiseBlockId, skillsBlockId, timelineBlockId);
        HttpResponse response = adminRestControllerConnectorHelper.getBlocks(defaultAdminCreds, ids);
        Map<String, Block> blocks = (Map<String, Block>) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(blocks.keySet().containsAll(ids));
    }

    @Test
    public void getBlocksNullIdsNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getBlocksNegative(null).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertTrue(response.getObject().get("message").getAsString().contains("Required request body is missing"));
    }

    @Test(dependsOnMethods = {"addAboutBlockTest", "addContactBlockTest", "addExpertiseBlockTest", "addSkillsBlockTest", "addTimelineBlockTest"})
    public void getBlocksOneOfIdsNullNegativeTest() {
        List<String> ids = new ArrayList<>(Arrays.asList(null, aboutBlockId, contactBlockId, expertiseBlockId, skillsBlockId, timelineBlockId));
        HttpResponse response = adminRestControllerConnectorHelper.getBlocks(defaultAdminCreds, ids);
        Map<String, Block> blocks = (Map<String, Block>) response.getObject();
        ids.remove(null);
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(blocks.keySet().containsAll(ids));
    }

    @Test(dependsOnMethods = {"addAboutBlockTest", "addContactBlockTest", "addExpertiseBlockTest", "addSkillsBlockTest", "addTimelineBlockTest"})
    public void getBlocksOneOfIdsNotExistingNegativeTest() {
        List<String> ids = new ArrayList<>(Arrays.asList(negativeBlockId, aboutBlockId, contactBlockId, expertiseBlockId, skillsBlockId, timelineBlockId));
        HttpResponse response = adminRestControllerConnectorHelper.getBlocks(defaultAdminCreds, ids);
        Map<String, Block> blocks = (Map<String, Block>) response.getObject();
        ids.remove(negativeBlockId);
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(blocks.keySet().containsAll(ids));
    }

}
