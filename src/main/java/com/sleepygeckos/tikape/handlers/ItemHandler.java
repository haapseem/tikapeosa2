/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sleepygeckos.tikape.handlers;

import com.sleepygeckos.tikape.Item;
import com.sleepygeckos.tikape.database.DbHandler;
import java.util.List;

/**
 *
 * @author sleepingduck
 */
public abstract class ItemHandler {
    
    private List<Item> items;
    private DbHandler dh;
    private String tableName;
    
    public ItemHandler(String tableName, DbHandler dh) throws Exception{
        this.dh = dh;
        this.items = dh.findAllItems(tableName);
        this.tableName = tableName;
    }
    
    public void resetList() throws Exception{
        this.items = dh.findAllItems(this.tableName);
    }

    public List<Item> getItems() {
        return items;
    }
    
    public Item getItem(int itemId) {
        //This is where using a hashmap would be a good idea
        for (Item item : items) {
            if (item.getId() == itemId) {
                return item;                
            }
        }
        
        return null;
    }

    public DbHandler getDh() {
        return dh;
    }

    public void setDh(DbHandler dh) {
        this.dh = dh;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public void addItem(String tableName, String name) throws Exception{
        dh.addItem(tableName, name);
        items = dh.findAllItems(tableName);
    }
    
    
}
