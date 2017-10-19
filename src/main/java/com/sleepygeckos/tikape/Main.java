package com.sleepygeckos.tikape;

import com.sleepygeckos.tikape.database.DbHandler;
import com.sleepygeckos.tikape.handlers.FoodHandler;
import com.sleepygeckos.tikape.handlers.IngredientHandler;
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
        IngredientHandler ingredientHandler = new IngredientHandler(dbhandler);

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("foods", foodHandler.getItems());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/ingredients", (req, res) -> {
            System.out.println("asd");
            HashMap ingredientMap = new HashMap<>();
            ingredientMap.put("ingredients", ingredientHandler.getItems());
            return new ModelAndView(ingredientMap, "ingredients");
        }, new ThymeleafTemplateEngine());

        Spark.get("/createsmoothie", (req, res) -> {
            HashMap foodMap = new HashMap<>();
            foodMap.put("foods", foodHandler.getItems());
            return new ModelAndView(foodMap, "createsmoothie");
        }, new ThymeleafTemplateEngine());

    }

}
