package com.example.askarbayev1.demo1;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BlankFragment2 extends Fragment {
    View view;
    DBHelper db;
    public static BlankFragment2 newInstance() {
        BlankFragment2 fragment = new BlankFragment2();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.blank_fragment2, container, false);
        ListView listview = (ListView) view.findViewById(R.id.listview);
        LinkedList<String> list = new LinkedList<>();
        list = getItems();
        final StableArrayAdapter adapter = new StableArrayAdapter(getActivity(),
                android.R.layout.simple_expandable_list_item_1, list, getItemIds());
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                int itemID = (int) id;
                nextActivity(itemID);
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                //list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });
        return view;
    }

    public LinkedList<String> getItems(){
        LinkedList<String> linkedList =new LinkedList<>();
        db = new DBHelper(getActivity());
        LinkedList<Item> items = db.getAllItems();
        for (Item item:items){
            linkedList.add(item.getName()+"\n"+item.getType());
        }
        return linkedList;
    }
    public LinkedList<Integer> getItemIds(){
        LinkedList<Integer> linkedList =new LinkedList<>();
        db = new DBHelper(getActivity());
        LinkedList<Item> items = db.getAllItems();
        for (Item item:items){
            linkedList.add(item.getId());
        }
        return linkedList;
    }
    public void nextActivity(int itemID){
        Intent intent = new Intent(getActivity(), ChosenItem.class);
        Bundle bundle = new Bundle();
        bundle.putInt("itemID", itemID);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
class StableArrayAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public StableArrayAdapter(Context context, int textViewResourceId,
                              List<String> objects, LinkedList<Integer> itemIds) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), itemIds.get(i));
        }
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}