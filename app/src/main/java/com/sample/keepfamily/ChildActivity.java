package com.sample.keepfamily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.Timer;
import java.util.TimerTask;

public class ChildActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private FusedLocationSource locationSource;
    private NaverMap naverMap;

    double latitude = 0;
    double longitude = 0;

    int timecount = 1;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("id");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);//지도 객체 생성
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        locationSource = new FusedLocationSource(this, ACCESS_LOCATION_PERMISSION_REQUEST_CODE); //위치코드
        naverMap.setLocationSource(locationSource); //지도상의 로케이션 활성화
        UiSettings uiSettings = naverMap.getUiSettings(); //ui 세팅에 대한 객체
        uiSettings.setCompassEnabled(true);// 나침반 활성화
        uiSettings.setLogoClickEnabled(false); // 네이버 클릭 비활성화

        naverMap.setLocationTrackingMode(LocationTrackingMode.Face);//키자마자 트래킹 모드

        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() { // 실시간 위치를 토스트를 통해서 위도,경도를 표시해주는 곳.
            @Override
            public void onLocationChange(@NonNull Location location) {
                latitude = location.getLatitude(); //위도 값 넘기기
                longitude = location.getLongitude(); //경도 값 넘기기

            }
        });

        //타이머 부분(예제에 나와 있는 부분)
        TimerTask Taskact = new TimerTask() {
            public void run() { //주기적으로 실행할 작업 추가
                databaseReference.child("id").child("myposition").child("lat").setValue(latitude, timecount); //파이어 베이스에 위도 값 주기적으로 넘기기
                databaseReference.child("id").child("myposition").child("lon").setValue(longitude, timecount); //파이어 베이스에 경도 값 주기적으로 넘기기
                timecount++; //timecount 계속 누적 시키기

            }
        };
        Timer timer = new Timer();
        timer.schedule(Taskact, 1000, timecount * (30 * 100)); //// 60초후 첫실행, timecount분마다 계속실행

    }
}