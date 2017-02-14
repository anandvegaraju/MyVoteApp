package com.example.anand.myvoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Acer on 21-08-2016.
 */

public class FinalRegActivity extends AppCompatActivity {

    private int flag = 0;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_reg_activity);
        Intent intent = getIntent();
        final String phnum = intent.getStringExtra("phnumb");
        final String name = intent.getStringExtra("name");
        final String dob = intent.getStringExtra("dob");
        final String address = intent.getStringExtra("address");
        final String gender = intent.getStringExtra("gender");
        final String uniqueid = intent.getStringExtra("uid");

        TextView textView = (TextView)findViewById(R.id.textView5);
        textView.setText("Name : " + name + "\nDate of birth : " + dob + "\nAddress : " + address + "\nUnique ID : " + uniqueid + "\nGender : " + gender + "\nPhone number : " + phnum);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef99 = database.getReference("phonenumbers");
        final DatabaseReference uidRef = database.getReference("uniqueids");
        final DatabaseReference myRef100 = database.getReference(phnum);
        Button finalsubbutton = (Button)findViewById(R.id.finalregbutton);
        finalsubbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myRef99.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String phlist = dataSnapshot.getValue(String.class);
                                        String s = phlist + " " + phnum;
                                        myRef99.setValue(s);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );

                        uidRef.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String uidlist = dataSnapshot.getValue(String.class);
                                        if(uidlist.contains(uniqueid)){
                                            Intent gotoerror = new Intent(FinalRegActivity.this,ErrorScreen.class);
                                            flag = 1;
                                            gotoerror.putExtra("errormsg","Your have already registered with the UID " + uniqueid);
                                            startActivity(gotoerror);
                                        }else {
                                            String u = uidlist + " " + uniqueid;
                                            uidRef.setValue(u);
                                            myRef100.child("name").setValue(name);
                                            myRef100.child("dob").setValue(dob);
                                            myRef100.child("address").setValue(address);
                                            myRef100.child("gender").setValue(gender);
                                            myRef100.child("UniqueID").setValue(uniqueid);
                                            myRef100.child("approved").setValue("false");
                                            myRef100.child("voted").setValue("false");

                                            Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_SHORT).show();
                                            Intent gotofirst = new Intent(FinalRegActivity.this,MainActivity.class);
                                            startActivity(gotofirst);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );



                    }
                }
        );

    }
}
