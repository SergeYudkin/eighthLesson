package com.example.eighthlesson.model;

import com.example.eighthlesson.R;

import java.util.Random;

public class PictureIndexConverter {

    private static Random rnd = new Random();
    private static Object syncObj = new Object();

    private static int[] picIndex = {R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7,
            R.drawable.doski

    };

    public static int randomPictureIndex(){
        synchronized (syncObj){
            return rnd.nextInt(picIndex.length);
        }
    }

    public static int getPictureByIndex(int index){
        if (index < 0 || index >= picIndex.length){
            index = 0;
        }
        return picIndex[index];
    }

    public static int getIndexByPicture(int picture){
        for (int i = 0; i < picIndex.length; i++){
            if(picIndex[i] == picture){
                return i;
            }
        }
        return 0;
    }


}
