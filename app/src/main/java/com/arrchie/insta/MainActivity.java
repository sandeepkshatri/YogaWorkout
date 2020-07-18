package com.arrchie.insta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.arrchie.insta.Fragment.HomeFragment;
import com.arrchie.insta.Fragment.NotificationFragment;
import com.arrchie.insta.Fragment.ProfileFragment;
import com.arrchie.insta.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView =findViewById(R.id.button_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch(menuItem.getItemId()){

                        case R.id.nav_home :
                            selectedFragment=new HomeFragment();
                            break;

                        case R.id.nav_search :
                            selectedFragment=new SearchFragment();
                            break;

                        case R.id.nav_add :
                            selectedFragment=null;
                            startActivity(new Intent(MainActivity.this,PostActivity.class));
                            break;

                        case R.id.nav_heart :
                            selectedFragment=new NotificationFragment();
                            break;

                        case R.id.nav_profile :
                            SharedPreferences.Editor editor=getSharedPreferences("PRESS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedFragment=new ProfileFragment();
                            break;

                    }
                    if(selectedFragment!=null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }
                    return false;
                }
            };
}