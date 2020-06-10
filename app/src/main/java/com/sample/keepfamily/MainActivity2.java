package com.sample.keepfamily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sample.keepfamily.fragment.Markfragment;
import com.sample.keepfamily.fragment.NaverMapFragment;
import com.sample.keepfamily.fragment.RecordFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;

    Markfragment markf = new Markfragment();
    NaverMapFragment navermap = new NaverMapFragment();
    RecordFragment record = new RecordFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.mainactivity_bottomnavigationview);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_framelayout, navermap).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_map:
                        fragmentTransaction.replace(R.id.mainactivity_framelayout,navermap).commitAllowingStateLoss();
                        return true;
                    case R.id.action_mark:
                        fragmentTransaction.replace(R.id.mainactivity_framelayout,markf).commitAllowingStateLoss();
                        return true;
                    case R.id.action_record:
                        fragmentTransaction.replace(R.id.mainactivity_framelayout,record).commitAllowingStateLoss();
                        return true;
                }

                return false;
            }
        });
        passPushTokenToServer();

    }
    void passPushTokenToServer(){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String token = FirebaseInstanceId.getInstance().getToken();
        Map<String,Object> map = new HashMap<>();
        map.put("pushToken",token);

        FirebaseDatabase.getInstance().getReference().child("users").child(uid).updateChildren(map);


    }
}