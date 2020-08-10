package me.shenderov.website.dao.settings;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        property = "classType")
public abstract class AbstractApplicationSettings {

    @Id
    private String id;
    private String classType;

    public AbstractApplicationSettings(String id) {
        this.id = id;
        this.classType = getClass().getTypeName();
    }

    public AbstractApplicationSettings() {
        this.classType = getClass().getTypeName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractApplicationSettings)) return false;
        AbstractApplicationSettings that = (AbstractApplicationSettings) o;
        return getId().equals(that.getId()) &&
                getClassType().equals(that.getClassType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClassType());
    }

    @Override
    public String toString() {
        return "AbstractApplicationSettings{" +
                "id='" + id + '\'' +
                ", classType='" + classType + '\'' +
                '}';
    }
}
