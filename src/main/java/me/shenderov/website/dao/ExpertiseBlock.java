package me.shenderov.website.dao;

import me.shenderov.website.entities.GenericElement;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.Set;

@Document(collection = "block")
public class ExpertiseBlock extends Block {

    private Set<GenericElement> blocks;

    public Set<GenericElement> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<GenericElement> blocks) {
        this.blocks = blocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpertiseBlock)) return false;
        ExpertiseBlock that = (ExpertiseBlock) o;
        return Objects.equals(getBlocks(), that.getBlocks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBlocks());
    }
}
