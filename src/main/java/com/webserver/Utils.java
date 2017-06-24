package com.webserver;

/**
 * Created by Marin on 1/15/2017.
 */
public class Utils {

    private static final double PROFIT_PERCENTAGE = 0.05;

    public static double getSellPrice(double price) {
        return price - price * PROFIT_PERCENTAGE;
    }

    public static double getBuyPrice(double price) {
        return price + price * PROFIT_PERCENTAGE;
    }
}
