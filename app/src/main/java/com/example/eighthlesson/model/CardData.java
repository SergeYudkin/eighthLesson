package com.example.eighthlesson.model;




import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class CardData implements Parcelable {



    private String title;
    private String description;
    private int picture;
    private boolean like;
    private Date data;


    public Date getData() {
        return data;
    }



    public CardData(String title, String description, int picture, boolean like, Date data) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.like = like;
        this.data = data;
    }

    protected CardData(Parcel in) {
        title = in.readString();
        description = in.readString();
        picture = in.readInt();
        like = in.readByte() != 0;


    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

    public String getTitle(String s) {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }

    public boolean isLike() {
        return like;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(picture);
        dest.writeByte((byte) (like ? 1 : 0));
        dest.writeString(String.valueOf(data));
    }
}
