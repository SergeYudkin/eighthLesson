package com.example.eighthlesson;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

public class CardSourceImpl implements CardSource {

    private List<CardData> dataSource;
    private Resources resources;

    public CardSourceImpl(Resources resources) {
        dataSource = new ArrayList<>();
        this.resources = resources;
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    public CardSourceImpl init(){


        String[] titles = resources.getStringArray(R.array.titles);
        String[] description = resources.getStringArray(R.array.description);
        TypedArray typedArray = resources.obtainTypedArray(R.array.pictures);
        int[] pictures = new int[typedArray.length()];

        for (int i = 0; i <typedArray.length(); i++){
            pictures[i] = typedArray.getResourceId(i,-1);
        }
        for (int i = 0; i <titles.length; i++){
            dataSource.add(new CardData(titles[i],description[i],pictures[i],false));

        }
        return this;
    }

}
