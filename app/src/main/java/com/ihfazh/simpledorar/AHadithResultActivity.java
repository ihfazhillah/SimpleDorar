package com.ihfazh.simpledorar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ihfazh.simpledorar.adapters.SimpleDorarAdapter;
import com.ihfazh.simpledorar.models.SimpleDorar;
//import com.ihfazh.simpledorar.tasks.SimpleDorarTask;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class AHadithResultActivity extends AppCompatActivity {

    ArrayList<SimpleDorar> simpleDorars;
    String skey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_hadith_result);

        // get text
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null){
            skey = intent.getStringExtra(Intent.EXTRA_TEXT);
        } else {
            skey = intent.getStringExtra(MainActivity.SKEY);
        }

        // set title
        setTitle(skey);


        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ahadithList);
//        new SimpleDorarTask(recyclerView, progressBar, this).execute(skey);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }

}