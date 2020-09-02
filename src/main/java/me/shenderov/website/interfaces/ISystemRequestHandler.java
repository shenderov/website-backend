package me.shenderov.website.interfaces;

import me.shenderov.website.dao.settings.AbstractApplicationSettings;
import me.shenderov.website.entities.system.SystemCpuInfo;
import me.shenderov.website.entities.system.SystemDiskInfo;
import me.shenderov.website.entities.system.SystemMemoryInfo;

public interface ISystemRequestHandler {

    SystemDiskInfo getDiskInfo();

    SystemMemoryInfo getMemoryInfo();

    SystemCpuInfo getCpuInfo();

    AbstractApplicationSettings getSettings(String id) throws Exception;

    AbstractApplicationSettings updateSettings(AbstractApplicationSettings settings) throws Exception;

}
