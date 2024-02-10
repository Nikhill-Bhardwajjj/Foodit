package com.lihkin16.foodit.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lihkin16.foodit.R;
import com.lihkin16.foodit.admin.AddItemActivity;
import com.lihkin16.foodit.admin.AdminHomepageActivity;
import com.lihkin16.util.FoodAPI;
import com.lihkin16.util.OrderAPI;
import com.lihkin16.util.UserApi;
import com.squareup.picasso.Picasso;

import java.util.List;

public class getOrder extends AppCompatActivity {

    private ImageView FoodImage;
    private TextView TextfoodName ;
    String foodName;
    private EditText username ;
    private EditText specification;
    private TextView TextfoodDiscription ;
    private TextView TextfoodPrice;
    private TextView quantity ;
    private TextView up ;
    private TextView down ;
    private  String imageUri ;

    private Button order_button;
    private FoodAPI menuItem ;
   List<FoodAPI>items;
   String value ;
    String foodPrice;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("OrderAPI");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_order);

        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
         user = mAuth.getCurrentUser();

        UserApi userApi = UserApi.getInstance();
        FoodImage = findViewById(R.id.orderItemImage);
        TextfoodName = findViewById(R.id.orderItemName);
        TextfoodPrice = findViewById(R.id.orderItemPrice);
        TextfoodDiscription = findViewById(R.id.orderItemDescription);
        order_button = findViewById(R.id.order_button);
        username = findViewById(R.id.username_editText);
        specification = findViewById(R.id.specification);

        up = findViewById(R.id.order_up);
        down = findViewById(R.id.order_down);
        quantity = findViewById(R.id.quantity);



        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CharSequence charSequenceValue = quantity.getText();

                int value = Integer.parseInt(charSequenceValue.toString());
                   value++;

                quantity.setText(String.valueOf(value));





            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CharSequence charSequenceValue = quantity.getText();

                int value = Integer.parseInt(charSequenceValue.toString());

                if(value>0)
                {
                    value--;
                    quantity.setText(String.valueOf(value));

                }
            }
        });



        Intent intent = getIntent();
         foodName = intent.getStringExtra("foodName");
           foodPrice = intent.getStringExtra("foodPrice");
        String foodDescription = intent.getStringExtra("foodDescription");
         imageUri = intent.getStringExtra("imageUri");


        TextfoodName.setText(String.format("Food name :-%s",foodName));
        TextfoodPrice.setText(String.format("Food Price :-₹%s",foodPrice));
        TextfoodDiscription.setText(String.format("About:-%s",foodDescription));
        Picasso.get().load(imageUri).placeholder(R.drawable.fooditwallpp).into(FoodImage);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();
                if(user!= null)
                {

                }
                else {

                }
            }
        };

        order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save_order();
            }
        });





        }

    private void save_order() {

        user = mAuth.getCurrentUser();
        String foodId = user.getUid();
        String specs = specification.getText().toString().trim();
        String user_name = username.getText().toString().trim();
        String itemName = TextfoodName.getText().toString().trim();
        CharSequence charSequenceValue = quantity.getText();
        int v1 = Integer.parseInt(charSequenceValue.toString());

        CharSequence chr2 = foodPrice;
        int v2 = Integer.parseInt(chr2.toString());
        String price = String.valueOf(v1 * v2);
        String foodQuantity = quantity.getText().toString().trim();


        if (TextUtils.isEmpty(user_name)) {
            Toast.makeText(getOrder.this, "username cannot be empty ", Toast.LENGTH_SHORT).show();
        } else {


            OrderAPI orderAPI = new OrderAPI();
            orderAPI.setFoodname(foodName);
            orderAPI.setFoodprice(price);
            orderAPI.setFoodquantity(foodQuantity);
            orderAPI.setImageUrl(imageUri);
            orderAPI.setUsername(user_name);
            orderAPI.setSpecification(specs);

            collectionReference.add(orderAPI)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            startActivity(new Intent(getOrder.this, user_Display_order.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getOrder.this, "an error occur", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        }



    @Override
    protected void onStart() {
        super.onStart();

        user = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mAuth != null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }
}

