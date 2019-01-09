package me.shenderov.website.api.common;

import me.shenderov.website.dao.*;
import me.shenderov.website.interfaces.IAdminRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping(path = "/admin")
@ConditionalOnExpression("${application.restcontroller.admin.enabled:false}")
@RestController
public class AdminApiImpl {

    private final IAdminRequestHandler requestHandler;

    @Autowired
    public AdminApiImpl(IAdminRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @CachePut("blocks")
    @RequestMapping(value = "/block", method = RequestMethod.PUT)
    public Block addBlock(@RequestBody Block block) {
        return requestHandler.addBlock(block);
    }

    @CacheEvict("blocks")
    @RequestMapping(value = "/block", method = RequestMethod.POST)
    public Block updateBlock(@RequestBody Block block) {
        return requestHandler.updateBlock(block);
    }

    @CacheEvict("blocks")
    @RequestMapping(value = "/block", method = RequestMethod.DELETE)
    public boolean deleteBlock(@RequestParam("id") String id) {
        return requestHandler.deleteBlock(id);
    }

    @RequestMapping(value = "/getMessages", method = RequestMethod.GET)
    public List<MessageWrapper> getMessages() {
        return requestHandler.getMessages();
    }

}
