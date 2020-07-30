package me.shenderov.website.dao;

import me.shenderov.website.entities.GenericElement;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "block")
public class SkillsBlock extends Block {

    private List<GenericElement> skills;

    public List<GenericElement> getSkills() {
        return skills;
    }

    public void setSkills(List<GenericElement> skills) {
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkillsBlock)) return false;
        SkillsBlock that = (SkillsBlock) o;
        return Objects.equals(getSkills(), that.getSkills());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSkills());
    }
}
