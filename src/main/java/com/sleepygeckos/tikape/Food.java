package com.sleepygeckos.tikape;

import java.util.List;

public class Food extends Item {

    List<RecipeLine> recipeLines;

    public Food(int id, String name) {
        super(id, name);
    }

    public void addRecipeLine(RecipeLine recipeLine) {
        this.recipeLines.add(recipeLine);
    }

    public void addRecipeLines(List<RecipeLine> recipeLinesToAdd) {
        for (RecipeLine recipeLine : recipeLinesToAdd) {
            this.recipeLines.add(recipeLine);
        }
    }

    public void ClearRecipeLines() {
        this.recipeLines.clear();
    }
}
