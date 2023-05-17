package com.example.treasurehunt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class productAdepter extends RecyclerView.Adapter<productAdepter.ViewHolder>{

    ArrayList<productModel> list;
    Context context;


    public productAdepter(ArrayList<productModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        productModel model = list.get(position);

        Picasso.get().load(model.getProductImage()).into(holder.imgp);

        holder.title1.setText(model.getTitle());
        holder.price1.setText(model.getPrice());

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context,SingleProductActivity.class);
            intent.putExtra("pcat",model.getCategory());
            intent.putExtra("pname",model.getTitle());
            intent.putExtra("pImage",model.getProductImage());
            intent.putExtra("pprice",model.getPrice());
            intent.putExtra("pcolor",model.getColor());
            intent.putExtra("desp",model.getDescriptionp());
            intent.putExtra("size",model.getDescriptions());
            intent.putExtra("sname",model.getName());
            intent.putExtra("sphone",model.getPhone());
            intent.putExtra("plocation",model.getLocation());
            intent.putExtra("sername",model.getSername());
            intent.putExtra("quantity",model.getQuantity());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title1,price1;
        ImageView imgp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title1 = itemView.findViewById(R.id.title1);
            price1 = itemView.findViewById(R.id.price1);
            imgp = itemView.findViewById(R.id.imgp);
        }
    }
}
