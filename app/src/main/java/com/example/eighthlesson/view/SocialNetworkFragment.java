package com.example.eighthlesson.view;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eighthlesson.R;
import com.example.eighthlesson.model.CardData;
import com.example.eighthlesson.model.CardSource;
import com.example.eighthlesson.model.CardSourceImpl;

public class SocialNetworkFragment extends Fragment {

    private CardSource data;
    private SocialNetworkAdapter adapter;
    private RecyclerView recyclerView;

    public static SocialNetworkFragment newInstance(){
        return  new SocialNetworkFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);  // меню фрагмента

        View view = inflater.inflate(R.layout.fragment_social_network, container, false);
        data  = new CardSourceImpl(getResources()).init();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SocialNetworkAdapter(data, this);
        recyclerView.setAdapter(adapter);
        adapter.setOnMyOnClickListener(new MyOnClickListener() {

            @Override
            public void onMyClick(View view, int position) {
                Toast.makeText(getContext(),"Тяж обработка для " +position,Toast.LENGTH_SHORT).show();
            }
        });


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.fragment_menu,menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch ( item.getItemId()){
            case R.id.action_add:
                data.addCardData(new CardData("Новая"+data.size(),
                        "Описание"+data.size(),
                        R.drawable.pic7,false));
                adapter.notifyItemInserted(data.size()-1);
                //recyclerView.scrollToPosition(data.size()-1);  резкий скролл до новой позиции
                recyclerView.smoothScrollToPosition(data.size()-1); // плавный скролл (относительно плавный)
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return  super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.card_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = adapter.getMenuContextClickPosition();

        switch ( item.getItemId()){
            case R.id.action_update:
                data.getCardData(position).setTitle("Обновили"+position);
                adapter.notifyItemChanged(position);


                return true;
            case R.id.action_delete:
                data.deleteCardData(position);
                adapter.notifyItemRemoved(position);


                return true;
        }

        return super.onContextItemSelected(item);
    }
}
