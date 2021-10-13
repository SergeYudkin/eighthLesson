package com.example.eighthlesson.model;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.example.eighthlesson.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CardSourceLocalImpl implements CardSource {

    private final List<CardData> dataSource;
    private final Resources resources;

    public CardSourceLocalImpl(Resources resources) {
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

    @Override
    public void deleteCardData(int position) {

        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData newCardData) {
        dataSource.set(position,newCardData);
    }

    @Override
    public void addCardData(CardData newCardData) {

        dataSource.add(newCardData); // индекс новой карточки индекс ставится перед cardData ( по умолчанию появляется последней)

    }

    @Override
    public void clearCardData() {

        dataSource.clear();
    }

    public CardSourceLocalImpl init(CardSourceResponse cardSourceResponse){


        String[] titles = resources.getStringArray(R.array.titles);
        String[] description = resources.getStringArray(R.array.description);
        TypedArray typedArray = resources.obtainTypedArray(R.array.pictures);
        int[] pictures = new int[typedArray.length()];

        for (int i = 0; i <typedArray.length(); i++){
            pictures[i] = typedArray.getResourceId(i,-1);
        }
        for (int i = 0; i <titles.length; i++){
            dataSource.add(new CardData(titles[i],description[i],pictures[i],false,
                    Calendar.getInstance().getTime()));

        }
        if (cardSourceResponse !=null){
            cardSourceResponse.initialized(this);
        }
        return this;
    }

}
