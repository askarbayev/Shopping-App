package com.example.askarbayev1.demo1;

import android.support.design.widget.TextInputLayout;

public class UserInput {
    public static boolean itemNameCheck(String name, TextInputLayout layout){
        boolean res = name.matches("[a-zA-Z]+");
        if (!res){
            layout.setErrorEnabled(true);
            layout.setError("enter valid item name");
        }
        else {
            layout.setError(null);
            layout.setErrorEnabled(false);
        }
        return res;
    }
    public static boolean itemTypeCheck(String type, TextInputLayout layout){
        boolean res = type.matches("([a-zA-Z]|[0-9])+");
        if (!res){
            layout.setErrorEnabled(true);
            layout.setError("enter valid item type");
        }
        else {
            layout.setError(null);
            layout.setErrorEnabled(false);
        }
        return res;
    }
    public static boolean itemPriceCheck(double price, TextInputLayout layout){
        boolean res = false;
        if (price != 0){
            res = true;
        }
        if (!res){
            layout.setErrorEnabled(true);
            layout.setError("enter valid item price");
        }
        else {
            layout.setError(null);
            layout.setErrorEnabled(false);
        }
        return res;
    }
    public static boolean itemQuantityCheck(int quantity, TextInputLayout layout){
        boolean res = false;
        if (quantity != 0){
            res = true;
        }
        if (!res){
            layout.setErrorEnabled(true);
            layout.setError("enter valid item quantity");
        }
        else {
            layout.setError(null);
            layout.setErrorEnabled(false);
        }
        return res;
    }

}
