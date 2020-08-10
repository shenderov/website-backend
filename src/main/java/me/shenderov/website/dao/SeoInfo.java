package me.shenderov.website.dao;

import me.shenderov.website.entities.MetadataElement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "seo_info")
public class SeoInfo {
    @Id
    private Integer id;
    private String title;
    private List<MetadataElement> metaData;
    private String footerCopyright;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MetadataElement> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetadataElement> metaData) {
        this.metaData = metaData;
    }

    public String getFooterCopyright() {
        return footerCopyright;
    }

    public void setFooterCopyright(String footerCopyright) {
        this.footerCopyright = footerCopyright;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeoInfo)) return false;
        SeoInfo seoInfo = (SeoInfo) o;
        return getId().equals(seoInfo.getId()) &&
                Objects.equals(getTitle(), seoInfo.getTitle()) &&
                Objects.equals(getMetaData(), seoInfo.getMetaData()) &&
                Objects.equals(getFooterCopyright(), seoInfo.getFooterCopyright());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getMetaData(), getFooterCopyright());
    }

    @Override
    public String toString() {
        return "SeoInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", metaData=" + metaData.toString() +
                ", footerCopyright='" + footerCopyright + '\'' +
                '}';
    }
}
