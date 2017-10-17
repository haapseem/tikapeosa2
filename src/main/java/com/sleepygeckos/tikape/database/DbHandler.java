package com.sleepygeckos.tikape.database;

import com.sleepygeckos.tikape.Food;
import com.sleepygeckos.tikape.Ingredient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sleepygeckos.tikape.Item;

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
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM ?");
        statement.setString(1, tableName);
        
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

}