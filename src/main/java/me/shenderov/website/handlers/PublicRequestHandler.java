package me.shenderov.website.handlers;

import me.shenderov.website.dao.Block;
import me.shenderov.website.dao.MessageWrapper;
import me.shenderov.website.dao.SeoInfo;
import me.shenderov.website.entities.Message;
import me.shenderov.website.exceptions.MissingParameterException;
import me.shenderov.website.exceptions.RecaptchaValidationException;
import me.shenderov.website.interfaces.IPublicRequestHandler;
import me.shenderov.website.repositories.BlockRepository;
import me.shenderov.website.repositories.MessageRepository;
import me.shenderov.website.repositories.SeoRepository;
import me.shenderov.website.services.MessageScheduler;
import me.shenderov.website.services.RecapchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PublicRequestHandler implements IPublicRequestHandler {

    private final static Logger LOGGER = Logger.getLogger(PublicRequestHandler.class.getName());

    @Value("${application.security.enable-recaptcha}")
    private boolean enableRecaptcha;

    @Autowired
    private SeoRepository seoRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageScheduler messageScheduler;

    @Autowired
    private RecapchaService recapchaService;

    @Cacheable({"seo"})
    public SeoInfo getSeoData() throws Exception {
        return seoRepository.findById(1).orElseThrow(() -> new Exception(String.format("Seo Data is not found")));
    }

    @Cacheable({"blocks"})
    public Block getBlock(String id) throws Exception {
        return blockRepository.findById(id).orElseThrow(() -> new Exception(String.format("Block with id: %s not found", id)));
    }

    @Cacheable({"blocks"})
    public Map<String, Block> getBlocks(List<String> ids) {
        Map<String, Block> blocks = new HashMap<>();
        for(Block b : blockRepository.findAllById(ids)){
            blocks.put(b.getId(), b);
        }
        return blocks;
    }

    public MessageWrapper sendMessage(Message message, String recaptchaResponse) {
        if(enableRecaptcha){
            validateRecaptcha(recaptchaResponse);
        }
        MessageWrapper wrapper = new MessageWrapper(message);
        wrapper = messageRepository.insert(wrapper);
        messageScheduler.sendFormMessage(wrapper);
        LOGGER.info(wrapper.toString());
        return wrapper;
    }

    private void validateRecaptcha(String recaptchaResponse) {
        if(recaptchaResponse == null){
            throw new MissingParameterException("Required String parameter 'g-recaptcha-response' is not present");
        }else if (recapchaService.verifyRecaptcha(recaptchaResponse)){
            LOGGER.info("Recaptcha successfully validated");
        }else{
            LOGGER.warning("Recaptcha validation failded");
            throw new RecaptchaValidationException("Recaptcha validation failded");
        }
    }
}
