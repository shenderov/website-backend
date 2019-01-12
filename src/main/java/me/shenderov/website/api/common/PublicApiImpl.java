package me.shenderov.website.api.common;

import me.shenderov.website.dao.Block;
import me.shenderov.website.dao.SeoInfo;
import me.shenderov.website.entities.Message;
import me.shenderov.website.interfaces.IPublicRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RequestMapping(path = "/")
@RestController
public class PublicApiImpl {

    private final static Logger LOGGER = Logger.getLogger(PublicApiImpl.class.getName());

    private final IPublicRequestHandler requestHandler;

    @Autowired
    public PublicApiImpl(IPublicRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @RequestMapping(value = "/seo", method = RequestMethod.GET)
    public SeoInfo getSeoData(HttpServletRequest request) throws Exception {
        LOGGER.info(request.getRemoteAddr()+"/seo|");
        return requestHandler.getSeoData();
    }

    @RequestMapping(value = "/block", method = RequestMethod.GET)
    public Block getBlock(@RequestParam("id") String id, HttpServletRequest request) throws Exception {
        LOGGER.info(request.getRemoteAddr()+"/block|");
        return requestHandler.getBlock(id);
    }

    @RequestMapping(value = "/blocks", method = RequestMethod.POST)
    public Map<String, Block> getBlocks(@RequestBody List<String> ids, HttpServletRequest request){
        LOGGER.info(request.getRemoteAddr()+"/blocks|");
        return requestHandler.getBlocks(ids);
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public Message sendMessage(@RequestBody Message message, HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/sendMessage|");
        return requestHandler.sendMessage(message).getMessage();
    }

}
