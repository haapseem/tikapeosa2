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

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
