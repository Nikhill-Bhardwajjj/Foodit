package com.lihkin16.foodit.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lihkin16.foodit.R;
import com.lihkin16.util.AdminApi;

public class adminActivity extends AppCompatActivity {


    private Button singupButton;
    private Button loginButton;
    private ProgressBar progressBar;
    private AutoCompleteTextView emailAddress;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser currentUser;

    final private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final private CollectionReference collectionReference = db.collection("Admins");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);



         mAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.adminLogin);
        singupButton = findViewById(R.id.adminSignup);
        emailAddress = findViewById(R.id.admin_email);
        password = findViewById(R.id.admin_password);
        progressBar = findViewById(R.id.admin_login_progress);


        loginButton.setOnClickListener(view ->

                loginEmailPasswordUser(emailAddress.getText().toString().trim()
                        , password.getText().toString().trim()));


        singupButton.setOnClickListener(v ->

                startActivity(new Intent(adminActivity.this, AdminSignupActivity.class)));
    }

    private void loginEmailPasswordUser(String email, String pwd) {

        progressBar.setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(pwd)) {
            mAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            FirebaseUser admin = mAuth.getCurrentUser();
                            assert admin != null;
                            String currentAdminId = admin.getUid();

                            collectionReference.whereEqualTo("userId", currentAdminId)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
                                                return;
                                            }
                                            assert value != null;
                                            if (!value.isEmpty()) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                for (QueryDocumentSnapshot snapshot : value) {
                                                    AdminApi adminApi = AdminApi.getInstance();

                                                    adminApi.setUserId(snapshot.getString("userId"));
                                                    adminApi.setUsername(snapshot.getString("username"));

                                                    startActivity(new Intent(adminActivity.this, AdminHomepageActivity.class));

                                                }
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
        }else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(adminActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();

        }


    }

}