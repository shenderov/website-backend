package me.shenderov.website.handlers;

import me.shenderov.website.dao.Block;
import me.shenderov.website.dao.MessageWrapper;
import me.shenderov.website.dao.SeoInfo;
import me.shenderov.website.entities.Message;
import me.shenderov.website.interfaces.IPublicRequestHandler;
import me.shenderov.website.repositories.BlockRepository;
import me.shenderov.website.repositories.MessageRepository;
import me.shenderov.website.repositories.SeoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PublicRequestHandler implements IPublicRequestHandler {

    private final static Logger LOGGER = Logger.getLogger(PublicRequestHandler.class.getName());

    @Autowired
    private SeoRepository seoRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public SeoInfo getSeoData() throws Exception {
        return seoRepository.findById(1).orElseThrow(() -> new Exception(String.format("Seo Data is not found")));
    }

    public Block getBlock(String id) throws Exception {
        return blockRepository.findById(id).orElseThrow(() -> new Exception(String.format("Block with id: %s not found", id)));
    }

    public Map<String, Block> getBlocks(List<String> ids) {
        Map<String, Block> blocks = new HashMap<>();
        for(Block b : blockRepository.findAllById(ids)){
            blocks.put(b.getId(), b);
        }
        return blocks;
    }

    public MessageWrapper sendMessage(Message message) {
        MessageWrapper wrapper = new MessageWrapper(message);
        //TODO implement email sender
        LOGGER.info(wrapper.toString());
        return messageRepository.insert(wrapper);
    }
}
