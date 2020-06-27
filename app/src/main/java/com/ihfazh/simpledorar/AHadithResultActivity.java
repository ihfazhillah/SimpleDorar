package com.ihfazh.simpledorar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ihfazh.simpledorar.adapters.SimpleDorarAdapter;
import com.ihfazh.simpledorar.models.SimpleDorar;
import com.ihfazh.simpledorar.tasks.SimpleDorarTask;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class AHadithResultActivity extends AppCompatActivity {

    ArrayList<SimpleDorar> simpleDorars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_hadith_result);

        // get text
        Intent intent = getIntent();
        String skey = intent.getStringExtra(MainActivity.SKEY);
        // set title
        setTitle(skey);


        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ahadithList);
        new SimpleDorarTask(recyclerView, progressBar, this).execute(skey);

//        // initialize
//        try {
//            simpleDorars = SimpleDorar.createSimpleDorar(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        // create adapter
//        SimpleDorarAdapter simpleDorarAdapter = new SimpleDorarAdapter(simpleDorars);
//        recyclerView.setAdapter(simpleDorarAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // send to service
        // when service done
        // hide the progress bar
        // and show the result
        // get results from internet
//        progressBar.setVisibility(View.INVISIBLE);
    }
}