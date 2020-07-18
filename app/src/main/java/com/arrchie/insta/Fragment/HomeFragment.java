package com.arrchie.insta.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arrchie.insta.Adapter.PostAdapter;
import com.arrchie.insta.BmiActivity;
import com.arrchie.insta.CalActivity;
import com.arrchie.insta.LoginActivity;
import com.arrchie.insta.MainActivity;
import com.arrchie.insta.Model.Post;
import com.arrchie.insta.R;
import com.arrchie.insta.challengeActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

            private RecyclerView recyclerView;
            private PostAdapter postAdapter;
             public List<Post> postLists;
            private List<String> followingList;
            //Drawer Menu
            DrawerLayout drawerLayout;
            NavigationView navigationView;
             ImageView challenge;
             public Intent intent;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home,container,false);
            recyclerView=view.findViewById(R.id.recycler_view);

            //Menu Hooks
            drawerLayout=view.findViewById(R.id.drawer_layout);
            navigationView=view.findViewById(R.id.nav_view1);
            //Navigation Drawer
            navigationView.bringToFront();
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setCheckedItem(R.id.nav_homenav);
        //challenge
        challenge=view.findViewById(R.id.challenge);

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeFragment.this.getActivity(),challengeActivity.class);
                HomeFragment.this.startActivity(intent);

            }
        });
        //
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        postLists=new ArrayList<>();
        postAdapter=new PostAdapter(getContext(),postLists);
        recyclerView.setAdapter(postAdapter);

        checkFollowing();

        return view;
    }
    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch(menuItem.getItemId()){

                        case R.id.nav_homenav :
                            Intent intent=new Intent(HomeFragment.this.getActivity(),MainActivity.class);
                            HomeFragment.this.startActivity(intent);
                            break;

                        case R.id.nav_bmi :
                            intent=new Intent(HomeFragment.this.getActivity(), BmiActivity.class);
                            HomeFragment.this.startActivity(intent);
                            break;

                        case R.id.nav_cal :
                            intent=new Intent(HomeFragment.this.getActivity(), CalActivity.class);
                            HomeFragment.this.startActivity(intent);
                            break;

                        case R.id.nav_logout :
                           intent=new Intent(HomeFragment.this.getActivity(), LoginActivity.class);
                            HomeFragment.this.startActivity(intent);
                            break;
                    }
                    return false;
                }
            };


    private void checkFollowing(){
        followingList=new ArrayList<>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Follow")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Following");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    followingList.add(snapshot.getKey());
                }
                readPosts();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readPosts(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postLists.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Post post = snapshot.getValue(Post.class);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                        System.out.println("PostID"+Objects.requireNonNull(post).getPublisher());

                        for (String id : followingList) {
                            if (Objects.equals(post.getPublisher(), id)) {
                                postLists.add(post);
                            }

                        }
                    }
                    System.out.println("followingList"+followingList.size());

                    System.out.println("PostLists"+postLists);
                }

                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        return true;
    }
}