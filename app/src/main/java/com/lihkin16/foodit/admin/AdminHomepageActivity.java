package com.lihkin16.foodit.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.lihkin16.foodit.MainActivity;
import com.lihkin16.foodit.R;
import com.lihkin16.util.FoodAPI;

import java.util.ArrayList;
import java.util.List;

public class AdminHomepageActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private com.lihkin16.foodit.admin.adminMenuItemAdapter adminMenuItemAdapter;
    private List<FoodAPI> adminItems;
    private FloatingActionButton addItemButton;

    private FirebaseAuth mAuth ;
    private FirebaseAuth.AuthStateListener  mAuthListener ;
    private FirebaseUser admin;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference ;

    private final CollectionReference collectionReference = db.collection("FoodAPI");
    private TextView noFoodItems ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);

        mAuth = FirebaseAuth.getInstance();
        admin = mAuth.getCurrentUser();


        recyclerView = findViewById(R.id.admin_items_recycler_view);
        addItemButton = findViewById(R.id.fab_add_item);
        adminItems = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminMenuItemAdapter = new adminMenuItemAdapter(this, adminItems);
        recyclerView.setAdapter(adminMenuItemAdapter);



        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminHomepageActivity.this , AddItemActivity.class));


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu , menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    protected void onStart() {
        super.onStart();

        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()) {
                    adminItems.clear();
                    for (QueryDocumentSnapshot foods : queryDocumentSnapshots) {
                        FoodAPI foodAPI = foods.toObject(FoodAPI.class);
                        adminItems.add(foodAPI);
                    }

                    if (adminMenuItemAdapter == null) {
                        adminMenuItemAdapter = new adminMenuItemAdapter(AdminHomepageActivity.this, adminItems);
                        recyclerView.setAdapter(adminMenuItemAdapter);
                    } else {
                        adminMenuItemAdapter.notifyDataSetChanged();
                    }

                } else {

                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_Order_admin:
                startActivity(new Intent(AdminHomepageActivity.this , admin_display_order.class));
                break;
            case R.id.action_signout_admin:
                mAuth.signOut();
                startActivity(new Intent(AdminHomepageActivity.this, MainActivity.class));
                break;
            case R.id.action_order_history_admin:
                startActivity(new Intent(AdminHomepageActivity.this , adminorderdetail.class));
        }
        return super.onOptionsItemSelected(item);
    }
}





