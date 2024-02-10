package com.lihkin16.foodit.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.lihkin16.foodit.admin.adminActivity;
import com.lihkin16.util.UserApi;

public class userActivity extends AppCompatActivity {

   private Button signupButton  ;
   private Button loginButton ;

   private Button adminButton;
   private ProgressBar progressBar ;
   private AutoCompleteTextView emailAddress ;
   private EditText password ;
   private FirebaseAuth mAuth ;
   private FirebaseAuth.AuthStateListener mAuthListener ;
   private FirebaseUser CurrentUser ;
  final private FirebaseFirestore db = FirebaseFirestore.getInstance();
  final  private CollectionReference collectionReference = db.collection("Users");





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mAuth = FirebaseAuth.getInstance();
        adminButton = findViewById(R.id.Admin_button_user_activity);
        signupButton= findViewById(R.id.user_email_sign_up_button);
        loginButton = findViewById(R.id.user_email_sign_in_button) ;
        emailAddress= findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        progressBar = findViewById(R.id.user_login_progress);


        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(userActivity.this, adminActivity.class));


            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEmailPasswordUser(emailAddress.getText().toString().trim()
                ,password.getText().toString().trim());
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(userActivity.this, userSignupActivity.class));


            }
        });
    }

    private void loginEmailPasswordUser(String email, String pwd) {

        progressBar.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pwd))
        {
            mAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            String currentUserId = user.getUid();

                            collectionReference.whereEqualTo("userId", currentUserId)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                            if(error != null )
                                            {
                                                return ;
                                            }
                                            assert  value != null;
                                            if(!value.isEmpty())
                                            {
                                                progressBar.setVisibility(View.VISIBLE);

                                                for(QueryDocumentSnapshot snapshot: value)
                                                {
                                                    UserApi userApi = UserApi.getInstance();




                                                    startActivity( new Intent(userActivity.this, userHomepage.class));
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
        }
        else
        {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(userActivity.this, "please enter email and password", Toast.LENGTH_SHORT).show();
        }
    }
}