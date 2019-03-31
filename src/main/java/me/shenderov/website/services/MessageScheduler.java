package me.shenderov.website.services;

import me.shenderov.website.dao.MessageWrapper;
import me.shenderov.website.entities.MessageStatus;
import me.shenderov.website.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class MessageScheduler {

    private final static Logger LOGGER = Logger.getLogger(MessageScheduler.class.getName());

    private final Environment env;

    private final MessageRepository messageRepository;

    private final MailContentBuilder mailContentBuilder;

    private final MailService mailService;

    @Autowired
    public MessageScheduler(Environment env, MessageRepository messageRepository, MailContentBuilder mailContentBuilder, MailService mailService) {
        this.env = env;
        this.messageRepository = messageRepository;
        this.mailContentBuilder = mailContentBuilder;
        this.mailService = mailService;
    }

    @Async
    public void sendFormMessage(MessageWrapper wrapper){
        if(Boolean.parseBoolean(env.getProperty("application.mailer.enable-mailer"))){
            List<MessageWrapper> messages = new LinkedList<>();
            messages.add(wrapper);
            sendMessage(messages, "New Message Received");
        }else {
            LOGGER.warning("Mailer disabled, message won't be send");
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void sendUnsentMessages(){
        if(Boolean.parseBoolean(env.getProperty("application.mailer.enable-mailer"))){
            resendMessages();
        }else {
            LOGGER.warning("Mailer disabled, resend messages scheduler will skip the iteration");
        }
    }

    private void resendMessages(){
        LOGGER.info("Unsent messages resend scheduled");
        List<MessageStatus> statuses = new ArrayList<>();
        statuses.add(MessageStatus.NEW);
        statuses.add(MessageStatus.FAILED);
        List<MessageWrapper> messages = messageRepository.findByStatusIsIn(statuses);
        if(messages.size() > 0){
            sendMessage(messages, "New Messages Received (Scheduler)");
        }
    }

    private void sendMessage(List<MessageWrapper> messages, String subject){
        String message = mailContentBuilder.buildFormMessage(subject, messages);
        try {
            mailService.sendHtmlMessage(env.getProperty("application.admin.email"), subject, message);
            updateStatus(messages, MessageStatus.DELIVERED);
        } catch (MailException e) {
            e.printStackTrace();
            updateStatus(messages, MessageStatus.FAILED);
        }
    }

    private void updateStatus(List<MessageWrapper> messages, MessageStatus status){
        for(MessageWrapper message : messages){
            message.setStatus(status);
            messageRepository.save(message);
        }
    }

}
