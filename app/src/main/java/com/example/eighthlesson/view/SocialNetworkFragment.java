package com.example.eighthlesson.view;

import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eighthlesson.MainActivity;
import com.example.eighthlesson.Navigation;
import com.example.eighthlesson.R;
import com.example.eighthlesson.model.CardData;
import com.example.eighthlesson.model.CardSource;

import com.example.eighthlesson.model.CardSourceLocalImpl;
import com.example.eighthlesson.model.CardSourceResponse;
import com.example.eighthlesson.model.CardsSourceRemoteImpl;
import com.example.eighthlesson.observe.Observer;
import com.example.eighthlesson.observe.Publisher;

public class SocialNetworkFragment extends Fragment {

    private CardSource data;
    private SocialNetworkAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    public static  SocialNetworkFragment newInstance(){
        return  new SocialNetworkFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);  // меню фрагмента

        View view = inflater.inflate(R.layout.fragment_social_network, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        initRecyclerView(recyclerView, data);
        if (false) {
            data = new CardSourceLocalImpl(getResources()).init(new CardSourceResponse() {
                @Override
                public void initialized(CardSource cardSource) {

                }
            });
        } else {

            data = new CardsSourceRemoteImpl().init(new CardSourceResponse() {
                @Override
                public void initialized(CardSource cardSource) {
                    adapter.notifyDataSetChanged();
                }
            });
        }
        adapter.setDataSource(data);
        return view;
    }

    private  void initRecyclerView(RecyclerView recyclerView, CardSource data){



        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SocialNetworkAdapter( this);
        recyclerView.setAdapter(adapter);

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000);
        defaultItemAnimator.setChangeDuration(1000);
        defaultItemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(defaultItemAnimator);



        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter.SetOnItemClickListener(new SocialNetworkAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu,@NonNull MenuInflater inflater){
        inflater.inflate(R.menu.fragment_menu,menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch ( item.getItemId()){
            case R.id.action_add:
                navigation.addFragment(CardUpdateFragment.newInstance(),true);
                  publisher.subscribe(new Observer() {
                      @Override
                      public void updateState(CardData cardData) {
                          data.addCardData(cardData);
                          adapter.notifyItemInserted(data.size()-1);
                      }
                  });

                //recyclerView.scrollToPosition(data.size()-1);  резкий скролл до новой позиции
              //  recyclerView.smoothScrollToPosition(data.size()-1); // плавный скролл (относительно плавный)

                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }

        return  super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu( ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.card_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = adapter.getMenuContextClickPosition();

        switch ( item.getItemId()){
            case R.id.action_update:
               /* data.getCardData(position).setTitle("Обновили"+position);
                adapter.notifyItemChanged(position);*/

                navigation.addFragment(CardUpdateFragment.newInstance(data.getCardData(position)),true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(CardData cardData) {
                        data.updateCardData(position, cardData);
                        adapter.notifyItemChanged(position);
                    }

                });


                return true;
            case R.id.action_delete:
                data.deleteCardData(position);
                adapter.notifyItemRemoved(position);

                return true;
        }

        return super.onContextItemSelected(item);
    }
}
