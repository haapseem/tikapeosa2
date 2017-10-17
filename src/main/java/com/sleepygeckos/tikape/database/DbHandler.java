package com.sleepygeckos.tikape.database;

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

    public DbHandler() {
        //Assuming db file is in the root folder
        this.connection = DriverManager.getConnection("jdbc:sqlite:vuokraamo.db");
    }
    
    public List<Item> findAllItems(String tableName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM ?");
        statement.setString(1, tableName);
        
        ResultSet resultSet = statement.executeQuery();
        List<Item> foundItems = new ArrayList<>();
        
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            
            foundItems.add(new Item(id, name));
        }
        
        return foundItems;
    }

}