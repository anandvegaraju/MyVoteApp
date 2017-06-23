package com.example.anand.myvoteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;


public class MainActivity extends AppCompatActivity {

    private static final String TWITTER_KEY = "gN5ydNpGONZOjauIa4nm5OMkf";
    private static final String TWITTER_SECRET = "MGs5uEiKc1ga7IYUQZiyYIzOt7u5pmihbCr1PkZhd2fSId63QU";
    private AuthCallback authCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY,TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        TextView registertext = (TextView)findViewById(R.id.registertextview);
        registertext.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent gotoregactivity = new Intent(MainActivity.this,RegistrationActivity.class);
                        startActivity(gotoregactivity);
                    }
                }
        );

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button1);
        digitsButton.setText("Vote");
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Choice.class);
                intent.putExtra("phnum",phoneNumber);
                startActivity(intent);
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });




    }

}
