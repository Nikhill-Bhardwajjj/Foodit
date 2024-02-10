package com.lihkin16.foodit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lihkin16.foodit.admin.adminActivity;
import com.lihkin16.foodit.user.userActivity;
import com.lihkin16.foodit.user.userHomepage;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button userButton ;
        Button adminButton ;
        Button GetStarted;

        GetStarted = findViewById(R.id.Get_started);





        GetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                        startActivity(new Intent(MainActivity.this, userActivity.class));



        }
            });







    }
}