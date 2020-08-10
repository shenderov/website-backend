package me.shenderov.website.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        property = "classType")
public abstract class Block {
    @Id
    private String id;
    private String title;
    private Integer position;
    private String classType;

    public Block() {
        classType = getClass().getTypeName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getClassType() {
        return classType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block)) return false;
        Block block = (Block) o;
        return getId().equals(block.getId()) &&
                Objects.equals(getTitle(), block.getTitle()) &&
                Objects.equals(getPosition(), block.getPosition()) &&
                Objects.equals(getClassType(), block.getClassType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getPosition(), getClassType());
    }
}
