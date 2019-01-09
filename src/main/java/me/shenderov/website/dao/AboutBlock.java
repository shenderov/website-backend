package me.shenderov.website.dao;

import me.shenderov.website.entities.GenericElement;
import me.shenderov.website.entities.MediaLink;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "block")
public class AboutBlock extends Block {
    private String name;
    private String positionName;
    private String photo;
    private String description;
    private List<GenericElement> contacts;
    private List<GenericElement> mediaLinks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GenericElement> getContacts() {
        return contacts;
    }

    public void setContacts(List<GenericElement> contacts) {
        this.contacts = contacts;
    }

    public List<GenericElement> getMediaLinks() {
        return mediaLinks;
    }

    public void setMediaLinks(List<GenericElement> mediaLinks) {
        this.mediaLinks = mediaLinks;
    }
}
