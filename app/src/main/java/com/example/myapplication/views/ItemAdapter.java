package com.example.myapplication.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.HomeListAdapter;
import com.example.myapplication.categories.PizzaFragment;
import com.example.myapplication.categories.pizzaitem;
import com.example.myapplication.models.Product;
import com.example.myapplication.repositories.HomeRepo;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>  {

    private List<Item> itemList;


    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }


    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemNameTextView.setText(item.getName());
        holder.itemImageView.setImageResource(item.getImageResource());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        ImageView itemImageView;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.item_name);
            itemImageView = itemView.findViewById(R.id.item_image);
        }


    }

}
