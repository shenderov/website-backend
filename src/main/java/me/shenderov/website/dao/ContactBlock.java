package me.shenderov.website.dao;

import me.shenderov.website.entities.GenericElement;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "block")
public class ContactBlock extends Block {

    private List<GenericElement> formLabels;

    public List<GenericElement> getFormLabels() {
        return formLabels;
    }

    public void setFormLabels(List<GenericElement> formLabels) {
        this.formLabels = formLabels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactBlock)) return false;
        ContactBlock that = (ContactBlock) o;
        return Objects.equals(getFormLabels(), that.getFormLabels());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFormLabels());
    }
}
