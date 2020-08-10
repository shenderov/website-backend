package me.shenderov.website.handlers;

import me.shenderov.website.entities.system.SystemCpuInfo;
import me.shenderov.website.entities.system.SystemDiskInfo;
import me.shenderov.website.entities.system.SystemMemoryInfo;
import me.shenderov.website.interfaces.ISystemRequestHandler;

import java.io.File;

public class SystemRequestHandler implements ISystemRequestHandler {

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


}
