package com.example.askarbayev1.demo1;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class BlankFragment extends Fragment implements PrintOutInterface {

    EditText name, type, price, quantity;
    TextInputLayout text_input_name, text_input_type, text_input_price, text_input_quantity;
    Button enter;
    View view;
    DBHelper db;

    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.blank_fragment1, container, false);
        name = (EditText) view.findViewById(R.id.name);
        type = (EditText) view.findViewById(R.id.type);
        price = (EditText) view.findViewById(R.id.price);
        quantity = (EditText) view.findViewById(R.id.quantity);
        enter = (Button) view.findViewById(R.id.enter);
        text_input_name = (TextInputLayout) view.findViewById(R.id.text_input_name);
        text_input_type = (TextInputLayout) view.findViewById(R.id.text_input_type);
        text_input_price = (TextInputLayout) view.findViewById(R.id.text_input_price);
        text_input_quantity = (TextInputLayout) view.findViewById(R.id.text_input_quantity);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item_name = nameEnterCheck(text_input_name, name);
                String item_type = typeEnterCheck(text_input_type, type);
                double item_price = priceEnterCheck(text_input_price, price);
                int item_quantity = quantityEnterCheck(text_input_quantity, quantity);

                if (UserInput.itemNameCheck(item_name, text_input_name) && UserInput.itemTypeCheck(item_type, text_input_type)
                        && UserInput.itemPriceCheck(item_price, text_input_price) && UserInput.itemQuantityCheck(item_quantity, text_input_quantity)){
                    insertItem(item_name, item_type, item_price, item_quantity);
                }
                //Toast.makeText(getActivity(),  item_name+item_quantity, Toast.LENGTH_SHORT).show();
                //name.setText("");
                //text_input_name.setError("Enter valid data");

            }
        });

        return view;
    }
    public boolean insertItem(String name, String type, double price, int quantity){
        db = new DBHelper(getActivity());
        boolean res;
        ArrayList<Item> items = db.getAllItems();
        int id = -1;
        Log.d(name+" - "+type+" - "+price+" - "+quantity, "before");
        for (Item item:items){
            if (item.getName().equals(name) && item.getType().equals(type) && item.getPrice() == price){
                id = item.getId();
                quantity = quantity + item.getQuantity();
                break;
            }
            //Log.d(item.getName()+" - "+item.getType()+" - "+item.getPrice()+" - "+item.getQuantity(), "inside loop");
        }
        if (id != -1){
            res = db.updateItem(id, name, type, price, quantity);
            Toast.makeText(getActivity(), "UPDATED", Toast.LENGTH_SHORT).show();
        }
        else {
            res = db.insertItem(name, type, price, quantity);
            Toast.makeText(getActivity(), "INSERTED", Toast.LENGTH_SHORT).show();
        }
        return res;
    }

    @Override
    public String nameEnterCheck(TextInputLayout text_input_name, EditText name) {
        String name_val = null;
        try{
            name_val = String.valueOf(name.getText());
        }catch (NumberFormatException e){
            text_input_name.setError("enter valid item name");
        }
        return name_val;
    }

    @Override
    public String typeEnterCheck(TextInputLayout text_input_type, EditText type) {
        String type_val = null;
        try{
            type_val = String.valueOf(type.getText());
        }catch (NumberFormatException e){
            text_input_type.setError("enter valid item type");
        }
        return type_val;
    }

    @Override
    public int quantityEnterCheck(TextInputLayout text_input_quantity, EditText quantity) {
        int item_quantity = -1;

        try{
           item_quantity = Integer.parseInt(String.valueOf(quantity.getText()));
        }catch (NumberFormatException e){
            text_input_quantity.setError("enter valid item qty");
        }
        return item_quantity;
    }

    @Override
    public double priceEnterCheck(TextInputLayout text_input_price, EditText price) {
        double item_price = -1;
        try{
            item_price = Double.valueOf(String.valueOf(price.getText()));
        }catch (NumberFormatException e){
            text_input_price.setError("enter valid item price");
        }
        return item_price;
    }
}
