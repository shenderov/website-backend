package me.shenderov.website.handlers;

import me.shenderov.website.dao.Block;
import me.shenderov.website.dao.MessageWrapper;
import me.shenderov.website.interfaces.IAdminRequestHandler;
import me.shenderov.website.repositories.BlockRepository;
import me.shenderov.website.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;

public class AdminRequestHandler implements IAdminRequestHandler {

    private final static Logger LOGGER = Logger.getLogger(AdminRequestHandler.class.getName());

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Block addBlock(Block block) {
        return blockRepository.insert(block);
    }

    public Block updateBlock(Block block) {
        return blockRepository.save(block);
    }

    public boolean deleteBlock(String id) {
        blockRepository.deleteById(id);
        return blockRepository.existsById(id);
    }

    public List<MessageWrapper> getMessages() {
        return messageRepository.findAll();
    }
}
