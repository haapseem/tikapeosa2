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

//delete smoothie
        Spark.get("/deletesmoothie/:id", (req, res) -> {
//            foodHandler.deleteSmoothie(req.queryParams(""));

            res.redirect("/");
            return "";
        });

//add smoothie
        Spark.post("/addsmoothie", (req, res) -> {
//            foodHandler.addSmoothie(req.queryParams(""));

            res.redirect("/");
            return "";
        });

// delete an ingredient
        Spark.post("/deleteingredient", (req, res) -> {
//            ingredientHandler.addIngredient(req.queryParams(""));

            res.redirect("/");
            return "";
        });

// add an ingredient
        Spark.post("/addingredient", (req, res) -> {
//            ingredientHandler.addIngredient(req.queryParams(""));

            res.redirect("/");
            return "";
        });

// add an ingredient for a smoothie
        Spark.post("/addIngredientForSmoothie/:id", (req, res) -> {
//            foodtHandler.addIngredientForSmoothie(req.queryParams(""));

            res.redirect("/");
            return "";
        });
    }

}
