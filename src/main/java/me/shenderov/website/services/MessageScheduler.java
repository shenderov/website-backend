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

    @Autowired
    private Environment env;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Autowired
    private MailService mailService;

    @Async
    public void sendFormMessage(MessageWrapper wrapper){
        List<MessageWrapper> messages = new LinkedList<>();
        messages.add(wrapper);
        sendMessage(messages, "New Message Received");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void sendUnsentMessages(){
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
