package com.example.eighthlesson.model;

import com.example.eighthlesson.model.CardData;

public interface CardSource {

    int size();

    CardData getCardData(int position);

    void deleteCardData(int position);
    void updateCardData(int position,CardData newCardData);
    void addCardData(CardData newCardData);
    void clearCardData();

}
