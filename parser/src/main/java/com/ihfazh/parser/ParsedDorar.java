package com.ihfazh.parser;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ParsedDorar {
    private ArrayList<DorarHadith> dorarHadithList = new ArrayList<>();
    private ArrayList<DorarInfo> dorarInfoList = new ArrayList<>();

    public ArrayList<DorarHadith> getDorarHadithList() {
        return dorarHadithList;
    }

    public ArrayList<DorarInfo> getDorarInfoList() {
        return dorarInfoList;
    }

    public void appendHadith(DorarHadith dorarHadith){
        dorarHadithList.add(dorarHadith);
    }

    public void appendInfo(DorarInfo dorarInfo){
        dorarInfoList.add(dorarInfo);
    }
}
