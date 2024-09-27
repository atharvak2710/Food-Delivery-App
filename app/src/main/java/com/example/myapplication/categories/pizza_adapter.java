package com.example.myapplication.categories;

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


import java.util.List;

public class pizza_adapter extends RecyclerView.Adapter<pizza_adapter.pizzaViewHolder>
{
    private List<pizzaitem> pizzaList;
    public pizza_adapter(List<pizzaitem> pizzaList) {
        this.pizzaList = pizzaList;
    }

    @NonNull
    @Override
    public pizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizzalayout, parent, false);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new pizza_adapter.pizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull pizzaViewHolder holder, int position) {
        pizzaitem item = pizzaList.get(position);
        holder.itemNameTextView.setText(item.getName());
        holder.itemImageView.setImageResource(item.getImageResource());
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    static class pizzaViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        ImageView itemImageView;

        pizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.pizza_name);
            itemImageView = itemView.findViewById(R.id.pizza_image);
        }
        public void bind(pizzaitem pizzaItem) {
            itemNameTextView.setText(pizzaItem.getName());
            itemImageView.setImageResource(pizzaItem.getImageResource());

            // You can handle clicks or any other events here
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click event
                    // For example, if you want to open a fragment
                    PizzaFragment pizzaFragment = new PizzaFragment();
                    // Pass any necessary data to the fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("pizza_name", pizzaItem.getName());

                    pizzaFragment.setArguments(bundle);

                    // Navigate to the fragment
                    AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.pizzaFragment, pizzaFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

    }
}
