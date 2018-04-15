package com.example.askarbayev1.demo1;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BlankFragment2 extends Fragment {
    DBHelper db;
    Button purchase, budget;
    Switch byName, byPrice;
    EditText editBudget;
    TextView budget_val;
    ArrayList<Item> listItems;
    private Context context;
    private CustomListAdapter adapter;
    public static BlankFragment2 newInstance() {
        BlankFragment2 fragment = new BlankFragment2();
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db =  new DBHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.blank_fragment2, container, false);
        }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadListView(view);
        setPurchase(view);
        budget_val = (TextView) view.findViewById(R.id.budget);
        budget_val.setText(String.valueOf((db.getBudget()==-1)?0.0:db.getBudget()));
        setBudget(view);

    }
    private void loadListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.listview);
        purchase = (Button) view.findViewById(R.id.purchase);
        byName = (Switch) view.findViewById(R.id.byName);
        byPrice = (Switch) view.findViewById(R.id.byPrice);

        String res = switchListener(byName, byPrice);
        listItems  = getItems(res);
        adapter = new CustomListAdapter(context, listItems, true);
        listView.setAdapter(adapter);
    }

    public void setBudget(final View view){
        view.findViewById(R.id.enter_budget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBudget = (EditText) view.findViewById(R.id.edit_budget);
                //db.insertItem("iphone", "x", 1000.0, 5);
                double budget_value = Double.valueOf(editBudget.getText().toString());
                if (db.getBudget()==-1){
                    db.insertBudget(budget_value);
                }
                else {
                    db.updateBudget(budget_value);
                }
                editBudget.setText("");
                budget_val.setText(String.valueOf(db.getBudget()));

            }
        });
    }

    public void setPurchase(View view){
        view.findViewById(R.id.purchase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray selectedRows = adapter.getSelectedIds();
                HashMap<Integer, Integer> selectedItems = adapter.getSelectedItems();
                double budgetValue = Double.valueOf(budget_val.getText().toString());
                double checkBudget = checkBudget(budgetValue, selectedItems);
                if (selectedItems.size()>0 && checkBudget!=-1){
                    for (Map.Entry<Integer, Integer> entry : selectedItems.entrySet()) {
                        int position = entry.getKey();
                        int quantity = entry.getValue();
                        Item item = listItems.get(position);
                        db.insertCartItem(item.getName(), item.getType(), item.getPrice(), quantity);
                        db.updateItem(item.getId(), item.getName(), item.getType(), item.getPrice(), item.getQuantity()-quantity);

                    }
                    //Toast.makeText(getActivity(), "your item(s) added to CART", Toast.LENGTH_SHORT).show();
                    db.updateBudget(db.getBudget()-checkBudget);
                    updateActivity();
                    Toast.makeText(getActivity(), "your item(s) added to CART", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "You don't have enough budget", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void updateActivity(){
        Intent intent = getActivity().getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getActivity().overridePendingTransition(0, 0);
        getActivity().finish();

        getActivity().overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public double checkBudget(double budget, HashMap<Integer, Integer> selectedItems){
        int budgetValue = 0;
        for (Map.Entry<Integer, Integer> entry : selectedItems.entrySet()) {
            int position = entry.getKey();
            int quantity = entry.getValue();
            Item selectedItem = listItems.get(position);
            budgetValue+=selectedItem.getPrice()*quantity;
        }
        if (budget>=budgetValue){
            return budgetValue;
        }
        return -1;
    }


    public String switchListener(final Switch byName, final Switch byPrice){
        String status = db.getStatus();
        if (status.equals("No way")){
            db.insertStatus("byName");
        }
        status = db.getStatus();

        if (status.equals("byName")){
            byName.setChecked(true);
        }
        else if (status.equals("byPrice")){
            byPrice.setChecked(true);
        }

        final String[] res = {"No way"};
        if (byName.isChecked()){
            res[0] = "byName";
        }
        else if (byPrice.isChecked()){
            res[0] = "byPrice";
        }
        byName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Checking the byName: ", ""+isChecked);
                if (isChecked){
                    db.updateStatus("byName");
                    res[0] = "byName";
                    byName.setChecked(true);
                    byPrice.setChecked(false);
                    updateActivity();
                }

            }
        });

        // Sort by price
        byPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Checking the byPrice: ", ""+isChecked);
                if (isChecked){
                    db.updateStatus("byPrice");
                    res[0] = "byPrice";
                    byPrice.setChecked(true);
                    byName.setChecked(false);
                   updateActivity();
                }


            }
        });


        return res[0];
    }

    public ArrayList<Item> getItems(String sortBy){
        ArrayList<Item> items = db.getAllItems();
        if (sortBy.equals("byName")){
            Collections.sort(items, new NameComparator());
        }
        else if(sortBy.equals("byPrice")){
            Collections.sort(items, new PriceComparator());
        }
        return  items;
    }

    public LinkedList<Integer> getItemIds(){
        LinkedList<Integer> linkedList =new LinkedList<>();
        db = new DBHelper(getActivity());
        ArrayList<Item> items = db.getAllItems();
        for (Item item:items){
            linkedList.add(item.getId());
        }
        return linkedList;
    }


}


class NameComparator implements Comparator<Item>{

    @Override
    public int compare(Item a, Item b) {
        return a.getName().compareTo(b.getName());
    }
}

class PriceComparator implements Comparator<Item>{

    @Override
    public int compare(Item a, Item b){
        return (int)(a.getPrice() - b.getPrice());
    }
}