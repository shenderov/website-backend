package me.shenderov.website.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private Environment env;


}
