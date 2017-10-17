/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sleepygeckos.tikape.handlers;

import com.sleepygeckos.tikape.Food;
import com.sleepygeckos.tikape.database.DbHandler;
import java.sql.SQLException;

/**
 *
 * @author sleepingduck
 */
public class FoodHandler extends ItemHandler {

    public FoodHandler() throws Exception{
        super("Food");
    }
    
    public void generateRecipeLines() throws SQLException {
        DbHandler dh = new DbHandler();
        super.getItems().stream().forEach(x -> {
            try{
            dh.getFoodRecipeLines(x.getId()).stream().forEach(y -> {
                ((Food)x).addRecipeLine(y);
            });}catch(Exception e){}
        });
    }
    
}
