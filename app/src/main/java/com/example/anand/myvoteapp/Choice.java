package com.example.anand.myvoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Choice extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private int flag_next;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_choice);
        Intent intent2 = getIntent();
        final String mobilenumber = intent2.getStringExtra("phnum");
        TextView phonetext = (TextView)findViewById(R.id.textView2);
        phonetext.setText(mobilenumber);
        final RadioGroup groupnu = (RadioGroup)findViewById(R.id.radio_group);
        final RadioButton option1,option2,option3,option4,option5;
        option1 = (RadioButton)findViewById(R.id.radioButton5);
        option2 = (RadioButton)findViewById(R.id.radioButton4);
        option3 = (RadioButton)findViewById(R.id.radioButton3);
        option4 = (RadioButton)findViewById(R.id.radioButton2);
        option5 = (RadioButton)findViewById(R.id.radioButton);
        assert groupnu!=null;


        mDatabase = FirebaseDatabase.getInstance().getReference("phonenumbers");

        groupnu.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {

                        RadioButton selec = (RadioButton)findViewById(i);
                        assert selec!=null;
                        Toast.makeText(getApplicationContext(),"You have selected "+selec.getText(),Toast.LENGTH_SHORT).show();
                    }
                }
        );
        Button submitbutton = (Button)findViewById(R.id.button);

        submitbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (option1.isChecked()||option2.isChecked()||option3.isChecked()||option4.isChecked()||option5.isChecked()){

                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // This method is called once with the initial value and again
                                    // whenever data at this location is updated.
                                    String value = dataSnapshot.getValue(String.class);
                                    if(value.contains(mobilenumber)){
                                        int finalchoice = groupnu.getCheckedRadioButtonId();
                                        RadioButton finalbutton = (RadioButton)findViewById(finalchoice);
                                        String finchoice = finalbutton.getText().toString();
                                        Intent gotosubmit = new Intent(Choice.this,SubmitActivity.class);
                                        gotosubmit.putExtra("phonenum",mobilenumber);
                                        gotosubmit.putExtra("choice",finchoice);
                                        startActivity(gotosubmit);

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Invalid user",Toast.LENGTH_SHORT).show();

                                        Intent goerror = new Intent(Choice.this,ErrorScreen.class);
                                        goerror.putExtra("errormsg","Sorry! You are not a registered user.");
                                        startActivity(goerror);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {


                                }
                            });


                        }
                    }
                }
        );

    }
}
