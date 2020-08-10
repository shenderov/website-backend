package me.shenderov.website.interfaces;

import me.shenderov.website.entities.system.SystemCpuInfo;
import me.shenderov.website.entities.system.SystemDiskInfo;
import me.shenderov.website.entities.system.SystemMemoryInfo;

public interface ISystemRequestHandler {

    SystemDiskInfo getDiskInfo();

    SystemMemoryInfo getMemoryInfo();

    SystemCpuInfo getCpuInfo();

}
