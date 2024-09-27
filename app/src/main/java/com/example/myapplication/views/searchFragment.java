package com.example.myapplication.views;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.HomeListAdapter;
import com.example.myapplication.databinding.FragmentSearchBinding;
import com.example.myapplication.databinding.PizzalayoutBinding;
import com.example.myapplication.models.Product;
import com.example.myapplication.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.List;


public class searchFragment extends Fragment  {
    private RecyclerView recyclerView;
    private EditText search;
    private NavController navController;
    private ItemAdapter adapter;
    TextView itemname;
    private List<Item> itemList;

    private List<Item> filteredList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);


    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      search=view.findViewById(R.id.searchbar);
      search.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0f, 0f, 0));
      search.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0f, 0f, 0));
      recyclerView=view.findViewById(R.id.searchview);
      itemname=view.findViewById(R.id.item_name);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
      itemList=new ArrayList<>();
        navController= Navigation.findNavController(view);

itemList.add(new Item("Peperoni Pizza",R.drawable.pizza));
itemList.add(new Item("Veg Burger",R.drawable.pop_2));
itemList.add(new Item("Veg Loaded Pizza",R.drawable.pop_3));
itemList.add(new Item("Ice Tea",R.drawable.icetea));
itemList.add(new Item("Black Coffee",R.drawable.icetea));
itemList.add(new Item("Milk Coffee",R.drawable.milkcoffee));
        filteredList = new ArrayList<>(itemList);



        adapter = new ItemAdapter(filteredList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(itemList);
        } else {
            for (Item item : itemList) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


}