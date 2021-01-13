package me.shenderov.website.entities;

import me.shenderov.website.dao.Block;

public class BlockPreview extends Block {

    private String classType;

    @Override
    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    @Override
    public String toString() {
        return "BlockPreview{}";
    }
}
