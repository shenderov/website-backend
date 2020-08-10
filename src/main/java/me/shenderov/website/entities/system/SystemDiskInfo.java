package me.shenderov.website.entities.system;

public class SystemDiskInfo {

    private Long diskSizeBytes;
    private Long diskUsedBytes;
    private Long diskAvailableBytes;
    private Float diskCapacityPercent;

    public SystemDiskInfo(Long diskSizeBytes, Long diskUsedBytes, Long diskAvailableBytes, Float diskCapacityPercent) {
        this.diskSizeBytes = diskSizeBytes;
        this.diskUsedBytes = diskUsedBytes;
        this.diskAvailableBytes = diskAvailableBytes;
        this.diskCapacityPercent = diskCapacityPercent;
    }

    public SystemDiskInfo() {
    }

    public Long getDiskSizeBytes() {
        return diskSizeBytes;
    }

    public void setDiskSizeBytes(Long diskSizeBytes) {
        this.diskSizeBytes = diskSizeBytes;
    }

    public Long getDiskUsedBytes() {
        return diskUsedBytes;
    }

    public void setDiskUsedBytes(Long diskUsedBytes) {
        this.diskUsedBytes = diskUsedBytes;
    }

    public Long getDiskAvailableBytes() {
        return diskAvailableBytes;
    }

    public void setDiskAvailableBytes(Long diskAvailableBytes) {
        this.diskAvailableBytes = diskAvailableBytes;
    }

    public Float getDiskCapacityPercent() {
        return diskCapacityPercent;
    }

    public void setDiskCapacityPercent(Float diskCapacityPercent) {
        this.diskCapacityPercent = diskCapacityPercent;
    }

    @Override
    public String toString() {
        return "SystemDiskInfo{" +
                "diskSizeBytes=" + diskSizeBytes +
                ", diskUsedBytes=" + diskUsedBytes +
                ", diskAvailableBytes=" + diskAvailableBytes +
                ", diskCapacityPercent=" + diskCapacityPercent +
                '}';
    }
}
