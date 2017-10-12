package com.sleepygeckos.tikape;

import spark.Spark;

public class Main {

    public static void main(String[] args) {

        Spark.get("/hei", (req, res) -> {
            return "Hei maailma!!";
        });

    }

}
