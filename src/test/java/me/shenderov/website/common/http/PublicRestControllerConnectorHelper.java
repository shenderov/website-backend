package me.shenderov.website.common.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import me.shenderov.website.common.TestConfiguration;
import me.shenderov.website.dao.Block;
import me.shenderov.website.dao.SeoInfo;
import me.shenderov.website.entities.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicRestControllerConnectorHelper extends HttpConnectorGeneral {

    public PublicRestControllerConnectorHelper(String hostname, int port, String basePath) {
        super(hostname, port, basePath);
    }

    public HttpResponse getSeoData() {
        HttpResponse response = sendGetRequest(TestConfiguration.GET_SEO_INFO_PUBLIC);
        response.setObject(testTools.stringToObject((String) response.getObject(), SeoInfo.class));
        return response;
    }

    public HttpResponse getBlock(String blockId, Block blockType) {
        Map<String, String> params = new HashMap<>();
        params.put("id", blockId);
        HttpResponse response = sendGetRequest(TestConfiguration.GET_BLOCK, params);
        response.setObject(testTools.stringToObject(response.getObject().toString(), blockType.getClass()));
        return response;
    }

    public HttpResponse getBlocks(List<String> blocks) {
        HttpResponse response = sendPostRequest(TestConfiguration.GET_BLOCKS, blocks);
        try {
            Map<String, Block> blocksMap = mapper.readValue(response.getObject().toString(), new TypeReference<Map<String, Block>>() {});
            response.setObject(blocksMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public HttpResponse sendMessage(Message message) {
        HttpResponse response = sendPostRequest(TestConfiguration.SEND_MESSAGE, message);
        response.setObject(testTools.stringToObject((String) response.getObject(), Message.class));
        return response;
    }

}
