package com.example.eighthlesson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import android.os.Bundle;
import android.view.Menu;

import com.example.eighthlesson.observe.Publisher;
import com.example.eighthlesson.view.SocialNetworkFragment;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, FragmentOnAttachListener {

    private Publisher publisher = new Publisher();

    public Navigation getNavigation() {
        return navigation;
    }

    private Navigation navigation;

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = new Navigation(getSupportFragmentManager());
        initToolbar();
        navigation.addFragment(SocialNetworkFragment.newInstance(),false);
        getSupportFragmentManager().addFragmentOnAttachListener(this);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SocialNetworkFragment.newInstance()).commit();
        }

    }
    @Override
    public void onBackStackChanged() {
       if (getSupportFragmentManager().getBackStackEntryCount()>0){
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       } else {
           getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       }
    }

       //общее меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.activity_menu,menu);
       return super.onCreateOptionsMenu(menu);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return toolbar;

    }


    @Override
    public void onAttachFragment(@NonNull  FragmentManager fragmentManager, @NonNull Fragment fragment) {

    }
}