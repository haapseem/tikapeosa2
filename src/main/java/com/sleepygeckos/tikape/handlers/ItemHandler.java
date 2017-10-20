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
    
    public ItemHandler(String tableName, DbHandler dh) throws Exception{
        this.dh = dh;
        this.items = dh.findAllItems(tableName);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public void addItem(String tableName, String name) throws Exception{
        dh.addItem(tableName, name);
        items = dh.findAllItems(tableName);
    }
    
    
}
