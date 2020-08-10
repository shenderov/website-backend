package me.shenderov.website.interfaces;

import me.shenderov.website.dao.Block;
import me.shenderov.website.dao.MessageWrapper;
import me.shenderov.website.dao.SeoInfo;
import me.shenderov.website.entities.BlockPreview;
import me.shenderov.website.entities.MessageStatus;
import me.shenderov.website.entities.MessageStatusWrapper;
import me.shenderov.website.security.dao.Authority;
import me.shenderov.website.security.dao.User;
import me.shenderov.website.security.entities.NewUserWrapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface IAdminRequestHandler {

    User addUser(NewUserWrapper user);

    User getUserDetails(String username);

    List<User> getAllUsers();

    List<Authority> getAuthorities();

    User changeUserName(String username, String newName);

    User changeUserPassword(String username, String password);

    User setUserAuthorities(String username, Set<Authority> authorities);

    User setUserEnabled(String username, Boolean isEnabled);

    Boolean deleteUser(String username);

    Map<String, BlockPreview> getAllBlocks();

    Block addBlock(Block block);

    Block updateBlock(Block block);

    Boolean deleteBlock(String id);

    SeoInfo addSeoInfo(SeoInfo seoInfo);

    SeoInfo updateSeoInfo(SeoInfo seoInfo);

    Boolean deleteSeoInfo(Integer id);

    SeoInfo getSeoInfo(Integer id) throws Exception;

    List<MessageWrapper> getAllMessages();

    MessageWrapper getMessage(UUID uuid);

    void readMessages(List<UUID> uuids);

    void readAllMessages();

    MessageWrapper readMessage(UUID uuid);

    Boolean deleteMessage(UUID uuid);

    Boolean deleteMessages(List<UUID> uuids);

    Boolean deleteAllMessages();

    Integer getNewMessagesCount();

}
