package com.ihfazh.simpledorar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String SKEY = "com.ihfazh.simpledorar.skey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSearchClick(View view){
        // create intent
        Intent intent = new Intent(this, AHadithResultActivity.class);
        // send the key
        TextInputEditText textInputEditText = (TextInputEditText) findViewById(R.id.skey);
        String skey = Objects.requireNonNull(textInputEditText.getText()).toString();
        // activate the intent
        intent.putExtra(SKEY, skey);
        startActivity(intent);

    }
}