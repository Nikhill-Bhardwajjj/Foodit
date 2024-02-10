package com.lihkin16.foodit.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.lihkin16.foodit.R;
import com.lihkin16.util.OrderAPI;

import java.util.ArrayList;
import java.util.List;

public class admin_display_order extends AppCompatActivity {

    private RecyclerView items ;
    private Admin_order_adapter itemAdapter;
    private List<OrderAPI> orderItems;
    private FirebaseAuth mAuth ;
    private FirebaseAuth.AuthStateListener mAuthListener ;
    private FirebaseUser user ;
    private ImageView details;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference ;
    private final CollectionReference collectionReference= db.collection("OrderAPI");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_display_order);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        orderItems = new ArrayList<>();
        items = findViewById(R.id.admin_order_recycle_view);
        details = findViewById(R.id.details);
        items.setHasFixedSize(true);
        items.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new Admin_order_adapter( orderItems , this);
        items.setAdapter(itemAdapter);

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(admin_display_order.this , adminorderdetail.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty())
                        {
                            orderItems.clear();
                            for(QueryDocumentSnapshot orders: queryDocumentSnapshots)
                            {
                                OrderAPI orderAPI = orders.toObject(OrderAPI.class);
                                orderItems.add(orderAPI);
                            }
                            if(itemAdapter == null)
                            {
                                itemAdapter = new Admin_order_adapter(orderItems , admin_display_order.this );
                                items.setAdapter(itemAdapter);
                            }
                            else {
                                itemAdapter.notifyDataSetChanged();
                            }
                        }
                        else {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}