package com.example.myapplication.categories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.views.Item;
import com.example.myapplication.views.ItemAdapter;

import java.util.ArrayList;
import java.util.List;


public class PizzaFragment extends Fragment {
    private RecyclerView recyclerView;
    private pizza_adapter adapter;
    TextView itemname;
    private List<pizzaitem> pizzaList;
    private List<pizzaitem> list;

    public PizzaFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.pizzaview);
        itemname=view.findViewById(R.id.pizza_name);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        pizzaList=new ArrayList<>();

        pizzaList.add(new pizzaitem("Peperoni Pizza",R.drawable.pizza));
        pizzaList.add(new pizzaitem("Veg Pizza",R.drawable.pop_3));
        pizzaList.add(new pizzaitem("Veg Loaded Pizza",R.drawable.pizza));
        pizzaList.add(new pizzaitem("Chicken Sausage Pizza",R.drawable.pop_3));
        pizzaList.add(new pizzaitem("Panner Pizza",R.drawable.pizza));
        pizzaList.add(new pizzaitem("Mushroom Pizza",R.drawable.pop_3));
        list = new ArrayList<>(pizzaList);

        adapter = new pizza_adapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pizza, container, false);

    }
}