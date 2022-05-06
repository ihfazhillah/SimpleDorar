package com.ihfazh.simpledorar.models;

import android.text.SpannableStringBuilder;

import java.util.ArrayList;

public class SimpleDorar {
    private String txt;
    private SpannableStringBuilder spannableString;

    public String getTxt() {
        return txt;
    }


    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setTxt(SpannableStringBuilder txt){
        this.spannableString = txt;
    }

    private static int lastHadithId = 0;

    public static ArrayList<SimpleDorar> createSimpleDorar(int numHadith) throws InterruptedException {
        Thread.sleep(2000);
        ArrayList<SimpleDorar> simpleDorars = new ArrayList<>();
        for (int i = 0; i < numHadith; i++) {
            SimpleDorar simpleDorar = new SimpleDorar();
            ++lastHadithId;
            simpleDorar.setTxt("Hadith ke " + lastHadithId);
            simpleDorars.add(simpleDorar);
        }

        return simpleDorars;
    }

    public SpannableStringBuilder getSpannableString() {
        return spannableString;
    }
}
