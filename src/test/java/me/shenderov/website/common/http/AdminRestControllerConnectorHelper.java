package me.shenderov.website.common.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import me.shenderov.website.common.TestConfiguration;
import me.shenderov.website.dao.Block;
import me.shenderov.website.dao.MessageWrapper;
import me.shenderov.website.dao.SeoInfo;
import me.shenderov.website.entities.Message;
import me.shenderov.website.security.entities.LoginWrapper;
import me.shenderov.website.security.dao.User;
import me.shenderov.website.security.entities.NewUserWrapper;
import me.shenderov.website.security.entities.UsernameAuthorityWrapper;
import me.shenderov.website.security.entities.UsernameValueWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminRestControllerConnectorHelper extends HttpConnectorGeneral {

    public AdminRestControllerConnectorHelper(String hostname, int port, String basePath) {
        super(hostname, port, basePath);
    }

    public HttpResponse addUser(LoginWrapper loginWrapper, NewUserWrapper user) {
        HttpResponse response = sendPostRequest(TestConfiguration.ADD_USER, user, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), User.class));
        return response;
    }

    public HttpResponse addUserNegative(LoginWrapper loginWrapper, NewUserWrapper user){
        return sendPostRequest(TestConfiguration.ADD_USER, user, loginWrapper);
    }

    public HttpResponse addUserNegative(NewUserWrapper user){
        return sendPostRequest(TestConfiguration.ADD_USER, user);
    }

    public HttpResponse changeUserName(LoginWrapper loginWrapper, UsernameValueWrapper wrapper) {
        HttpResponse response = sendPostRequest(TestConfiguration.CHANGE_NAME, wrapper, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), User.class));
        return response;
    }

    public HttpResponse changeUserNameNegative(LoginWrapper loginWrapper, UsernameValueWrapper wrapper){
        return sendPostRequest(TestConfiguration.CHANGE_NAME, wrapper, loginWrapper);
    }

    public HttpResponse changeUserNameNegative(UsernameValueWrapper wrapper){
        return sendPostRequest(TestConfiguration.CHANGE_NAME, wrapper);
    }

    public HttpResponse changeUserPassword(LoginWrapper loginWrapper, UsernameValueWrapper wrapper) {
        HttpResponse response = sendPostRequest(TestConfiguration.CHANGE_PASSWORD, wrapper, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), User.class));
        return response;
    }

    public HttpResponse changeUserPasswordNegative(LoginWrapper loginWrapper, UsernameValueWrapper wrapper){
        return sendPostRequest(TestConfiguration.CHANGE_PASSWORD, wrapper, loginWrapper);
    }

    public HttpResponse changeUserPasswordNegative(UsernameValueWrapper wrapper){
        return sendPostRequest(TestConfiguration.CHANGE_PASSWORD, wrapper);
    }

    public HttpResponse changeUserAuthorities(LoginWrapper loginWrapper, UsernameAuthorityWrapper wrapper) {
        HttpResponse response = sendPostRequest(TestConfiguration.SET_AUTHORITIES, wrapper, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), User.class));
        return response;
    }

    public HttpResponse changeUserAuthoritiesNegative(LoginWrapper loginWrapper, UsernameAuthorityWrapper wrapper){
        return sendPostRequest(TestConfiguration.SET_AUTHORITIES, wrapper, loginWrapper);
    }

    public HttpResponse changeUserAuthoritiesNegative(UsernameAuthorityWrapper wrapper){
        return sendPostRequest(TestConfiguration.SET_AUTHORITIES, wrapper);
    }

    public HttpResponse setUserEnabled(LoginWrapper loginWrapper, UsernameValueWrapper wrapper) {
        HttpResponse response = sendPostRequest(TestConfiguration.SET_ENABLED, wrapper, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), User.class));
        return response;
    }

    public HttpResponse setUserEnabledNegative(LoginWrapper loginWrapper, UsernameValueWrapper wrapper){
        return sendPostRequest(TestConfiguration.SET_ENABLED, wrapper, loginWrapper);
    }

    public HttpResponse setUserEnabledNegative(UsernameValueWrapper wrapper){
        return sendPostRequest(TestConfiguration.SET_ENABLED, wrapper);
    }

    public HttpResponse deleteUser(LoginWrapper loginWrapper, String username) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        HttpResponse response = sendDeleteRequest(TestConfiguration.DELETE_USER, params, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), Boolean.class));
        return response;
    }

    public HttpResponse deleteUserNegative(LoginWrapper loginWrapper, String username){
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        return sendDeleteRequest(TestConfiguration.DELETE_USER, params, loginWrapper);
    }

    public HttpResponse deleteUserNegative(String username){
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        return sendDeleteRequest(TestConfiguration.DELETE_USER, params);
    }

    public HttpResponse getUserDetails(LoginWrapper loginWrapper, String username) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        HttpResponse response = sendGetRequest(TestConfiguration.GET_USER_DETAILS, params, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), User.class));
        return response;
    }

    public HttpResponse getUserDetailsNegative(LoginWrapper loginWrapper, String username){
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        return sendGetRequest(TestConfiguration.GET_USER_DETAILS, params, loginWrapper);
    }

    public HttpResponse getUserDetailsNegative(String username){
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        return sendGetRequest(TestConfiguration.GET_USER_DETAILS, params);
    }

    public HttpResponse addBlock(LoginWrapper loginWrapper, Block block) {
        HttpResponse response = sendPostRequest(TestConfiguration.ADD_BLOCK, block, loginWrapper);
        response.setObject(testTools.stringToObject(response.getObject().toString(), block.getClass()));
        return response;
    }

    public HttpResponse addBlockNegative(LoginWrapper loginWrapper, Block block){
        return sendPostRequest(TestConfiguration.ADD_BLOCK, block, loginWrapper);
    }

    public HttpResponse addBlockNegative(Block block){
        return sendPostRequest(TestConfiguration.ADD_BLOCK, block);
    }

    public HttpResponse getBlock(LoginWrapper loginWrapper, String blockId, Block blockType) {
        Map<String, String> params = new HashMap<>();
        params.put("id", blockId);
        HttpResponse response = sendGetRequest(TestConfiguration.GET_BLOCK, params, loginWrapper);
        response.setObject(testTools.stringToObject(response.getObject().toString(), blockType.getClass()));
        return response;
    }

    public HttpResponse getBlockNegative(LoginWrapper loginWrapper, String blockId){
        Map<String, String> params = new HashMap<>();
        params.put("id", blockId);
        return sendGetRequest(TestConfiguration.GET_BLOCK, params, loginWrapper);
    }

    public HttpResponse getBlockNegative(String blockId){
        Map<String, String> params = new HashMap<>();
        params.put("id", blockId);
        return sendGetRequest(TestConfiguration.GET_BLOCK, params);
    }

    public HttpResponse getBlocks(LoginWrapper loginWrapper, List<String> blocks) {
        HttpResponse response = sendPostRequest(TestConfiguration.GET_BLOCKS, blocks, loginWrapper);
        try {
            Map<String, Block> blocksMap = mapper.readValue(response.getObject().toString(), new TypeReference<Map<String, Block>>() {});
            response.setObject(blocksMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public HttpResponse getBlocksNegative(List<String> blocks){
        return sendPostRequest(TestConfiguration.GET_BLOCKS, blocks);
    }

    public HttpResponse updateBlock(LoginWrapper loginWrapper, Block block) {
        HttpResponse response = sendPostRequest(TestConfiguration.UPDATE_BLOCK, block, loginWrapper);
        response.setObject(testTools.stringToObject(response.getObject().toString(), block.getClass()));
        return response;
    }

    public HttpResponse updateBlockNegative(LoginWrapper loginWrapper, Block block){
        return sendPostRequest(TestConfiguration.UPDATE_BLOCK, block, loginWrapper);
    }

    public HttpResponse updateBlockNegative(Block block){
        return sendPostRequest(TestConfiguration.UPDATE_BLOCK, block);
    }

    public HttpResponse deleteBlock(LoginWrapper loginWrapper, String blockId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", blockId);
        HttpResponse response = sendDeleteRequest(TestConfiguration.DELETE_BLOCK, params, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), Boolean.class));
        return response;
    }

    public HttpResponse deleteBlockNegative(LoginWrapper loginWrapper, String blockId){
        Map<String, String> params = new HashMap<>();
        params.put("id", blockId);
        return sendDeleteRequest(TestConfiguration.DELETE_BLOCK, params, loginWrapper);
    }

    public HttpResponse deleteBlockNegative(String blockId){
        Map<String, String> params = new HashMap<>();
        params.put("id", blockId);
        return sendDeleteRequest(TestConfiguration.DELETE_BLOCK, params);
    }

    public HttpResponse addSeoInfo(LoginWrapper loginWrapper, SeoInfo seoInfo) {
        HttpResponse response = sendPostRequest(TestConfiguration.ADD_SEO, seoInfo, loginWrapper);
        response.setObject(testTools.stringToObject(response.getObject().toString(), seoInfo.getClass()));
        return response;
    }

    public HttpResponse addSeoInfoNegative(LoginWrapper loginWrapper, SeoInfo seoInfo){
        return sendPostRequest(TestConfiguration.ADD_SEO, seoInfo, loginWrapper);
    }

    public HttpResponse addSeoInfoNegative(SeoInfo seoInfo){
        return sendPostRequest(TestConfiguration.ADD_SEO, seoInfo);
    }

    public HttpResponse updateSeoInfo(LoginWrapper loginWrapper, SeoInfo seoInfo) {
        HttpResponse response = sendPostRequest(TestConfiguration.UPDATE_SEO, seoInfo, loginWrapper);
        response.setObject(testTools.stringToObject(response.getObject().toString(), seoInfo.getClass()));
        return response;
    }

    public HttpResponse updateSeoInfoNegative(LoginWrapper loginWrapper, SeoInfo seoInfo){
        return sendPostRequest(TestConfiguration.UPDATE_SEO, seoInfo, loginWrapper);
    }

    public HttpResponse updateSeoInfoNegative(SeoInfo seoInfo){
        return sendPostRequest(TestConfiguration.UPDATE_SEO, seoInfo);
    }

    public HttpResponse getSeoDataPublic(LoginWrapper loginWrapper) {
        HttpResponse response = sendGetRequest(TestConfiguration.GET_SEO_INFO_PUBLIC, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), SeoInfo.class));
        return response;
    }

    public HttpResponse getSeoData(LoginWrapper loginWrapper, Integer seoId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", seoId.toString());
        HttpResponse response = sendGetRequest(TestConfiguration.GET_SEO_INFO_ADMIN, params, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), SeoInfo.class));
        return response;
    }

    public HttpResponse getSeoDataNegative(LoginWrapper loginWrapper, Integer seoId) {
        Map<String, String> params = new HashMap<>();
        if(seoId != null)
            params.put("id", seoId.toString());
        return sendGetRequest(TestConfiguration.GET_SEO_INFO_ADMIN, params, loginWrapper);
    }

    public HttpResponse getSeoDataNegative(Integer seoId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", seoId.toString());
        return sendGetRequest(TestConfiguration.GET_SEO_INFO_ADMIN, params);
    }

    public HttpResponse deleteSeoInfo(LoginWrapper loginWrapper, Integer seoId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", seoId.toString());
        HttpResponse response = sendDeleteRequest(TestConfiguration.DELETE_SEO, params, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), Boolean.class));
        return response;
    }

    public HttpResponse deleteSeoInfoNegative(LoginWrapper loginWrapper, Integer seoId){
        Map<String, String> params = new HashMap<>();
        if(seoId != null)
            params.put("id", seoId.toString());
        return sendDeleteRequest(TestConfiguration.DELETE_SEO, params, loginWrapper);
    }

    public HttpResponse deleteSeoInfoNegative(Integer seoId){
        Map<String, String> params = new HashMap<>();
        params.put("id", seoId.toString());
        return sendDeleteRequest(TestConfiguration.DELETE_SEO, params);
    }

    public HttpResponse sendMessagePublic(LoginWrapper loginWrapper,Message message) {
        HttpResponse response = sendPostRequest(TestConfiguration.SEND_MESSAGE, message, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), Message.class));
        return response;
    }

    public HttpResponse getAllMessages(LoginWrapper loginWrapper) {
        HttpResponse response = sendGetRequest(TestConfiguration.GET_MESSAGES, loginWrapper);
        try {
            List<MessageWrapper> list = mapper.readValue(response.getObject().toString(), new TypeReference<List<MessageWrapper>>() {});
            response.setObject(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public HttpResponse getAllMessagesNegative(LoginWrapper loginWrapper){
        return sendGetRequest(TestConfiguration.GET_MESSAGES, loginWrapper);
    }

    public HttpResponse getAllMessagesNegative(){
        return sendGetRequest(TestConfiguration.GET_MESSAGES);
    }

    public HttpResponse getMessage(LoginWrapper loginWrapper, String uuid) {
        Map<String, String> params = new HashMap<>();
        if(uuid != null)
            params.put("uuid", uuid);
        HttpResponse response = sendGetRequest(TestConfiguration.GET_MESSAGE, params, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), MessageWrapper.class));
        return response;
    }

    public HttpResponse getMessageNegative(LoginWrapper loginWrapper, String uuid){
        Map<String, String> params = new HashMap<>();
        if(uuid != null)
            params.put("uuid", uuid);
        return sendGetRequest(TestConfiguration.GET_MESSAGE, params, loginWrapper);
    }

    public HttpResponse readMessage(LoginWrapper loginWrapper, String uuid){
        Map<String, String> params = new HashMap<>();
        if(uuid != null)
            params.put("uuid", uuid);
        return sendGetRequest(TestConfiguration.GET_MESSAGE, params, loginWrapper);
    }

    public HttpResponse readMessages(LoginWrapper loginWrapper, List<String> uuids){
        Map<String, List<String>> params = new HashMap<>();
        if(uuids != null)
            params.put("uuids", uuids);
        return sendPostRequest(TestConfiguration.GET_MESSAGE, params, loginWrapper);
    }

    public HttpResponse getMessageNegative(String uuid){
        Map<String, String> params = new HashMap<>();
        if(uuid != null)
            params.put("uuid", uuid);
        return sendGetRequest(TestConfiguration.GET_MESSAGE, params);
    }

    public HttpResponse deleteMessage(LoginWrapper loginWrapper, String uuid) {
        Map<String, String> params = new HashMap<>();
        if(uuid != null)
            params.put("uuid", uuid);
        HttpResponse response = sendDeleteRequest(TestConfiguration.DELETE_MESSAGE, params, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), Boolean.class));
        return response;
    }

    public HttpResponse deleteMessageNegative(LoginWrapper loginWrapper, String uuid){
        Map<String, String> params = new HashMap<>();
        if(uuid != null)
            params.put("uuid", uuid);
        return sendDeleteRequest(TestConfiguration.DELETE_MESSAGE, params, loginWrapper);
    }

    public HttpResponse deleteMessageNegative(String uuid){
        Map<String, String> params = new HashMap<>();
        if(uuid != null)
            params.put("uuid", uuid);
        return sendDeleteRequest(TestConfiguration.DELETE_MESSAGE, params);
    }

    public HttpResponse deleteMessages(LoginWrapper loginWrapper, List<String> uuids) {
        Map<String, List<String>> params = new HashMap<>();
        if(uuids != null)
            params.put("uuids", uuids);
        HttpResponse response = sendDeleteRequest(TestConfiguration.DELETE_MESSAGES, params, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), Boolean.class));
        return response;
    }

    public HttpResponse deleteMessagesNegative(LoginWrapper loginWrapper, List<String> uuids){
        Map<String, List<String>> params = new HashMap<>();
        if(uuids != null)
            params.put("uuids", uuids);
        return sendDeleteRequest(TestConfiguration.DELETE_MESSAGES, params, loginWrapper);
    }

    public HttpResponse deleteMessagesNegative(List<String> uuids){
        Map<String, List<String>> params = new HashMap<>();
        if(uuids != null)
            params.put("uuids", uuids);
        return sendDeleteRequest(TestConfiguration.DELETE_MESSAGES, params);
    }

    public HttpResponse deleteAllMessages(LoginWrapper loginWrapper) {
        HttpResponse response = sendDeleteRequest(TestConfiguration.DELETE_ALL_MESSAGES, loginWrapper);
        response.setObject(testTools.stringToObject((String) response.getObject(), Boolean.class));
        return response;
    }

    public HttpResponse deleteAllMessagesNegative(LoginWrapper loginWrapper){
        return sendDeleteRequest(TestConfiguration.DELETE_ALL_MESSAGES, loginWrapper);
    }

    public HttpResponse deleteAllMessagesNegative(){
        return sendDeleteRequest(TestConfiguration.DELETE_ALL_MESSAGES);
    }

}
