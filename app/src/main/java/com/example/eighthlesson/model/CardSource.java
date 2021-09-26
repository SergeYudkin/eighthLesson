package com.example.eighthlesson.model;

import com.example.eighthlesson.model.CardData;

public interface CardSource {

    int size();

    CardData getCardData(int position);

}
