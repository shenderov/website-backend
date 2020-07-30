package me.shenderov.website;

import me.shenderov.website.common.http.HttpResponse;
import me.shenderov.website.common.http.HttpResponseJson;
import me.shenderov.website.dao.SeoInfo;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

public class SeoTests extends WebsiteApplicationTests {

    private Map<String, String> metaData;

    @BeforeClass
    public void setup() {
        metaData = new HashMap<>();
        metaData.put("author", "Konstantin Shenderov");
        metaData.put("keywords", "qa, test automation, test automation engineer");
    }

    @AfterClass
    public void teardown() {
        seoRepository.deleteById(10);
    }

    @Test
    public void addSeoInfoTest() throws Exception {
        SeoInfo seoInfo = new SeoInfo();
        seoInfo.setId(10);
        seoInfo.setTitle("Seo data");
        seoInfo.setFooterCopyright("2020 Konstantin Shenderov");
        seoInfo.setMetaData(metaData);
        HttpResponse response = adminRestControllerConnectorHelper.addSeoInfo(defaultAdminCreds, seoInfo);
        SeoInfo newSeoInfo = (SeoInfo) response.getObject();
        SeoInfo seoInfoDb = seoRepository.findById(10).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(seoInfo, newSeoInfo);
        assertTrue(seoRepository.existsById(10));
        assertEquals(seoInfo, seoInfoDb);
    }

    @Test
    public void addSeoInfoWithoutIdTest() {
        SeoInfo seoInfo = new SeoInfo();
        seoInfo.setTitle("Seo data");
        seoInfo.setFooterCopyright("2020 Konstantin Shenderov");
        seoInfo.setMetaData(metaData);
        HttpResponseJson response = adminRestControllerConnectorHelper.addSeoInfoNegative(defaultAdminCreds, seoInfo).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "The given id must not be null!");
    }

    @Test(dependsOnMethods = "addSeoInfoTest")
    public void addSeoInfoExistingIdTest() {
        SeoInfo seoInfo = new SeoInfo();
        seoInfo.setId(10);
        seoInfo.setTitle("Seo data");
        seoInfo.setFooterCopyright("2020 Konstantin Shenderov");
        seoInfo.setMetaData(metaData);
        HttpResponseJson response = adminRestControllerConnectorHelper.addSeoInfoNegative(defaultAdminCreds, seoInfo).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "SEO info with id '10' already exist");
    }

    @Test
    public void addSeoInfoNonAdminUserTest() {
        SeoInfo seoInfo = new SeoInfo();
        seoInfo.setId(10);
        seoInfo.setTitle("Seo data");
        seoInfo.setFooterCopyright("2020 Konstantin Shenderov");
        seoInfo.setMetaData(metaData);
        HttpResponseJson response = adminRestControllerConnectorHelper.addSeoInfoNegative(nonAdminUserCreds, seoInfo).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 403);
        assertEquals(response.getObject().get("error").getAsString(), "Forbidden");
        assertEquals(response.getObject().get("message").getAsString(), "Forbidden");
    }

    @Test
    public void addSeoInfoUnauthorizedTest() {
        SeoInfo seoInfo = new SeoInfo();
        seoInfo.setId(10);
        seoInfo.setTitle("Seo data");
        seoInfo.setFooterCopyright("2020 Konstantin Shenderov");
        seoInfo.setMetaData(metaData);
        HttpResponseJson response = adminRestControllerConnectorHelper.addSeoInfoNegative(seoInfo).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertEquals(response.getObject().get("message").getAsString(), "Unauthorized");
    }

    @Test(dependsOnMethods = "addSeoInfoTest")
    public void updateSeoInfoTest() throws Exception {
        SeoInfo seoInfo = seoRepository.findById(10).orElseThrow(Exception::new);
        seoInfo.setTitle("Seo data updated");
        HttpResponse response = adminRestControllerConnectorHelper.updateSeoInfo(defaultAdminCreds, seoInfo);
        SeoInfo updatedSeoInfo = (SeoInfo) response.getObject();
        SeoInfo seoInfoDb = seoRepository.findById(10).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertEquals(seoInfo.getTitle(), updatedSeoInfo.getTitle());
        assertEquals(seoInfo, updatedSeoInfo);
        assertEquals(seoInfo, seoInfoDb);
    }

    @Test
    public void updateSeoInfoWithoutIdTest() throws Exception {
        SeoInfo seoInfo = seoRepository.findById(10).orElseThrow(Exception::new);
        seoInfo.setId(null);
        HttpResponseJson response = adminRestControllerConnectorHelper.updateSeoInfoNegative(defaultAdminCreds, seoInfo).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "The given id must not be null!");
    }

    @Test
    public void updateSeoInfoNotExistingIdTest() throws Exception {
        SeoInfo seoInfo = seoRepository.findById(10).orElseThrow(Exception::new);
        seoInfo.setId(123456);
        HttpResponseJson response = adminRestControllerConnectorHelper.updateSeoInfoNegative(defaultAdminCreds, seoInfo).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "SEO info with id '123456' does not exist");
    }

    @Test(dependsOnMethods = "updateSeoInfoTest")
    public void updateSeoInfoNonAdminUserTest() throws Exception {
        SeoInfo seoInfo = seoRepository.findById(10).orElseThrow(Exception::new);
        seoInfo.setTitle("Seo data updated");
        HttpResponseJson response = adminRestControllerConnectorHelper.updateSeoInfoNegative(nonAdminUserCreds, seoInfo).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 403);
        assertEquals(response.getObject().get("error").getAsString(), "Forbidden");
        assertEquals(response.getObject().get("message").getAsString(), "Forbidden");
    }

    @Test(dependsOnMethods = "updateSeoInfoTest")
    public void updateSeoInfoUnauthorizedTest() throws Exception {
        SeoInfo seoInfo = seoRepository.findById(10).orElseThrow(Exception::new);
        seoInfo.setTitle("Seo data updated");
        HttpResponseJson response = adminRestControllerConnectorHelper.updateSeoInfoNegative(seoInfo).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertEquals(response.getObject().get("message").getAsString(), "Unauthorized");
    }

    @Test(dependsOnMethods = "addSeoInfoTest")
    public void getSeoInfoAsAdminAdminApiTest() throws Exception {
        SeoInfo seoInfo;
        HttpResponse response = adminRestControllerConnectorHelper.getSeoData(defaultAdminCreds, 10);
        seoInfo = (SeoInfo) response.getObject();
        SeoInfo seoInfoDb = seoRepository.findById(10).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertNotNull(seoInfo);
        assertEquals(seoInfo, seoInfoDb);
    }

    @Test
    public void getSeoInfoAsAdminPublicApiTest() throws Exception {
        SeoInfo seoInfo;
        HttpResponse response = adminRestControllerConnectorHelper.getSeoDataPublic(defaultAdminCreds);
        seoInfo = (SeoInfo) response.getObject();
        SeoInfo seoInfoDb = seoRepository.findById(1).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertNotNull(seoInfo);
        assertEquals(seoInfo, seoInfoDb);
    }

    @Test(dependsOnMethods = "addSeoInfoTest")
    public void getSeoInfoAsNonAdminAdminApiNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getSeoDataNegative(nonAdminUserCreds, 10).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 403);
        assertEquals(response.getObject().get("error").getAsString(), "Forbidden");
        assertEquals(response.getObject().get("message").getAsString(), "Forbidden");
    }

    @Test
    public void getSeoInfoAsNonAdminPublicApiTest() throws Exception {
        SeoInfo seoInfo;
        HttpResponse response = adminRestControllerConnectorHelper.getSeoDataPublic(nonAdminUserCreds);
        seoInfo = (SeoInfo) response.getObject();
        SeoInfo seoInfoDb = seoRepository.findById(1).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertNotNull(seoInfo);
        assertEquals(seoInfo, seoInfoDb);
    }

    @Test(dependsOnMethods = "addSeoInfoTest")
    public void getSeoInfoUnauthAdminApiNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getSeoDataNegative(10).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertEquals(response.getObject().get("message").getAsString(), "Unauthorized");
    }

    @Test
    public void getSeoInfoUnauthPublicApiTest() throws Exception {
        SeoInfo seoInfo;
        HttpResponse response = publicRestControllerConnectorHelper.getSeoData();
        seoInfo = (SeoInfo) response.getObject();
        SeoInfo seoInfoDb = seoRepository.findById(1).orElseThrow(Exception::new);
        assertEquals(response.getHttpStatusCode(), 200);
        assertNotNull(seoInfo);
        assertEquals(seoInfo, seoInfoDb);
    }

    @Test
    public void getSeoInfoNoIdAdminApiNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getSeoDataNegative(defaultAdminCreds,null).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Required Integer parameter 'id' is not present");
    }

    @Test
    public void getSeoInfoNonExistingIdAdminApiNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.getSeoDataNegative(defaultAdminCreds,9999).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "Seo Data with id: 9999 is not found");
    }

    @Test(dependsOnMethods = {"updateSeoInfoNonAdminUserTest", "updateSeoInfoUnauthorizedTest", "getSeoInfoAsAdminAdminApiTest", "getSeoInfoAsNonAdminAdminApiNegativeTest", "getSeoInfoUnauthAdminApiNegativeTest"})
    public void deleteSeoInfoNonAdminNegativeTest(){
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteSeoInfoNegative(nonAdminUserCreds, 10).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 403);
        assertEquals(response.getObject().get("error").getAsString(), "Forbidden");
        assertEquals(response.getObject().get("message").getAsString(), "Forbidden");
    }

    @Test(dependsOnMethods = "deleteSeoInfoNonAdminNegativeTest")
    public void deleteSeoInfoUnAuthorizedNegativeTest(){
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteSeoInfoNegative(10).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 401);
        assertEquals(response.getObject().get("error").getAsString(), "Unauthorized");
        assertEquals(response.getObject().get("message").getAsString(), "Unauthorized");
    }

    @Test(dependsOnMethods = "deleteSeoInfoUnAuthorizedNegativeTest")
    public void deleteSeoInfoTest(){
        assertTrue(seoRepository.existsById(10));
        HttpResponse response = adminRestControllerConnectorHelper.deleteSeoInfo(defaultAdminCreds, 10);
        Boolean res = (Boolean) response.getObject();
        assertEquals(response.getHttpStatusCode(), 200);
        assertTrue(res);
        assertFalse(seoRepository.existsById(10));
        HttpResponseJson response1 = adminRestControllerConnectorHelper.getSeoDataNegative(defaultAdminCreds,10).convertToHttpResponseJson();
        assertEquals(response1.getHttpStatusCode(), 500);
        assertEquals(response1.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response1.getObject().get("message").getAsString(), "Seo Data with id: 10 is not found");
    }

    @Test
    public void deleteSeoInfoNullIdNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteSeoInfoNegative(defaultAdminCreds,null).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 400);
        assertEquals(response.getObject().get("error").getAsString(), "Bad Request");
        assertEquals(response.getObject().get("message").getAsString(), "Required Integer parameter 'id' is not present");
    }

    @Test
    public void deleteSeoInfoNotExistingIdNegativeTest() {
        HttpResponseJson response = adminRestControllerConnectorHelper.deleteSeoInfoNegative(defaultAdminCreds,9999).convertToHttpResponseJson();
        assertEquals(response.getHttpStatusCode(), 500);
        assertEquals(response.getObject().get("error").getAsString(), "Internal Server Error");
        assertEquals(response.getObject().get("message").getAsString(), "SEO info with id '9999' does not exist");
    }
}
