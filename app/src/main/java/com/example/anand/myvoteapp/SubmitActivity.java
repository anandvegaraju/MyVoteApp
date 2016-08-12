package com.example.anand.myvoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
 * Created by Acer on 12-08-2016.
 */

public class SubmitActivity extends AppCompatActivity {
    private DatabaseReference mDatabase1,mDatabase2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_activity);
        TextView userdata = (TextView)findViewById(R.id.userdetails);
        Intent i = getIntent();
        final String ph = i.getStringExtra("phonenum");
        final String ch = i.getStringExtra("choice");
        userdata.setText("Confirm your details\n Phone: "+i.getStringExtra("phonenum")+"\nChoice: "+i.getStringExtra("choice"));


        Button confirmbutton = (Button)findViewById(R.id.confirm_button);
        mDatabase1 = FirebaseDatabase.getInstance().getReference(ph);
        mDatabase2 = FirebaseDatabase.getInstance().getReference(ch);

        confirmbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Read from the database
                        mDatabase1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.
                                String value = dataSnapshot.getValue(String.class);

                                if (value.contains("false")||value.contains("true")){
                                    Intent i2 = new Intent(SubmitActivity.this,ThankYou.class);

                                    i2.putExtra("choice",ch);
                                    i2.putExtra("phnum",ph);
                                    startActivity(i2);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                                // Failed to read value


                            }
                        });
                    }
                }
        );

    }
}
