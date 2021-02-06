package com.wdk.passwordgenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import hotchemi.android.rate.AppRate;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.StringWriter;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    Boolean uppercheck =false;
    Boolean lowercheck =false;
    Boolean numbercheck =false;
    Boolean specialcheck =false;
public String PACKAGE_NAME;

    String password = "";

    CheckBox Upper;
    CheckBox Lower;
    CheckBox Special;
    CheckBox Numbers;

    EditText Result;
    EditText Query;

    Button generate;
    Button copy;
    Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        PACKAGE_NAME =getApplicationContext().getPackageName();

        AppRate.with(this)
                .setLaunchTimes(1)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);

        //EditText
        Result = findViewById(R.id.result);
        Result.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
        Query = findViewById(R.id.query);
        Query.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});


        //Checkboxes
        Upper = findViewById(R.id.upper);
        Lower = findViewById(R.id.lower);
        Special = findViewById(R.id.special);
        Numbers = findViewById(R.id.numbers);

        //Buttons
        generate = findViewById(R.id.generate);
        share = findViewById(R.id.share);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,Result.getText().toString());
                startActivity(Intent.createChooser(intent,"Share password on"));
            }
        });





        Upper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (Upper.isChecked()){
                    uppercheck = true;
                }
                else {
                    uppercheck = false;
                }

            }
        });

        Lower.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (Lower.isChecked()){
                    lowercheck = true;
                }
                else {
                    lowercheck = false;
                }

            }
        });
        Numbers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (Numbers.isChecked()){
                    numbercheck = true;
                }
                else {
                    numbercheck = false;
                }

            }
        });

        Special.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (Special.isChecked()){
                    specialcheck = true;
                }
                else {
                    specialcheck = false;
                }

            }
        });







        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int num1= Integer.parseInt(Query.getText().toString());
                try {
                    Result.setText(generateRandomPassword(num1,uppercheck,lowercheck,numbercheck,specialcheck));
                    int number = Integer.parseInt(Query.getText().toString());
                    if (number > 20){
                        Query.setText(String.valueOf(20));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
    return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
          int id = item.getItemId();
          if (id == R.id.Shareapp){
              Intent intent = new Intent (Intent.ACTION_SEND);
              intent.setType("text/plain");
              intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.wdk.passwordgenerator");
              startActivity(Intent.createChooser(intent,"Share App Via"));
          }
          return true;
    }

    private static String generateRandomPassword(int max_length, boolean upperCase, boolean lowerCase, boolean numbers, boolean specialCharacters)
    {
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*()_-+=<>?/{}~|";
        String allowedChars = "";

        Random rn = new Random();
        StringBuilder sb = new StringBuilder(max_length);

        //this will fulfill the requirements of atleast one character of a type.
        if(upperCase) {
            allowedChars += upperCaseChars;
            sb.append(upperCaseChars.charAt(rn.nextInt(upperCaseChars.length()-1)));
        }

        if(lowerCase) {
            allowedChars += lowerCaseChars;
            sb.append(lowerCaseChars.charAt(rn.nextInt(lowerCaseChars.length()-1)));
        }

        if(numbers) {
            allowedChars += numberChars;
            sb.append(numberChars.charAt(rn.nextInt(numberChars.length()-1)));
        }

        if(specialCharacters) {
            allowedChars += specialChars;
            sb.append(specialChars.charAt(rn.nextInt(specialChars.length()-1)));
        }


        //fill the allowed length from different chars now.
        for(int i=sb.length();i < max_length;++i){
            sb.append(allowedChars.charAt(rn.nextInt(allowedChars.length())));
        }

        return  sb.toString();
    }}