/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sleepygeckos.tikape.handlers;

import com.sleepygeckos.tikape.Food;
import com.sleepygeckos.tikape.Ingredient;
import com.sleepygeckos.tikape.Item;
import com.sleepygeckos.tikape.RecipeLine;
import com.sleepygeckos.tikape.database.DbHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sleepingduck
 */
public class FoodHandler extends ItemHandler {

    public FoodHandler(DbHandler dh) throws Exception {
        super("Food", dh);
    }

    public List<RecipeLine> getRecipeLines(int id) {
        List<RecipeLine> lines = new ArrayList<>();
        List<Item> items = super.getItems();
        for (Item x : items) {
            if (x.getId() == id) {
                Food f = (Food) x;
                System.out.println(" " + f.getRecipeLines());
                lines.addAll(f.getRecipeLines());
            }
        }
        return lines;
    }

    public Food getFood(int i) {
        for (Item x : super.getItems()) {
            if (x.getId() == i) {
                return (Food) x;
            }
        }
        return new Food(-1, "no food");
    }

    public void generateRecipeLines() throws SQLException {
        DbHandler dh = new DbHandler();
        super.getItems().stream().forEach(x -> {
            System.out.println(x.getName());

            try {
                List<RecipeLine> ing = dh.getFoodRecipeLines(x.getId());
                System.out.println("\nlist:" + ing.toString());
                Food f = (Food) x;
                if (ing != null && ing.size() > 0) {
                    f.addRecipeLines(ing);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void addRecipeLine(int foodId, int ingredientId, String order, String text, String amount) throws Exception {
        Ingredient ingredient = (Ingredient) (new IngredientHandler(super.getDh())).getItem(ingredientId);
        RecipeLine recipeLine = new RecipeLine(ingredient, order, amount, text);
        
        super.getDh().addRecipeLineToFood(foodId, recipeLine);
    }

}
