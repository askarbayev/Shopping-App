package com.example.askarbayev1.demo1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomCartAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> cartItemList;
    private LayoutInflater inflater;
    TextView name_type, price;
    private boolean isListView;

    CustomCartAdapter(Context context, ArrayList<Item> arrayList, boolean isListView){
        this.context = context;
        this.cartItemList = arrayList;
        this.isListView = isListView;
        inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return cartItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return cartItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            if(isListView)
                view = inflater.inflate(R.layout.cart_list_item, parent, false);
        }
        for (int j=0; j<cartItemList.size();j++){
            Log.d(cartItemList.get(j).getName()+"", j+"");
            Log.d(cartItemList.get(j).getType()+"", j+"");

        }
        name_type = (TextView) view.findViewById(R.id.name_type);
        price = (TextView) view.findViewById(R.id.price);
        Log.d("i", i+"");
        name_type.setText(cartItemList.get(i).getName()+" - "+cartItemList.get(i).getType());
        String priceValue = String.valueOf(cartItemList.get(i).getPrice());
        price.setText("$"+priceValue);
        String price_val = String.valueOf(cartItemList.get(i).getPrice());
        return view;
    }
}
