package com.example.askarbayev1.demo1;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomListAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {
    private Context context;
    private ArrayList<Item> arrayList;
    private LayoutInflater inflater;
    private boolean isListView;
    private SparseBooleanArray mSelectedItemsIds;
    HashMap<Integer, Integer> selectedItems;


    public CustomListAdapter(Context context, ArrayList<Item> arrayList, boolean isListView) {
        this.context = context;
        this.arrayList = arrayList;
        this.isListView = isListView;
        inflater = LayoutInflater.from(context);
        mSelectedItemsIds = new SparseBooleanArray();
        selectedItems = new HashMap<>();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();

            //inflate the layout on basis of boolean
            if (isListView)
                view = inflater.inflate(R.layout.list_view_items, viewGroup, false);

            viewHolder.label = (TextView) view.findViewById(R.id.textView_name);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            viewHolder.price = (TextView) view.findViewById(R.id.textView_price);

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();
        final Spinner quantity = (Spinner) view.findViewById(R.id.spinner);

        viewHolder.label.setText(arrayList.get(i).getName()+" - "+arrayList.get(i).getType());
        String price = String.valueOf(arrayList.get(i).getPrice());
        viewHolder.price.setText(price);
        viewHolder.checkBox.setChecked(mSelectedItemsIds.get(i));
        List<String> quantity_list = new ArrayList<>();
        int qty = arrayList.get(i).getQuantity();
        for (int val = 1; val<=qty; val++){
            quantity_list.add(String.valueOf(val));
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, quantity_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantity.setAdapter(dataAdapter);


        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(i, !mSelectedItemsIds.get(i), quantity, dataAdapter);
            }
        });




        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public HashMap<Integer, Integer> getSelectedItems(){
        return selectedItems;
    }

    class ViewHolder {
        private TextView label;
        private CheckBox checkBox;
        public TextView price;
    }


    /**
     * Check the Checkbox if not checked
     **/
    public void checkCheckBox(int position, boolean value, Spinner quantity, ArrayAdapter<String> dataAdapter) {
        if (value){
            ViewHolder viewHolder = new ViewHolder();
            int qnt = Integer.parseInt(quantity.getSelectedItem().toString());
            Log.d("Spinner val1: ", quantity.getSelectedItem().toString());
            selectedItems.put(position, qnt);
            mSelectedItemsIds.put(position, true);
            int spinnerPosition = dataAdapter.getPosition(quantity.getSelectedItem().toString());
            Log.d("Spinner potion: ", spinnerPosition+"");
            quantity.setSelection(spinnerPosition);
            Log.d("Spinner val2: ", quantity.getSelectedItem().toString());
        }
        else{
            mSelectedItemsIds.delete(position);
            selectedItems.remove(position);
        }

        notifyDataSetChanged();
    }

    /**
     * Return the selected Checkbox IDs
     **/
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}
