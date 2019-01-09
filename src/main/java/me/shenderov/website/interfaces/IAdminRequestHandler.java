package me.shenderov.website.interfaces;

import me.shenderov.website.dao.Block;
import me.shenderov.website.dao.MessageWrapper;

import java.util.List;

public interface IAdminRequestHandler {

    Block addBlock(Block block);

    Block updateBlock(Block block);

    boolean deleteBlock(String id);

    List<MessageWrapper> getMessages();
}
