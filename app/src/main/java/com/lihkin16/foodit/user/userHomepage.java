package com.lihkin16.foodit.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class userHomepage extends AppCompatActivity implements userMenuItemAdapter.ItemClickListener{

    private RecyclerView items ;
    private EditText search ;
    private userMenuItemAdapter itemAdapter ;
    private List<FoodAPI> menuItems ;
    private FirebaseAuth mAuth ;
    private FirebaseAuth.AuthStateListener mAuthListener ;
    private FirebaseUser user;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private final CollectionReference collectionReference = db.collection("FoodAPI");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        menuItems = new ArrayList<>() ;

        items = findViewById(R.id.rvMenuItems);
        search = findViewById(R.id.etSearch);
        items.setHasFixedSize(true);
        items.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter  = new userMenuItemAdapter(this , menuItems , this);
        items.setAdapter(itemAdapter);

      itemAdapter.setOnItemClickListener(new userMenuItemAdapter.ItemClickListener() {
          @Override
          public void onItemClick(FoodAPI foodItem) {
              Intent intent = new Intent(userHomepage.this, getOrder.class);
              intent.putExtra("foodName", foodItem.getItemName());
              intent.putExtra("foodPrice", foodItem.getItemPrice());
              intent.putExtra("foodDescription", foodItem.getItemDespription());
              intent.putExtra("imageUri", foodItem.getImageUri());
              startActivity(intent);
          }
      });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuu , menu);
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
                            menuItems.clear();
                            for (QueryDocumentSnapshot foods : queryDocumentSnapshots) {
                                FoodAPI foodAPI = foods.toObject(FoodAPI.class);
                                menuItems.add(foodAPI);
                            }

                            if (itemAdapter == null) {
                                itemAdapter = new userMenuItemAdapter(userHomepage.this, menuItems , this);
                                items.setAdapter(itemAdapter);
                            } else {
                                itemAdapter.notifyDataSetChanged();
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
            case R.id.action_Order_user:
                break;
            case R.id.action_signout_user:
                mAuth.signOut();

                startActivity(new Intent(userHomepage.this, MainActivity.class));
                break;
            case R.id.action_bar_profile_user:
                startActivity(new Intent(userHomepage.this , userProfile.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(FoodAPI foodItem) {


        Intent intent = new Intent(userHomepage.this, getOrder.class);
        intent.putExtra("foodName", foodItem.getItemName());
        intent.putExtra("foodPrice", foodItem.getItemPrice());
        intent.putExtra("foodDescription", foodItem.getItemDespription());
        intent.putExtra("imageUri", foodItem.getImageUri());
        startActivity(intent);

    }
}