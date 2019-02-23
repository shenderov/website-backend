package me.shenderov.website.interfaces;

import me.shenderov.website.dao.Block;
import me.shenderov.website.dao.MessageWrapper;
import me.shenderov.website.dao.SeoInfo;
import me.shenderov.website.entities.Message;

import java.util.List;
import java.util.Map;

public interface IPublicRequestHandler {

    SeoInfo getSeoData() throws Exception;

    Block getBlock(String id) throws Exception;

    Map<String, Block> getBlocks(List<String> id);

    MessageWrapper sendMessage(Message message, String recaptchaResponse);
}
