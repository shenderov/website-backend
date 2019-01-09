package me.shenderov.website.dao;

import me.shenderov.website.entities.GenericElement;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "block")
public class SkillsBlock extends Block {

    private List<GenericElement> skills;

    public List<GenericElement> getSkills() {
        return skills;
    }

    public void setSkills(List<GenericElement> skills) {
        this.skills = skills;
    }
}
