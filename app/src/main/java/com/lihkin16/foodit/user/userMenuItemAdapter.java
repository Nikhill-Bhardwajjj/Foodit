package com.lihkin16.foodit.user;

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
import com.lihkin16.util.FoodAPI;
import com.squareup.picasso.Picasso;

import java.util.List;



public class userMenuItemAdapter extends RecyclerView.Adapter<userMenuItemAdapter.ViewHolder> {


    private ItemClickListener listener;
    private List<FoodAPI> items;
    private Context context;


    public userMenuItemAdapter(userHomepage context, List<FoodAPI> menuItems, OnSuccessListener<QuerySnapshot> querySnapshotOnSuccessListener) {
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public userMenuItemAdapter(Context context , List<FoodAPI> items , ItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public userMenuItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.menu_item , parent, false) ;
        return new ViewHolder(view, context) ;



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodAPI menuItem = items.get(position);
        String imageUrl ;

        holder.iname.setText(String.format("Food Name :-%s",menuItem.getItemName()));
        holder.iDescription.setText(menuItem.getItemDespription());
        holder.iprice.setText(String.format("Price :-₹%s" , menuItem.getItemPrice()));
        imageUrl = menuItem.getImageUri();
        Picasso.get().load(imageUrl).placeholder(R.drawable.fooditwallpp).into(holder.iphoto);




    }

    public interface ItemClickListener {

        void onItemClick(FoodAPI foodItem);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView iname, iDescription, iprice;
        ImageView iphoto;

        public ViewHolder(@NonNull View view, Context cxt) {
            super(view);
            context = cxt;
            // Correctly initialize the views by finding them using findViewById
            iname = view.findViewById(R.id.tvItemName);
            iDescription = view.findViewById(R.id.tvItemDescription);
            iprice = view.findViewById(R.id.tvItemPrice);
            iphoto = view.findViewById(R.id.ivItemImage);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                listener.onItemClick(items.get(position));
            }
        }
    }
}









