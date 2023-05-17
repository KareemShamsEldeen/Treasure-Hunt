package com.example.treasurehunt.UModel;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasurehunt.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public ImageView btnmins;
    public ImageView btnplus;
    public ImageView btndelete;

    public ImageView imgp;
    public TextView txtname;
    public TextView txtprice;
    public TextView txtquan;

    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtname = itemView.findViewById(R.id.txtname);
        txtprice = itemView.findViewById(R.id.txtprice);
        txtquan = itemView.findViewById(R.id.txtquan);
        imgp = itemView.findViewById(R.id.imgp);

        btnmins = itemView.findViewById(R.id.btnmins);
        btnplus = itemView.findViewById(R.id.btnplus);
        btndelete = itemView.findViewById(R.id.btndelete);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
