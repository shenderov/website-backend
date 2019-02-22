package me.shenderov.website.services;

import me.shenderov.website.dao.MessageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildFormMessage(String headline, List<MessageWrapper> messages) {
        Context context = new Context();
        context.setVariable("headline", headline);
        context.setVariable("messages", messages);
        return templateEngine.process("messages-template", context);
    }
}
