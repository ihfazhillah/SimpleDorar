package com.ihfazh.simpledorar.tasks;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ihfazh.parser.Dorar;
import com.ihfazh.parser.DorarHadith;
import com.ihfazh.parser.DorarInfo;
import com.ihfazh.parser.ParsedDorar;
import com.ihfazh.simpledorar.adapters.SimpleDorarAdapter;
import com.ihfazh.simpledorar.models.SimpleDorar;

import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class SimpleDorarTask extends AsyncTask<String, Void, ArrayList<SimpleDorar>> {

    private static RecyclerView ivrecyclerView;
    private static Context ivContext;
    private static ProgressBar ivProgressBar;

    public SimpleDorarTask(RecyclerView recyclerView, ProgressBar progressBar, Context context){
        ivrecyclerView = recyclerView;
        ivContext = context;
        ivProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
       ivProgressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected ArrayList<SimpleDorar> doInBackground(String... strings) {
        ArrayList<SimpleDorar> simpleDorars = new ArrayList<>();
        ArrayList<ParsedDorar> results = new ArrayList<>();


        try {
            Dorar dorar = new Dorar(strings[0]);
            results.addAll(dorar.getParsedResults());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

//        BackgroundColorSpan highlighted = new BackgroundColorSpan(Color.RED);
        ForegroundColorSpan highlighted = new ForegroundColorSpan(Color.GREEN);

        for (ParsedDorar parsedDorar :
                results) {

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            // process hadith
            for (DorarHadith dorarHadith :
                    parsedDorar.getDorarHadithList()) {

                if (dorarHadith.isHighlighted()){
                    spannableStringBuilder.append(dorarHadith.getText(), new ForegroundColorSpan(Color.RED), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    spannableStringBuilder.append(dorarHadith.getText());
                }

            }

            // process hadith info
            spannableStringBuilder.append("\n");
            spannableStringBuilder.append("\n");

            for (DorarInfo dorarInfo :
                    parsedDorar.getDorarInfoList()) {
                spannableStringBuilder.append(dorarInfo.getKey(), new RelativeSizeSpan(0.75f), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                int start = spannableStringBuilder.length();
                int end = start + dorarInfo.getValue().length();
                spannableStringBuilder.append(dorarInfo.getValue(), new ForegroundColorSpan(Color.RED), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.setSpan(new RelativeSizeSpan(0.75f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.append("\n");
            }

            SimpleDorar simpleDorar = new SimpleDorar();
            simpleDorar.setTxt(spannableStringBuilder);
            simpleDorars.add(simpleDorar);

        }

        return simpleDorars;
    }

    @Override
    protected void onPostExecute(ArrayList<SimpleDorar> simpleDorars) {
        // initialize
        // create adapter
        ivProgressBar.setVisibility(ProgressBar.INVISIBLE);
        SimpleDorarAdapter simpleDorarAdapter = new SimpleDorarAdapter(simpleDorars);
        ivrecyclerView.setAdapter(simpleDorarAdapter);
        ivrecyclerView.setLayoutManager(new LinearLayoutManager(ivContext));
    }
}
