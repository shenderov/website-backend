package me.shenderov.website.dao;

import me.shenderov.website.entities.TimelineElement;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "block")
public class TimelineBlock extends Block {

    private List<TimelineElement> elements;

    public List<TimelineElement> getElements() {
        return elements;
    }

    public void setElements(List<TimelineElement> elements) {
        this.elements = elements;
    }
}
