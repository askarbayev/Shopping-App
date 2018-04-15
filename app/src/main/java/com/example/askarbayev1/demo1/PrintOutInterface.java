package com.example.askarbayev1.demo1;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

public interface PrintOutInterface {
    String nameEnterCheck(TextInputLayout text_input_name, EditText name);
    String typeEnterCheck(TextInputLayout text_input_type, EditText type);
    int quantityEnterCheck(TextInputLayout text_input_quantity, EditText quantity);
    double priceEnterCheck(TextInputLayout text_input_price, EditText price);
}
