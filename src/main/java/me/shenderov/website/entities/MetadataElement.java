package me.shenderov.website.entities;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class MetadataElement {

    @NotNull
    private String name;
    private String content;

    public MetadataElement(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public MetadataElement() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetadataElement)) return false;
        MetadataElement that = (MetadataElement) o;
        return getName().equals(that.getName()) &&
                Objects.equals(getContent(), that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getContent());
    }

    @Override
    public String toString() {
        return "MetadataElement{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
