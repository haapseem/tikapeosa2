package com.sleepygeckos.tikape.database;

import com.sleepygeckos.tikape.Food;
import com.sleepygeckos.tikape.Ingredient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sleepygeckos.tikape.Item;
import com.sleepygeckos.tikape.RecipeLine;

/**
 *
 * @author jkesala
 */
public class DbHandler {

    Connection connection;

    public DbHandler() throws SQLException {
        //Assuming db file is in the root folder
        this.connection = DriverManager.getConnection("jdbc:sqlite:db.db");
    }

    /**
     *
     * @param tableName
     * @return List<Item>
     * @throws SQLException
     *
     * Returns a List of Items, which will be either Food or Ingredient objects
     * depending on which table's name was given as parameter.
     */
    public List<Item> findAllItems(String tableName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName);

        ResultSet resultSet = statement.executeQuery();
        List<Item> foundItems = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");

            if (tableName.equals("Food")) {
                foundItems.add(new Food(id, name));
            } else if (tableName.equals("Ingredient")) {
                foundItems.add(new Ingredient(id, name));
            }
        }

        return foundItems;
    }

    //Pro tip: This may be buggy
    public List<RecipeLine> getFoodRecipeLines(int foodId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM FoodIngredient\n"
                + "  INNER JOIN Ingredient ON Ingredient.id = FoodIngredient.ingredientId\n"
                + "  WHERE FoodIngredient.foodId = ?");
        statement.setInt(1, foodId);

        ResultSet resultSet = statement.executeQuery();
        List<RecipeLine> foundItems = new ArrayList<>();

        while (resultSet.next()) {
            Ingredient ingredient = new Ingredient(foodId, resultSet.getString("name"));
            String order = resultSet.getString("orderName");
            String amount = resultSet.getString("amount");
            String text = resultSet.getString("recipe");
            RecipeLine recipeLine = new RecipeLine(ingredient, order, amount, text);
            foundItems.add(recipeLine);
        }

        statement.close();
        resultSet.close();

        return foundItems;
    }

    //Returns null if item not found
    public Item findItem(String tableName, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM ? WHERE id = ?");
        statement.setString(1, tableName);
        statement.setInt(2, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            //Toimiiko?
            statement.close();
            resultSet.close();

            if (tableName.equals("Food")) {
                return new Food(id, name);
            } else if (tableName.equals("Ingredient")) {
                return new Ingredient(id, name);
            }
        }

        return null;
    }

    //Adds a Food or Ingredient depending on given table parameter. The name
    //parameter is the name of the food or ingredient, not a Food or Ingredient
    //object, as we don't create objects when adding to the database.
    public void addItem(String tableName, String itemName) throws SQLException {
        if (tableName.equals("Food")) {
            PreparedStatement addFood = connection.prepareStatement("INSERT INTO Food (name) VALUES (?)");
            addFood.setString(1, itemName);
            addFood.executeUpdate();

            addFood.close();
        }

        if (tableName.equals("Ingredient")) {
            PreparedStatement addIngredient = connection.prepareStatement("INSERT INTO Ingredient (name) VALUES (?)");
            addIngredient.setString(1, itemName);
            addIngredient.executeUpdate();

            addIngredient.close();
        }
    }

    public void addRecipeLineToFood(int foodId, RecipeLine recipeLine) throws SQLException {
        PreparedStatement addFoodIngredient = connection.prepareStatement("INSERT INTO FoodIngredient (orderName, amount, recipe, ingredientId, foodId) VALUES (?, ?, ?, ?, ?)");
        //Variables named according to their column name in the database.
        String orderName = recipeLine.getOrder();
        String amount = recipeLine.getAmount();
        String recipe = recipeLine.getText();
        int ingredientId = recipeLine.getIngredient().getId();

        addFoodIngredient.setString(1, orderName);
        addFoodIngredient.setString(2, amount);
        addFoodIngredient.setString(3, recipe);
        addFoodIngredient.setInt(4, ingredientId);
        addFoodIngredient.setInt(5, foodId);

        addFoodIngredient.executeUpdate();

        addFoodIngredient.close();
    }

    //Removes a Food or Ingredient depending on the tableName parameter. Also
    //removes the given item from the FoodIngredient table to keep db consistent.
    public void removeItem(String tableName, int id) throws SQLException {
        if (tableName.equals("Food")) {

            PreparedStatement deleteFood = connection.prepareStatement("BEGIN TRANSACTION\n"
                    + "DELETE FROM Food WHERE id = ?;\n"
                    + "DELETE FROM FoodIngredient WHERE foodId = ?;\n"
                    + "COMMIT");

            deleteFood.setInt(1, id);
            deleteFood.setInt(2, id);
            deleteFood.executeUpdate();

            deleteFood.close();
        }

        if (tableName.equals("Ingredient")) {

            PreparedStatement deleteIngredient = connection.prepareStatement("BEGIN TRANSACTION\n"
                    + "DELETE FROM Ingredient WHERE id = ?;\n"
                    + "DELETE FROM FoodIngredient WHERE ingredientId = ?;\n"
                    + "COMMIT");
            deleteIngredient.setInt(1, id);
            deleteIngredient.setInt(2, id);
            deleteIngredient.executeUpdate();

            deleteIngredient.close();



        }
    }
}
