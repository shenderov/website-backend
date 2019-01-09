package me.shenderov.website.entities;

public class MediaLink {
    private int position;
    private String iconClass;
    private String url;

    public MediaLink(String iconClass, String url) {
        this.iconClass = iconClass;
        this.url = url;
    }

    public MediaLink(int position, String iconClass, String url) {
        this.position = position;
        this.iconClass = iconClass;
        this.url = url;
    }

    public MediaLink() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
