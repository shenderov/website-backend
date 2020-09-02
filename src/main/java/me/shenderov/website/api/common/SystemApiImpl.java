package me.shenderov.website.api.common;

import me.shenderov.website.dao.settings.AbstractApplicationSettings;
import me.shenderov.website.entities.system.SystemCpuInfo;
import me.shenderov.website.entities.system.SystemDiskInfo;
import me.shenderov.website.entities.system.SystemMemoryInfo;
import me.shenderov.website.interfaces.ISystemRequestHandler;
import me.shenderov.website.interfaces.IYandexMetricaHandler;
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
    private final IYandexMetricaHandler metricaHandler;

    @Autowired
    public SystemApiImpl(ISystemRequestHandler requestHandler, IYandexMetricaHandler metricaHandler) {
        this.requestHandler = requestHandler;
        this.metricaHandler = metricaHandler;
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

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping(HttpServletRequest request) {
        LOGGER.info(request.getRemoteAddr()+"/ping|");
        return "ok";
    }

    @RequestMapping(value = "/getSettings", method = RequestMethod.GET)
    public AbstractApplicationSettings getSettings(@RequestParam("id") String id, HttpServletRequest request) throws Exception {
        LOGGER.info(request.getRemoteAddr()+"/getSettings|");
        return requestHandler.getSettings(id);
    }

    @RequestMapping(value = "/updateSettings", method = RequestMethod.POST)
    public AbstractApplicationSettings updateSettings(@RequestBody AbstractApplicationSettings settings, HttpServletRequest request) throws Exception {
        LOGGER.info(request.getRemoteAddr()+"/updateSettings|");
        return requestHandler.updateSettings(settings);
    }

    @RequestMapping(value = "/getYMData", method = RequestMethod.GET)
    public String getYMData(@RequestParam(value = "type") String type, HttpServletRequest request) throws Exception {
        LOGGER.info(request.getRemoteAddr()+"/getYMData|");
        if(!Boolean.parseBoolean(System.getProperty("application.statistics.ym.enable")))
            throw new RuntimeException("Yandex Metrica is disabled");
        String res;
        switch (type){
            case "new_users":
                res = metricaHandler.getNewUsersStatistics();
                break;
            case "users":
                res = metricaHandler.getUsersStatistics();
                break;
            case "traffic_source":
                res = metricaHandler.getTrafficSourceStatistics();
                break;
            case "geography":
                res = metricaHandler.getGeographyStatistics();
                break;
            case "urls":
                res = metricaHandler.getUrlsStatistics();
                break;
            default:
                throw new RuntimeException("Wrong YM statistics type");
        }
        System.out.println(res);
        return res;
    }
}
