package com.lihkin16.foodit.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lihkin16.foodit.R;
import com.lihkin16.util.AdminApi;

import java.util.HashMap;
import java.util.Map;

public class AdminSignupActivity extends AppCompatActivity {

    private EditText emailEditText ;
    private EditText passwordEditText;
    private ProgressBar progressBar ;
    private  EditText adminNameEditText ;
    private Button signUpButton  ;

    private FirebaseAuth mAuth ;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser admin;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collectionReference = db.collection("Admins");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        mAuth = FirebaseAuth.getInstance();

        signUpButton = findViewById(R.id.admin_email_create_account_button);
        progressBar = findViewById(R.id.admin_signup_progress);
        emailEditText = findViewById(R.id.admin_email_account);
        passwordEditText = findViewById(R.id.admin_password_account);
        adminNameEditText = findViewById(R.id.admin_user_name_account);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                admin = firebaseAuth.getCurrentUser();

                if(admin != null )
                {

                }
                else {

                }
            }
        };


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(emailEditText.getText())
                        && !TextUtils.isEmpty(passwordEditText.getText().toString())
                        && !TextUtils.isEmpty(adminNameEditText.getText().toString()))
                {


                    String email = emailEditText.getText().toString().trim();
                    String password= passwordEditText.getText().toString().trim();
                    String adminname = adminNameEditText.getText().toString().trim();

                    createEmailAccount(email , password , adminname);
                }
                else
                {
                    Toast.makeText(AdminSignupActivity.this, "Empty Fields Are Not Allowed", Toast.LENGTH_SHORT).show();

                }
            }
        });




    }

    private void createEmailAccount(String email, String password, String adminname) {

        if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(adminname))
        {

            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                admin = mAuth.getCurrentUser();
                                assert  admin != null ;

                                String userId = admin.getUid();


                                Map<String , String> userObj = new HashMap<>();
                                userObj.put("userId" ,userId );
                                userObj.put("username" , adminname);


                                collectionReference.add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {

                                                documentReference.get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                                if(task.getResult().exists())
                                                                {
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                    String name = task.getResult().getString("adminnname");

                                                                    AdminApi adminApi = AdminApi.getInstance();
                                                                    adminApi.setUserId(userId);
                                                                    adminApi.setUsername(name);

                                                                    startActivity(new Intent(AdminSignupActivity.this , AdminHomepageActivity.class));

                                                                }
                                                                else {
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                }
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });

                            }
                            else {

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        }

        else {

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        admin = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
    }
}