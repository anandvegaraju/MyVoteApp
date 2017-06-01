package com.example.anand.myvoteapp;

/**
 * Created by Acer on 11-08-2016.
 */

import android.app.Application;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;




public class MyVoteApp extends Application {
    private AuthCallback authCallback;

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig =  new TwitterAuthConfig("gN5ydNpGONZOjauIa4nm5OMkf", "MGs5uEiKc1ga7IYUQZiyYIzOt7u5pmihbCr1PkZhd2fSId63QU");
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                AuthConfig.Builder authConfigBuilder = new AuthConfig.Builder()
                        .withAuthCallBack(authCallback)
                        .withPhoneNumber("+34111111111");

                Digits.authenticate(authConfigBuilder.build());

            }

            @Override
            public void failure(DigitsException exception) {

            }
        };
    }
    public AuthCallback getAuthCallback(){
        return authCallback;
    }
}