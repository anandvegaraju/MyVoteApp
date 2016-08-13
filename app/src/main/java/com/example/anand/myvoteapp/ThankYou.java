package com.example.anand.myvoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Acer on 12-08-2016.
 */

public class ThankYou extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyou);
        Intent intent = getIntent();
        String s2 = intent.getStringExtra("choice");
        String s3 = intent.getStringExtra("phnum");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef5 = database.getReference(s3);
        final DatabaseReference myRef3 = database.getReference(s2);
        myRef3.push().setValue(s3);
        myRef5.setValue("true");

        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
        Button exitbutton = (Button)findViewById(R.id.exit_button);
        exitbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }
                }
        );

    }
}