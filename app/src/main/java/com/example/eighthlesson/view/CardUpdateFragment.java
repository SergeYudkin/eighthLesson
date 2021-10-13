package com.example.eighthlesson.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eighthlesson.MainActivity;
import com.example.eighthlesson.R;
import com.example.eighthlesson.model.CardData;
import com.example.eighthlesson.model.PictureIndexConverter;
import com.example.eighthlesson.observe.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class CardUpdateFragment extends Fragment {

    private static final String ARG_CARD_DATA = "Param_CardData";

    private TextInputEditText title;
    private TextInputEditText description;
    private DatePicker dataPicker;

    private CardData cardData;
    private Publisher publisher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        publisher = ((MainActivity) context).getPublisher();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        publisher = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_card,container,false);
        initView(view);
        if (cardData!=null){
            populateView();
        }
        return view;
    }

    private void populateView(){

        title.setText(cardData.getTitle());
        description.setText(cardData.getDescription());
        initDatePicker(cardData.getData());
    }

    private void initDatePicker (Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.dataPicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),null);
    }

    private Date getDateFromDatePicker(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,this.dataPicker.getYear());
        cal.set(Calendar.MONTH,this.dataPicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH,this.dataPicker.getDayOfMonth());
        return  cal.getTime();
    }

    private void initView(View view){
        title = view.findViewById(R.id.inputTitle);
        description = view.findViewById(R.id.inputDescription);
        dataPicker = view.findViewById(R.id.inputDate);
    }

    public static CardUpdateFragment newInstance(CardData cardData){
        CardUpdateFragment fragment = new CardUpdateFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA,cardData);
        fragment.setArguments(args);
        return fragment;
    }

    public static CardUpdateFragment newInstance(){
        CardUpdateFragment fragment = new CardUpdateFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            cardData = getArguments().getParcelable(ARG_CARD_DATA);
        }
    }

    private CardData collectCardData(){
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        Date date = getDateFromDatePicker();
     //   int picture;
      //  boolean like;
        if (cardData != null){
            cardData.setTitle(title);
            cardData.setDescription(description);
            cardData.setData(date);
            return cardData;
        } else{
            int picture = PictureIndexConverter.getPictureByIndex(PictureIndexConverter.randomPictureIndex());
            return  new CardData(title,description,picture,false,date);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        cardData = collectCardData();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifyTask(cardData);
    }
}
