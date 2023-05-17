package com.example.treasurehunt;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class categoriesAdapter extends RecyclerView.Adapter<categoriesAdapter.MyViewHolder> {

    public static void sort(ArrayList<category> list)
    {
        list.sort((o2, o1) -> o1.getCatname().compareTo(o2.getCatname()));
    }

    static Context context;

    static ArrayList<category> list;

    public categoriesAdapter(Context context, ArrayList<category> list) {
        this.context = context;
        this.list = list;
        sort(list);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_category_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        category category1 = list.get(position);
        holder.name.setText(category1.getCatname());
        holder.quan.setText(category1.getQuantity());
        Picasso.get().load(category1.getImg()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        TextView quan,name;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.category_name);
            quan = itemView.findViewById(R.id.category_quan);
            image = itemView.findViewById(R.id.category_image);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int postion = getBindingAdapterPosition();
            Intent intent = new Intent(context,cat.class);
            intent.putExtra("name",list.get(postion).getCatname());
            context.startActivity(intent);
        }
    }

}
