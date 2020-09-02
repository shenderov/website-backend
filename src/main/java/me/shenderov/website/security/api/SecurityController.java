package me.shenderov.website.security.api;

import me.shenderov.website.repositories.UserRepository;
import me.shenderov.website.security.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.logging.Logger;

@RequestMapping(path = "/auth")
@RestController
public class SecurityController {

    private final static Logger LOGGER = Logger.getLogger(SecurityController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    public User getCurrentUser(Principal principal) {
        return userRepository.findUserByUsername(principal.getName());
    }

}
