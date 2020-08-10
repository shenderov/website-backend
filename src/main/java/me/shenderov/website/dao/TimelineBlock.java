package me.shenderov.website.dao;

import me.shenderov.website.entities.TimelineElement;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "block")
public class TimelineBlock extends Block {

    private List<TimelineElement> elements;

    public List<TimelineElement> getElements() {
        return elements;
    }

    public void setElements(List<TimelineElement> elements) {
        this.elements = elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimelineBlock)) return false;
        TimelineBlock that = (TimelineBlock) o;
        return Objects.equals(getElements(), that.getElements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getElements());
    }
}
