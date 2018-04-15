package com.example.askarbayev1.demo1;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class BlankFragment3 extends Fragment {
    DBHelper db;
    ArrayList<Item> listItems;
    private Context context;
    private CustomCartAdapter adapter;
    public static BlankFragment3 newInstance() {
        BlankFragment3 fragment = new BlankFragment3();
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
        return inflater.inflate(R.layout.blank_fragment3, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadListView(view);

    }
    private void loadListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.cartListView);
        listItems  = db.getCartItems();
        Log.d("loadListView", ""+listItems.size());

        adapter = new CustomCartAdapter(context, listItems, true);
        listView.setAdapter(adapter);
    }
}
