package com.example.eighthlesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SocialNetworkFragment extends Fragment {

    public static SocialNetworkFragment newInstance(){
        return  new SocialNetworkFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social_network, container, false);
       // String[] data = getResources().getStringArray(R.array.test_title);
        CardSource data  = new  CardSourceImpl(getResources()).init();

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
        return view;
    }
}
