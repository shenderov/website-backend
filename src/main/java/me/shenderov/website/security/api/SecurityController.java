package me.shenderov.website.security.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.logging.Logger;

@RequestMapping(path = "/security")
@RestController
public class SecurityController {

    private final static Logger LOGGER = Logger.getLogger(SecurityController.class.getName());

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getDiskInfo(HttpServletRequest request, Principal principal) {
        LOGGER.info(request.getRemoteAddr()+"/user: | " + principal.getName());
        return principal.getName();
    }

}
