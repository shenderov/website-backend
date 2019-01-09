package me.shenderov.website.entities;

import java.util.Date;

public class TimelineElement {
    private Date startDate;
    private Date endDate;
    private boolean noEndDate;
    private String positionName;
    private String organisation;
    private String location;
    private String description;

    public boolean isNoEndDate() {
        return noEndDate;
    }

    public void setNoEndDate(boolean noEndDate) {
        this.noEndDate = noEndDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
