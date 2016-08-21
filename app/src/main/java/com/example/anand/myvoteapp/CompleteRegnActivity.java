package com.example.anand.myvoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by Acer on 21-08-2016.
 */

public class CompleteRegnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completeregn);
        TextView textView = (TextView)findViewById(R.id.textView4);
        Intent intent = getIntent();
        final String phnum = intent.getStringExtra("phnumb");
        textView.setText("Your mobile number is "+phnum);

        final EditText nametext, dobtext, addtext;
        final RadioGroup gender = (RadioGroup)findViewById(R.id.genderselect);
        final RadioButton male = (RadioButton)findViewById(R.id.malebutton);
        final RadioButton female = (RadioButton)findViewById(R.id.femalebutton);
        Button submitbutton = (Button)findViewById(R.id.submitregdetails);
        nametext = (EditText)findViewById(R.id.nametext);
        dobtext = (EditText)findViewById(R.id.dobtext);
        addtext = (EditText)findViewById(R.id.addresstext);
        submitbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (nametext.getText()!=null && dobtext.getText()!=null && addtext.getText()!=null && (male.isChecked()||female.isChecked())){
                            Intent gotofinalreg = new Intent(CompleteRegnActivity.this,FinalRegActivity.class);
                            gotofinalreg.putExtra("phnumb",phnum);
                            int idd = gender.getCheckedRadioButtonId();
                            RadioButton radbuton = (RadioButton)findViewById(idd);
                            String genderr = radbuton.getText().toString();
                            String name = nametext.getText().toString();
                            String dob = dobtext.getText().toString();
                            String address = addtext.getText().toString();

                            gotofinalreg.putExtra("name",name);
                            gotofinalreg.putExtra("dob",dob);
                            gotofinalreg.putExtra("address",address);
                            gotofinalreg.putExtra("gender",genderr);
                            startActivity(gotofinalreg);
                        }
                    }
                }
        );


    }
}
