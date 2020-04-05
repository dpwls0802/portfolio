package com.example.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

/**
 * 메인 클래스.
 */
public class MainActivity extends AppCompatActivity{
    private AppBarConfiguration mAppBarConfiguration; //앱바(기본 코드)

    private long time= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //activity_main.xml 화면으로 표시

        //툴바 (기본 코드)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * 기본 드로어 레이아웃, 네비게이션 뷰
         */

        //전체 화면 설정
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //네비게이션 화면 설정
        NavigationView navigationView = findViewById(R.id.nav_view);

        /**
        내비게이션 헤더에 개인정보 입력 완료 (200204)
         */
        View nav_header_view = navigationView.getHeaderView(0);

        //ImageView profile = (ImageView) nav_header_view.findViewById(R.id.profile);
        TextView name = (TextView) nav_header_view.findViewById(R.id.name);
        TextView email = (TextView) nav_header_view.findViewById(R.id.email);

        Intent passedIntent = getIntent();

        if (passedIntent != null) {
            byte[]arr = getIntent().getByteArrayExtra("profile");
            //image = BitmapFactory.decodeByteArray(arr,0,arr.length);

            //Image profile1 = passedIntent.getExtras().get("profile");
            //String profile1 = passedIntent.getExtras().getString("profile");
            String name1 = passedIntent.getExtras().getString("name");
            String email1 = passedIntent.getExtras().getString("email");

            //profile.setImageBitmap(image);
            name.setText(name1);
            email.setText(email1);
        }

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_notice, R.id.nav_board, R.id.nav_logout, R.id.nav_people, R.id.nav_information)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     *  웹뷰 위치권한 메소드. 테스트 중
     private void permissionCheck () {
     if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
     //Manifest.permission.ACCESS_FINE_LOCATION 접근 승낙 상태 일때
     initWebView();
     } else {
     //Manifest.permission.ACCESS_FINE_LOCATION 접근 거절 상태 일때
     // 사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄운다.
     ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
     }
     }
     @Override public void onRequestPermissionsResult ( int requestCode,
     @NonNull String[] permissions, @NonNull int[] grantResults){
     super.onRequestPermissionsResult(requestCode, permissions, grantResults);
     if (requestCode == MY_PERMISSION_REQUEST_LOCATION) {
     initWebView();
     }
     }
     */


    /**
     * 뒤로 가기 버튼 눌렀을 때 토스트
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - time < 2000) {
            finish();
        }
    }

    /**
     * 기본 오버라이드
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}

