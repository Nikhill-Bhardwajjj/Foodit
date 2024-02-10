package com.lihkin16.foodit.user;

import androidx.annotation.NonNull;
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
import com.lihkin16.util.UserApi;

import java.util.HashMap;
import java.util.Map;

public class userSignupActivity extends AppCompatActivity {


    private Button signUpButton  ;
    private AutoCompleteTextView emailEditText ;
    private EditText passwordEditText;
    private ProgressBar progressBar ;
    private  EditText userNameEditText ;

    private FirebaseAuth mAuth ;
    private FirebaseAuth.AuthStateListener mAuthListener ;
    private FirebaseUser user ;
    final private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference collectionReference = db.collection("Users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        mAuth = FirebaseAuth.getInstance() ;

        signUpButton = findViewById(R.id.user_email_create_account_button);
        progressBar = findViewById(R.id.user_signup_progressbar);
        emailEditText = findViewById(R.id.user_email_account);
        passwordEditText = findViewById(R.id.user_password_account);
        userNameEditText = findViewById(R.id.user_name_account);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();

                if(user != null)
                {
                    //if user already have account ;
                }
                {

                    //if user does not have account
                }

            }
        };


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(emailEditText.getText())
                && !TextUtils.isEmpty(passwordEditText.getText().toString())
                && !TextUtils.isEmpty(userNameEditText.getText().toString()))
                {

                    String email = emailEditText.getText().toString().trim();
                    String password= passwordEditText.getText().toString().trim();
                    String username = userNameEditText.getText().toString().trim();

                    createUserEmailAccount(email , password , username );

                }
                else
                {
                    Toast.makeText(userSignupActivity.this, "Empty Fields Are Not Allowed", Toast.LENGTH_SHORT).show();

                }
            }
        });



        }

    private void createUserEmailAccount(String email, String password, String username) {

        if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(username))
        {
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())

                            {
                                user = mAuth.getCurrentUser();
                                assert user != null ;
                                String userId = user.getUid();


                                Map<String , String> userObj = new HashMap<>();
                                userObj.put("userId" ,userId );
                                userObj.put("username" , username);
                                Intent in = new Intent(userSignupActivity.this , getOrder.class);
                                in.putExtra("username" , username);

                                collectionReference.add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {

                                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.getResult().exists())
                                                        {
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            String username = task.getResult().getString("username");

                                                            UserApi userApi  = UserApi.getInstance();
                                                            userApi.setUserId(userId);
                                                            userApi.setUsername(username);

                                                            startActivity(new Intent(userSignupActivity.this, userHomepage.class));
                                                        }
                                                        else
                                                        {
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
                            else
                            {

                            }

                        }




                    }).addOnFailureListener(new OnFailureListener() {
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

        user = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
