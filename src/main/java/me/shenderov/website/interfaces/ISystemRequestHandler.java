package me.shenderov.website.interfaces;

import me.shenderov.website.entities.SystemCpuInfo;
import me.shenderov.website.entities.SystemDiskInfo;
import me.shenderov.website.entities.SystemMemoryInfo;

public interface ISystemRequestHandler {

    SystemDiskInfo getDiskInfo();

    SystemMemoryInfo getMemoryInfo();

    SystemCpuInfo getCpuInfo();

}
