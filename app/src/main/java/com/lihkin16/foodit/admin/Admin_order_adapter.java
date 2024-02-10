package com.lihkin16.foodit.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.lihkin16.foodit.R;
import com.lihkin16.util.OrderAPI;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Admin_order_adapter extends RecyclerView.Adapter<Admin_order_adapter.ViewHolder> {

    private List<OrderAPI> items ;
    private Context context ;

    public Admin_order_adapter(List<OrderAPI> items , Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public Admin_order_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_order_row , parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_order_adapter.ViewHolder holder, int position) {

        OrderAPI itemOrder  = items.get(position);
        String imgaeUrl ;

        holder.About.setText(String.format("How user want it's Food:-  %s",itemOrder.getSpecification()));
        holder.user_name.setText(String.format(" User Name :-%s", itemOrder.getUsername()));
        holder.foodName.setText(String.format("Food Name :-%s", itemOrder.getFoodname()));
        holder.Price.setText(String.format("Price :-₹%s" ,itemOrder.getFoodprice()));
        holder.quantity.setText(String.format("Quantity of Food :-%s",itemOrder.getFoodquantity()));

        imgaeUrl = itemOrder.getImageUrl();

        Picasso.get().load(imgaeUrl).placeholder(R.drawable.fooditwallpp).fit().into(holder.foodImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void notifyDataSetChanged(int i )
    {

    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView user_name , foodName , Price, quantity ,About;
        ImageView foodImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name = itemView.findViewById(R.id.order_display_username);
            foodName = itemView.findViewById(R.id.order_display_food_name);
            Price = itemView.findViewById(R.id.order_display_price);
            quantity = itemView.findViewById(R.id.order_display_quantity);
            foodImage = itemView.findViewById(R.id.order_display_image);
            About = itemView.findViewById(R.id.order_about);


        }
    }
}
