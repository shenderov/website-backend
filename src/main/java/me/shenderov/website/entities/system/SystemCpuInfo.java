package me.shenderov.website.entities.system;

public class SystemCpuInfo {

    private String cpuModel;
    private Integer cpuNumberOfCores;
    private Float cpuSpeed;
    private Float cpuUsage;

    public SystemCpuInfo(String cpuModel, Integer cpuNumberOfCores, Float cpuSpeed, Float cpuUsage) {
        this.cpuModel = cpuModel;
        this.cpuNumberOfCores = cpuNumberOfCores;
        this.cpuSpeed = cpuSpeed;
        this.cpuUsage = cpuUsage;
    }

    public SystemCpuInfo() {
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public Integer getCpuNumberOfCores() {
        return cpuNumberOfCores;
    }

    public void setCpuNumberOfCores(Integer cpuNumberOfCores) {
        this.cpuNumberOfCores = cpuNumberOfCores;
    }

    public Float getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(Float cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public Float getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(Float cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    @Override
    public String toString() {
        return "SystemCpuInfo{" +
                "cpuModel='" + cpuModel + '\'' +
                ", cpuNumberOfCores=" + cpuNumberOfCores +
                ", cpuSpeed=" + cpuSpeed +
                ", cpuUsage=" + cpuUsage +
                '}';
    }
}
