package me.shenderov.website.entities;

public class SystemMemoryInfo {

    private Long memoryTotalBytes;
    private Long memoryFreeBytes;
    private Long swapTotalBytes;
    private Long swapFreeBytes;

    public SystemMemoryInfo(Long memoryTotalBytes, Long memoryFreeBytes, Long swapTotalBytes, Long swapFreeBytes) {
        this.memoryTotalBytes = memoryTotalBytes;
        this.memoryFreeBytes = memoryFreeBytes;
        this.swapTotalBytes = swapTotalBytes;
        this.swapFreeBytes = swapFreeBytes;
    }

    public SystemMemoryInfo() {
    }

    public Long getMemoryTotalBytes() {
        return memoryTotalBytes;
    }

    public void setMemoryTotalBytes(Long memoryTotalBytes) {
        this.memoryTotalBytes = memoryTotalBytes;
    }

    public Long getMemoryFreeBytes() {
        return memoryFreeBytes;
    }

    public void setMemoryFreeBytes(Long memoryFreeBytes) {
        this.memoryFreeBytes = memoryFreeBytes;
    }

    public Long getSwapTotalBytes() {
        return swapTotalBytes;
    }

    public void setSwapTotalBytes(Long swapTotalBytes) {
        this.swapTotalBytes = swapTotalBytes;
    }

    public Long getSwapFreeBytes() {
        return swapFreeBytes;
    }

    public void setSwapFreeBytes(Long swapFreeBytes) {
        this.swapFreeBytes = swapFreeBytes;
    }

    @Override
    public String toString() {
        return "SystemMemoryInfo{" +
                "memoryTotalBytes=" + memoryTotalBytes +
                ", memoryFreeBytes=" + memoryFreeBytes +
                ", swapTotalBytes=" + swapTotalBytes +
                ", swapFreeBytes=" + swapFreeBytes +
                '}';
    }
}
