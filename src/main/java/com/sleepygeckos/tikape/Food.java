package com.sleepygeckos.tikape;

import java.util.ArrayList;
import java.util.List;

public class Food extends Item {

    List<RecipeLine> recipeLines;

    public Food(int id, String name) {
        super(id, name);
        this.recipeLines = new ArrayList<>();
    }

    public void addRecipeLine(RecipeLine recipeLine) {
        this.recipeLines.add(recipeLine);
    }

    public void addRecipeLines(List<RecipeLine> recipeLinesToAdd) {
        for (RecipeLine recipeLine : recipeLinesToAdd) {
            this.recipeLines.add(recipeLine);
        }
    }
    
    public List<RecipeLine> getRecipeLines(){
        return recipeLines;
    }

    public void ClearRecipeLines() {
        this.recipeLines.clear();
    }
}
