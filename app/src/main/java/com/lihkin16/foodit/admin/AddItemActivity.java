package com.lihkin16.foodit.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lihkin16.foodit.R;
import com.lihkin16.util.FoodAPI;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener {

    private AdminMenuItem editItem;
    private EditText itemNameEditText;
    private ProgressBar progressBar;
    private EditText itemDescriptionEditText;
    private EditText itemPriceEditText;
    private Button addItemButton;
    private ImageView itemImageView;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    private Bitmap selectedImageBitmap;
    private static final int PICK_IMAGE_REQUEST = 1;

    private String currentUserId;
    private String currentUserName;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private ActivityResultLauncher<Intent> galleryLauncher;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("FoodAPI");
    private Uri imageUri;
    private String TAG = "EditItemFragment";
    private static final int GALLERY_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_item);

        storageReference = FirebaseStorage.getInstance().getReference();

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            imageUri = data.getData();
                            if (imageUri != null) {
                                itemImageView.setImageURI(imageUri);
                            }

                        }
                    }
                });
        mAuth = FirebaseAuth.getInstance();
        itemNameEditText = findViewById(R.id.edit_text_item_name);
        progressBar= findViewById( R.id.postprogressbar);
        itemDescriptionEditText = findViewById(R.id.edit_text_item_description);
        itemPriceEditText = findViewById(R.id.edit_text_item_price);
        itemImageView = findViewById(R.id.image_view_item_image);
        addItemButton = findViewById(R.id.button_add_item);

        addItemButton.setOnClickListener(this);
        itemImageView.setOnClickListener(this);



        progressBar.setVisibility(View.INVISIBLE);

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

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.button_add_item:
                saveItems() ;
                break ;

            case R.id.image_view_item_image:
                Intent galleryIntent  = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryLauncher.launch(galleryIntent);
                break ;


        }

    }

    private void saveItems() {
          user = mAuth.getCurrentUser();
          String foodId = user.getUid();
        String itemName = itemNameEditText.getText().toString().trim();
        String itemDiscription = itemDescriptionEditText.getText().toString().trim();
        String price = itemPriceEditText.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(itemName)&&!TextUtils.isEmpty(itemDiscription )&& !TextUtils.isEmpty(price) && imageUri !=null)


        {
            StorageReference filepath = storageReference.child(" Food_images")
                    .child("food_image_item"+ Timestamp.now().getSeconds());

            filepath.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String imageUrl = uri.toString();
                                    FoodAPI foodAPI = new FoodAPI();
                                    foodAPI.setFoodId(foodId);
                                    foodAPI.setItemName(itemName);
                                    foodAPI.setItemDespription(itemDiscription);
                                    foodAPI.setItemPrice(price);
                                    foodAPI.setImageUri(imageUrl);

                                    collectionReference.add(foodAPI)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                        startActivity(new Intent(AddItemActivity.this, AdminHomepageActivity.class));
                                                    finish();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    Log.d(TAG, "onFailure :"+ e.getMessage());
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
        }

        else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
