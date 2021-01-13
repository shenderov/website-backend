package me.shenderov.website.entities;

import java.util.Date;
import java.util.Objects;

public class TimelineElement {
    private Date startDate;
    private Date endDate;
    private boolean noEndDate;
    private String positionName;
    private String organisation;
    private String location;
    private String description;

    public TimelineElement(Date startDate, Date endDate, boolean noEndDate, String positionName, String organisation, String location, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.noEndDate = noEndDate;
        this.positionName = positionName;
        this.organisation = organisation;
        this.location = location;
        this.description = description;
    }

    public TimelineElement() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimelineElement)) return false;
        TimelineElement that = (TimelineElement) o;
        return isNoEndDate() == that.isNoEndDate() &&
                Objects.equals(getStartDate(), that.getStartDate()) &&
                Objects.equals(getEndDate(), that.getEndDate()) &&
                Objects.equals(getPositionName(), that.getPositionName()) &&
                Objects.equals(getOrganisation(), that.getOrganisation()) &&
                Objects.equals(getLocation(), that.getLocation()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDate(), getEndDate(), isNoEndDate(), getPositionName(), getOrganisation(), getLocation(), getDescription());
    }
}
