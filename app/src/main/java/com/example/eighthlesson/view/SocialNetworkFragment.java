package com.example.eighthlesson.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eighthlesson.R;
import com.example.eighthlesson.model.CardSource;
import com.example.eighthlesson.model.CardSourceImpl;

public class SocialNetworkFragment extends Fragment {

    public static SocialNetworkFragment newInstance(){
        return  new SocialNetworkFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social_network, container, false);
        CardSource data  = new CardSourceImpl(getResources()).init();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        SocialNetworkAdapter socialNetworkAdapter = new SocialNetworkAdapter(data);
        socialNetworkAdapter.setOnMyOnClickListener(new MyOnClickListener() {

            @Override
            public void onMyClick(View view, int position) {
                Toast.makeText(getContext(),"Тяж обработка для " +position,Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(socialNetworkAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }
}