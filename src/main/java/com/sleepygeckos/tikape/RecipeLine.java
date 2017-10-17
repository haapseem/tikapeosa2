package com.sleepygeckos.tikape;

/**
 *
 * @author jkesala
 */
public class RecipeLine {

    Ingredient ingredient;
    String order;
    String amount;
    String text;

    public RecipeLine(Ingredient ingredient, String order, String amount, String text) {
        this.ingredient = ingredient;
        this.order = order;
        this.amount = amount;
        this.text = text;
    }
}
