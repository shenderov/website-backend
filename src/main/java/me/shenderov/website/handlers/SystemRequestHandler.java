package me.shenderov.website.handlers;

import me.shenderov.website.config.SettingsConfig;
import me.shenderov.website.dao.settings.AbstractApplicationSettings;
import me.shenderov.website.dao.settings.ApplicationSettings;
import me.shenderov.website.dao.settings.EmailSettings;
import me.shenderov.website.entities.system.SystemCpuInfo;
import me.shenderov.website.entities.system.SystemDiskInfo;
import me.shenderov.website.entities.system.SystemMemoryInfo;
import me.shenderov.website.interfaces.ISystemRequestHandler;
import me.shenderov.website.repositories.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.logging.Logger;

public class SystemRequestHandler implements ISystemRequestHandler {

    private final static Logger LOGGER = Logger.getLogger(SettingsConfig.class.getName());

    private SettingsConfig settingsConfig = new SettingsConfig();

    @Autowired
    private SettingsRepository settingsRepository;

    public SystemDiskInfo getDiskInfo() {
        SystemDiskInfo diskInfo = new SystemDiskInfo();
        File[] drives = File.listRoots();
        if (drives != null && drives.length > 0) {
            for (File drive : drives) {
                diskInfo.setDiskSizeBytes(drive.getTotalSpace());
                diskInfo.setDiskUsedBytes(drive.getTotalSpace()-drive.getUsableSpace());
                diskInfo.setDiskAvailableBytes(drive.getUsableSpace());
                diskInfo.setDiskCapacityPercent((drive.getUsableSpace() * 100.0f) / drive.getTotalSpace());
            }
        }
        return diskInfo;
    }

    public SystemMemoryInfo getMemoryInfo() {
        SystemMemoryInfo memoryInfo = new SystemMemoryInfo();
        memoryInfo.setMemoryFreeBytes(Runtime.getRuntime().freeMemory());
        memoryInfo.setMemoryTotalBytes(Runtime.getRuntime().totalMemory());

        //TODO
        memoryInfo.setSwapTotalBytes(22222222222L);
        memoryInfo.setSwapFreeBytes(11111111111L);
        return memoryInfo;
    }

    public SystemCpuInfo getCpuInfo() {
        SystemCpuInfo cpuInfo = new SystemCpuInfo();
        cpuInfo.setCpuNumberOfCores(Runtime.getRuntime().availableProcessors());

        //TODO
        cpuInfo.setCpuModel("Dual-Core Intel Core i5");
        cpuInfo.setCpuSpeed(2700.00F);
        cpuInfo.setCpuUsage(34.5F);
        return cpuInfo;
    }

    public AbstractApplicationSettings getSettings(String id) throws Exception {
        if(!id.equals("application") && !id.equals("email"))
            throw new RuntimeException("Only following IDs accepted: 'application', 'email");
        if(!settingsRepository.existsById(id) && id.equals("email")){
            settingsConfig.setEmailSettings(settingsRepository);
        } else if(!settingsRepository.existsById(id) && id.equals("application")){
            settingsConfig.setApplicationSettings(settingsRepository);
        }
        if(id.equals("email")){
            EmailSettings emailSettingsDb = (EmailSettings) settingsRepository.findById(id).orElseThrow(() -> new Exception(String.format("Settings with id: %s is not found", id)));
            EmailSettings emailSettingsProps = settingsConfig.getEmailSettingsFromProperties();
            if(!emailSettingsDb.equals(emailSettingsProps)){
                throw new RuntimeException("Email settings retrieved from DB not equals to properties");
            } else {
                return emailSettingsDb;
            }
        } else {
            ApplicationSettings applicationSettingsDb = (ApplicationSettings) settingsRepository.findById(id).orElseThrow(() -> new Exception(String.format("Settings with id: %s is not found", id)));
            ApplicationSettings applicationSettingsProps = settingsConfig.getApplicationSettingsFromProperties();
            if(!applicationSettingsDb.equals(applicationSettingsProps)){
                if(applicationSettingsDb.getEnableRecaptcha() == applicationSettingsProps.getEnableRecaptcha() &&
                applicationSettingsDb.getRecaptchaSecretKey().equals(applicationSettingsProps.getRecaptchaSecretKey()) &&
                applicationSettingsDb.getAdminEmail().equals(applicationSettingsProps.getAdminEmail()) &&
                applicationSettingsDb.getRunDataInitializerOnStartup() == applicationSettingsProps.getRunDataInitializerOnStartup() &&
                applicationSettingsDb.getUpdateFromJsonOnOnStartup() == applicationSettingsProps.getUpdateFromJsonOnOnStartup()){
                    LOGGER.warning("Email settings DB version is not equals to properties...");
                    applicationSettingsDb.setEnableMailerService(applicationSettingsProps.getEnableMailerService());
                    return applicationSettingsDb;
                }else {
                    throw new RuntimeException("Application settings retrieved from DB not equals to properties");
                }
            } else {
                return applicationSettingsDb;
            }
        }
    }

    public AbstractApplicationSettings updateSettings(AbstractApplicationSettings settings) throws Exception {
        if(!settings.getId().equals("application") && !settings.getId().equals("email"))
            throw new RuntimeException("Only following IDs accepted: 'application', 'email");
        if(settingsRepository.existsById(settings.getId()))
            settingsRepository.save(settings);
        else
            settingsRepository.insert(settings);
        if(settings.getId().equals("email")){
            settingsConfig.setEmailSettings(settingsRepository);
            EmailSettings emailSettingsDb = (EmailSettings) settingsRepository.findById(settings.getId()).orElseThrow(() -> new Exception(String.format("Settings with id: %s is not found", settings.getId())));
            EmailSettings emailSettingsProps = settingsConfig.getEmailSettingsFromProperties();
            if(!emailSettingsDb.equals(emailSettingsProps))
                throw new RuntimeException("Email settings retrieved from DB not equals to properties");
            else
                return emailSettingsDb;
        } else {
            settingsConfig.setApplicationSettings(settingsRepository);
            ApplicationSettings applicationSettingsDb = (ApplicationSettings) settingsRepository.findById(settings.getId()).orElseThrow(() -> new Exception(String.format("Settings with id: %s is not found", settings.getId())));
            ApplicationSettings applicationSettingsProps = settingsConfig.getApplicationSettingsFromProperties();
            if(applicationSettingsDb.equals(applicationSettingsProps)){
                if(applicationSettingsDb.getEnableMailerService()){
                    applicationSettingsDb.setEnableMailerService(applicationSettingsProps.getEnableMailerService());
                }
                return applicationSettingsDb;
            } else {
                throw new RuntimeException("Application settings retrieved from DB not equals to properties");
            }
        }
    }

}
