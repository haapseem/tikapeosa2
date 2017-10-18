package com.sleepygeckos.tikape;

import com.sleepygeckos.tikape.database.DbHandler;
import com.sleepygeckos.tikape.handlers.FoodHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) throws SQLException, Exception {
        DbHandler dbhandler = new DbHandler();
        FoodHandler foodHandler = new FoodHandler(dbhandler);
        
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();  
            map.put("foods", foodHandler.getItems());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/fdsa", (req, res) -> {
            HashMap map = new HashMap<>();  
            map.put("foods", foodHandler.getItems());
            return new ModelAndView(map, "ingredients");
        }, new ThymeleafTemplateEngine());
        
        
        
        
    }

}
