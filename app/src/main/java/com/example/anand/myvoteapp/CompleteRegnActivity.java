package com.example.anand.myvoteapp;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
                        String regexp = "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((1[6-9]|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((1[6-9]|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((1[6-9]|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$";

                        int flag = 0;

                        if (dobtext.getText().toString().matches(regexp)) {
                            //It's valid

                            String[] parts = dobtext.getText().toString().split("/");
                            String ipyear = parts[2];
                            int ipy = Integer.parseInt(ipyear);
                            int age = 2016-ipy;
                            if (age>=18){
                                flag = 0;
                            }
                            else {
                                flag =1;
                                Toast.makeText(getApplicationContext(),"Age less than 18",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            flag = 1;
                            Toast.makeText(getApplicationContext(),"Invalid age format",Toast.LENGTH_SHORT).show();
                        }
                        if (flag == 0 && nametext.getText()!=null && dobtext.getText()!=null && addtext.getText()!=null && (male.isChecked()||female.isChecked())){
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
                        }else {
                            Toast.makeText(getApplicationContext(),"Complete the form",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


    }
}
