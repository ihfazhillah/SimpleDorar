package com.ihfazh.simpledorar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String SKEY = "com.ihfazh.simpledorar.skey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSearchClick(View view){
        TextInputEditText textInputEditText = (TextInputEditText) findViewById(R.id.skey);
        String skey = Objects.requireNonNull(textInputEditText.getText()).toString();
        doSearch(skey);
    }

    private void doSearch(String skey){
        // create intent
        Intent intent = new Intent(this, AHadithResultActivity.class);
        // send the key
        // activate the intent
        intent.putExtra(SKEY, skey);
        startActivity(intent);
    }

    public void onAudioSearchClick(View view){
        // create intent
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-SA");
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null && requestCode == 10){
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String skey = results.get(0);
            doSearch(skey);
        } else {
            Toast.makeText(getApplicationContext(), "Failed to recognize speech!", Toast.LENGTH_LONG).show();
        }
    }
}