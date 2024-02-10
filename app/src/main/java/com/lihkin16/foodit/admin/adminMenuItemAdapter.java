package com.lihkin16.foodit.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lihkin16.foodit.R;
import com.lihkin16.util.FoodAPI;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adminMenuItemAdapter extends RecyclerView.Adapter<adminMenuItemAdapter.ViewHolder> {

    private List<FoodAPI> items ;
    private Context context;

   public adminMenuItemAdapter(Context context , List<FoodAPI> items)
   {
       this.items=items;
       this.context = context ;

   }


    @NonNull
    @Override
    public adminMenuItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.admin_menu_item , parent , false);
       return  new ViewHolder(view) ;

    }

    @Override
    public void onBindViewHolder(@NonNull adminMenuItemAdapter.ViewHolder holder, int position) {
        FoodAPI adminItem = items.get(position) ;
        String imageUrl;

        holder.tvName.setText(String.format("Food Name :-%s",adminItem.getItemName()));
        holder.tvDescription.setText(String.format("About :-%s",adminItem.getItemDespription()));
        holder.tvPrice.setText(String.format("Price :-₹%s" , adminItem.getItemPrice()));

        imageUrl = adminItem.getImageUri();

        Picasso.get().load(imageUrl).placeholder(R.drawable.fooditwallpp).fit().into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    private void notifyDataSetChanged(int i) {
    }


    public class ViewHolder extends  RecyclerView.ViewHolder {


        TextView tvName, tvDescription, tvPrice;
        ImageView ivImage;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.admin_item_name);
            tvDescription = view.findViewById(R.id.admin_item_description);
            tvPrice = view.findViewById(R.id.admin_item_price);
            ivImage = view.findViewById(R.id.admin_item_image);
        }
    }
}
