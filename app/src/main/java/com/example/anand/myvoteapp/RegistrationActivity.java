package com.example.anand.myvoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Acer on 21-08-2016.
 */

public class RegistrationActivity extends AppCompatActivity {

    private static final String TWITTER_KEY = "gN5ydNpGONZOjauIa4nm5OMkf";
    private static final String TWITTER_SECRET = "MGs5uEiKc1ga7IYUQZiyYIzOt7u5pmihbCr1PkZhd2fSId63QU";
    private AuthCallback authCallback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY,TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef9 = database.getReference("phonenumbers");
        DigitsAuthButton digitsAuthButton = (DigitsAuthButton)findViewById(R.id.auth_button1);
        digitsAuthButton.setCallback(
                new AuthCallback() {
                    @Override
                    public void success(DigitsSession session, final String phoneNumber) {


                        myRef9.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String numlist = dataSnapshot.getValue(String.class);
                                        if(numlist.contains(phoneNumber)){
                                            Intent gotoerror = new Intent(RegistrationActivity.this,ErrorScreen.class);
                                            gotoerror.putExtra("errormsg","Your number "+phoneNumber+" is already registered");
                                            startActivity(gotoerror);
                                        }
                                        else {
                                            Intent compreg = new Intent(RegistrationActivity.this,CompleteRegnActivity.class);
                                            compreg.putExtra("phnumb",phoneNumber);
                                            Toast.makeText(getApplicationContext(),"Please wait",Toast.LENGTH_LONG).show();
                                            startActivity(compreg);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );

                    }

                    @Override
                    public void failure(DigitsException error) {

                    }
                }
        );

    }
}
