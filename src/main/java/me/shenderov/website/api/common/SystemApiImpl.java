package me.shenderov.website.api.common;

import me.shenderov.website.entities.SystemCpuInfo;
import me.shenderov.website.entities.SystemDiskInfo;
import me.shenderov.website.entities.SystemMemoryInfo;
import me.shenderov.website.interfaces.ISystemRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@RequestMapping(path = "/system")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RestController
public class SystemApiImpl {

    private final static Logger LOGGER = Logger.getLogger(SystemApiImpl.class.getName());

    private final ISystemRequestHandler requestHandler;

    @Autowired
    public SystemApiImpl(ISystemRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @RequestMapping(value = "/getDiskInfo", method = RequestMethod.GET)
    public SystemDiskInfo getDiskInfo(HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/getDiskInfo|");
        return requestHandler.getDiskInfo();
    }

    @RequestMapping(value = "/getMemoryInfo", method = RequestMethod.GET)
    public SystemMemoryInfo getMemoryInfo(HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/getMemoryInfo|");
        return requestHandler.getMemoryInfo();
    }

    @RequestMapping(value = "/getCpuInfo", method = RequestMethod.GET)
    public SystemCpuInfo getCpuInfo(HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/getCpuInfo|");
        return requestHandler.getCpuInfo();
    }
}
