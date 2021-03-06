package com.sleepygeckos.tikape;

import com.sleepygeckos.tikape.database.DbHandler;
import com.sleepygeckos.tikape.handlers.FoodHandler;
import com.sleepygeckos.tikape.handlers.IngredientHandler;
import java.sql.SQLException;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) throws SQLException, Exception {
        DbHandler dbhandler = new DbHandler();
        FoodHandler foodHandler = new FoodHandler(dbhandler);
        IngredientHandler ingredientHandler = new IngredientHandler(dbhandler);
        foodHandler.generateRecipeLines();

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("foods", foodHandler.getItems());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/ingredients", (req, res) -> {
            HashMap ingredientMap = new HashMap<>();
            ingredientMap.put("ingredients", ingredientHandler.getItems());
            return new ModelAndView(ingredientMap, "ingredients");
        }, new ThymeleafTemplateEngine());

        Spark.get("/createsmoothie", (req, res) -> {
            HashMap foodMap = new HashMap<>();
            foodMap.put("foods", foodHandler.getItems());
            foodMap.put("ingredients", ingredientHandler.getItems());
            return new ModelAndView(foodMap, "createsmoothie");
        }, new ThymeleafTemplateEngine());

//        Spark.post("/deleteingredient", (req, res) -> {
//            ingredientHandler.req.queryParams(queryParam)
//
//            res.redirect("/createsmoothie");
//            return "";
//        });
        Spark.get("/food/:id", (req, res) -> {
            HashMap recipes = new HashMap<>();
            recipes.put("food", foodHandler.getFood(Integer.parseInt(req
                    .params(":id"))));
            recipes.put("lines", foodHandler.getRecipeLines(Integer
                    .parseInt(req.params(":id"))));
            return new ModelAndView(recipes, "food");
        }, new ThymeleafTemplateEngine());

        //delete smoothie
        Spark.get("/deletesmoothie/:id", (req, res) -> {
            int index = Integer.parseInt(req.params(":id"));
            try {
                dbhandler.removeItem("Food", index);
                foodHandler.resetList();
                ingredientHandler.resetList();
                foodHandler.generateRecipeLines();
                res.redirect("/createsmoothie");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        });

//add smoothie
        Spark.post("/addsmoothie", (req, res) -> {
            foodHandler.addItem("Food", req.queryParams("name"));

            res.redirect("/createsmoothie");
            return "";
        });

// delete an ingredient
        Spark.get("/deleteingredient/:id", (req, res) -> {
            int index = Integer.parseInt(req.params(":id"));
            try {
                dbhandler.removeItem("Ingredient", index);
                foodHandler.resetList();
                ingredientHandler.resetList();
                foodHandler.generateRecipeLines();
                res.redirect("/ingredients");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        });

// add an ingredient
        Spark.post("/addingredient", (req, res) -> {
            ingredientHandler.addItem("Ingredient", req.queryParams("name"));

            res.redirect("/ingredients");
            return "";
        });

// add an ingredient for a smoothie
        Spark.post("/addIngredientForSmoothie", (req, res) -> {
            int x = Integer.parseInt(req.queryParams("smoothie"));
            int y = Integer.parseInt(req.queryParams("ingredient"));
            String a = req.queryParams("order");
            String b = req.queryParams("amount");
            String c = req.queryParams("recipe");

            foodHandler.addRecipeLine(x, y, a, c, b);

            foodHandler.resetList();
            ingredientHandler.resetList();
            foodHandler.generateRecipeLines();

            res.redirect("/createsmoothie");
            return "";
        });

    }

}
