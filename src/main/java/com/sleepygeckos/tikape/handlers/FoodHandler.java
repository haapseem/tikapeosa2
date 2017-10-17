/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sleepygeckos.tikape.handlers;

import com.sleepygeckos.tikape.database.DbHandler;

/**
 *
 * @author sleepingduck
 */
public class FoodHandler extends ItemHandler {

    public FoodHandler() throws Exception{
        super("Food");
    }
    
    public void generateRecipeLines() throws Exception {
        DbHandler dh = new DbHandler();
        super.getItems().stream().forEach(x -> {
            dh.getFoodRecipeLines(x.getId()).stream().foreach(y -> {
                x.addRecipeLine(y);
            });
        });
    }
    
}
