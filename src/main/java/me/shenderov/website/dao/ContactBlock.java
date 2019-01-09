package me.shenderov.website.dao;

import me.shenderov.website.entities.GenericElement;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "block")
public class ContactBlock extends Block {

    private List<GenericElement> formLabels;

    public List<GenericElement> getFormLabels() {
        return formLabels;
    }

    public void setFormLabels(List<GenericElement> formLabels) {
        this.formLabels = formLabels;
    }
}
