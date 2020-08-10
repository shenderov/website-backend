package me.shenderov.website.entities;

import java.util.Objects;

public class GenericElement {
    private int position;
    private String iconClass;
    private String label;
    private String value;

    public GenericElement(int position, String iconClass, String label, String value) {
        this.position = position;
        this.iconClass = iconClass;
        this.label = label;
        this.value = value;
    }

    public GenericElement() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericElement)) return false;
        GenericElement that = (GenericElement) o;
        return getPosition() == that.getPosition() &&
                Objects.equals(getIconClass(), that.getIconClass()) &&
                Objects.equals(getLabel(), that.getLabel()) &&
                Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getIconClass(), getLabel(), getValue());
    }
}
