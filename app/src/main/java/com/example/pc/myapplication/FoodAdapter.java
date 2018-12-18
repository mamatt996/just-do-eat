package com.example.pc.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflter;
    private ArrayList<Food> data = new ArrayList<>();

    private OnQuantityChange onQuantityChange;

    public void setData(ArrayList<Food> foodArrayList) {
        this.data = foodArrayList;
        notifyDataSetChanged();
    }

    public interface OnQuantityChange {
        public void onItemAdded(float price);
        public void onItemRemoved(float price);
    }


    public void setOnQuantityChange(OnQuantityChange onQuantityChange) {
        this.onQuantityChange = onQuantityChange;
    }



    public FoodAdapter(Context context, ArrayList<Food> data) {
        this.data = data;
        mInflter = LayoutInflater.from(context);
    }

    public FoodAdapter (Context context) {
        mInflter = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = mInflter.inflate(R.layout.item_row, viewGroup, false);
        return new FoodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        FoodViewHolder foodViewHolder = (FoodViewHolder)viewHolder;
        foodViewHolder.name.setText(data.get(i).getName());
        foodViewHolder.price.setText(String.valueOf(data.get(i).getPrice()));
        foodViewHolder.quantity.setText(String.valueOf(data.get(i).getQuantity()));
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public TextView name;
        public TextView price;
        public TextView quantity;
        public Button plus;
        public Button minus;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_textVW);
            price = itemView.findViewById(R.id.item_price);
            quantity = itemView.findViewById(R.id.item_quantity);
            plus = itemView.findViewById(R.id.item_plus);
            minus = itemView.findViewById(R.id.item_minus);

            plus.setOnClickListener(this);
            minus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                if(v.getId() == R.id.item_plus) {
                    Food food = data.get(getAdapterPosition());
                    food.increaseQuantity();
                    notifyItemChanged(getAdapterPosition());
                    onQuantityChange.onItemAdded(food.getPrice());

                } else if (v.getId() == R.id.item_minus) {
                    Food food = data.get(getAdapterPosition());
                    if (food.getQuantity() > 0) {
                        food.decreaseQuantity();
                        notifyItemChanged(getAdapterPosition());
                        onQuantityChange.onItemRemoved(food.getPrice());
                    }
                }
        }
    }

}
