package com.example.map.ui.home;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.map.GpsTracker;
import com.example.map.HttpClient;
import com.example.map.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.LOCATION_SERVICE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private WebView webView; //웹뷰 선언
    private TextView textTime; //텍스트뷰 선언
    private TextView name, email;

    //gps
    private double latitude; //위도
    private double longitude; //경도

    private GpsTracker gpsTracker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    //시간
    int y = 0, m = 0, d = 0, h = 0, mi = 0; //년,월,일,시,분

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        //웹뷰
        String MAP_URL = "http://211.183.9.69:8080/map/mapview"; //웹뷰에 표시할 웹사이트 주소, 웹뷰 시작

        webView = (WebView) root.findViewById(R.id.webview); //레이어와 연결

        //JavaScript enable, Geolocation enable, Geolocation caching을 위한 DB path 설정
        webView.getSettings().setGeolocationEnabled(true); //웹뷰 위치정보 확인 설정
        webView.getSettings().setJavaScriptEnabled(true); //웹페이지 자바스크립트 허용 여부
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //웹뷰가 앱에 등록되어 있는 이미지 리소스를 자동으로 로드하도록 설정

        //webView.getSettings().setGeolocationDatabasePath(getFilesDir().getPath()); //지리적 위치 db 저장 경로 설정
        //webView.getSettings().setBuiltInZoomControls(true); //화면 확대 축소 허용 여부

        // 화면 비율
        webView.getSettings().setUseWideViewPort(true);       // 화면 사이즈 맞추기 허용 여부
        webView.getSettings().setLoadWithOverviewMode(true);  // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        webView.setInitialScale(300); //비율 조절

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //컨텐츠 사이즈 맞추기

        //html관련 설정
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        //성능 향상
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null); // 속도 향상
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //캐시

        // 새로운 창을 띄우지 않고 내부에서 웹뷰를 실행시킨다.
        webView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                callback.invoke(origin, true, false);
            }
        });
        webView.loadUrl(MAP_URL);


        /**
         * 현재 시간 버튼
         */
        textTime = root.findViewById(R.id.textTime);
        //getDT(); //앱 실행하자마자 현재시간 띄우기 (200108 등록)

        Button button1 = root.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDT();
            }
        });

        Timer timer1 = new Timer();

        timer1.schedule(new TimerTask() {
            @Override
            public void run() {

                SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmm");
                String format_time1 = format1.format(System.currentTimeMillis());

                //스프링으로 데이터 전송
                NetworkTask2 networkTask = new NetworkTask2();

                Map<String, String> params = new HashMap<String, String>();
                params.put("setTime", format_time1 + ""); //현재 시간은 데이터를 넘기지 않을거지만 테스트 위해 구현해놓음.

                networkTask.execute(params);
            }
        }, 0, 1000 * 30);//10초


        //시간, 날짜설정 버튼 (하나로 합침. 200115)
        textTime = (TextView) root.findViewById(R.id.textTime);
        Button button2 = root.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
                showTime();
            }
        });

        //적용 버튼
        textTime = (TextView) root.findViewById(R.id.textTime);
        Button button3 = root.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textTime.setText(y + "년 " + m + "월 " + d + "일 " + h + "시 " + mi + "분");

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (m < 9 || d < 10 || h < 10 || mi < 10) {

                            //스프링으로 데이터 전송
                            NetworkTask2 networkTask = new NetworkTask2();

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("setTime", y + "" + String.format("%02d", m) + "" + String.format("%02d", d) + "" + String.format("%02d", h) + "" + String.format("%02d", mi) + "");
                            networkTask.execute(params);
                        }
                    }
                }, 0, 1000 * 30); //10초
            }
        });

        /**
         * 위치
         */

        // 현재위치 확인
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }

        /**
         * 위치 버튼 필요 없지만 일단 놔둠.
         */
        /*Button ShowLocationButton = (Button) findViewById(R.id.button4);
        ShowLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                gpsTracker = new GpsTracker(MainActivity.this);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        double latitude = gpsTracker.getLatitude();
                        double longitude = gpsTracker.getLongitude();

                        //Toast.makeText(MainActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();

                        //스프링으로 데이터 전송
                        NetworkTask2 networkTask = new NetworkTask2();

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("latitude", Double.toString(latitude));
                        params.put("longitude", Double.toString(longitude));

                        networkTask.execute(params);
                    }
                }, 1000, 1000 * 60 * 1); //10초
            }
        });*/

        gpsTracker = new GpsTracker(getContext());

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                //Toast.makeText(MainActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();

                //스프링으로 데이터 전송
                NetworkTask2 networkTask = new NetworkTask2();

                Map<String, String> params = new HashMap<String, String>();
                params.put("latitude", Double.toString(latitude));
                params.put("longitude", Double.toString(longitude));

                networkTask.execute(params);
            }
        }, 0, 1000 * 30); //10초..?

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * 스프링 - 안드로이드. 서버 주소 넣은 코드 여기 있음
     */
    public class NetworkTask2 extends AsyncTask<Map<String, String>, Integer, String> {

        @Override
        protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

            // Http 요청 준비 작업
            HttpClient.Builder http = new HttpClient.Builder("POST", "http://211.183.9.77:8080/aa/android3"); //예진
            //HttpClient.Builder http = new HttpClient.Builder("POST", "http://211.183.9.69:8080/map/android3"); //원석

            // Parameter 를 전송한다.
            http.addAllParameters(maps[0]);

            //Http 요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 상태코드 가져오기
            int statusCode = post.getHttpStatusCode();

            // 응답 본문 가져오기
            String body = post.getBody();

            return body;
        }

        @Override
        protected void onPostExecute(String s) { //doInBackground에서 받아온 total 값 사용 장소
            Map<String, String> params = new HashMap<>();
            Log.d("JSON_RESULT", s);
            params.put("현재 시간", "y+\"년 \"+m+\"월 \"+d+\"일 \"+h+\"시 \"+mi+\"분\"");
            params.put("설정 시간", "y+\"년 \"+m+\"월 \"+d+\"일 \"+h+\"시 \"+mi+\"분\"");

            /*Gson gson = new Gson();

            String output = gson.toJson(params);*/
            //JsonObject object = new JsonObject();
            //object.addProperty("currentTime","settingTime");
            //String json = gson.toJson(object);
            //System.out.println(json);

            //Data data = gson.fromJson(s, Data.class);
            //Log.d("JSON_RESULT", data.getCurrentTime());
            //Log.d("JSON_RESULT", data.getSettingTime());
            //Log.d("JSON_RESULT", String.valueOf(data.getLatitude()));
            //Log.d("JSON_RESULT", String.valueOf(data.getLongitude()));

            /*textTime.setText(y + "년 " + m + "월 " + d + "일 " + h + "시 " + mi + "분");
            textTime.setText(y + "년 " + m + "월 " + d + "일 " + h + "시 " + mi + "분");*/
        }
    }

    void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                y = year;
                m = month + 1;
                d = dayOfMonth;

            }
        }, 2019, 12, 01);



        datePickerDialog.setMessage("날짜를 선택해주세요");
        datePickerDialog.show();
    }

    void showTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mi = minute;

            }
        }, 12, 00, true);

        timePickerDialog.setMessage("시간을 선택해주세요");
        timePickerDialog.show();
    }

    public void getDT() {

        Calendar cal = Calendar.getInstance();

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH) + 1;
        d = cal.get(Calendar.DAY_OF_MONTH);
        h = cal.get(Calendar.HOUR_OF_DAY);
        mi = cal.get(Calendar.MINUTE);

        textTime.setText(y + "년 " + m + "월 " + d + "일 " + h + "시 " + mi + "분");
    }

    /**
     * 위치 허용 관련 코드
     *
     * @param permsRequestCode
     * @param permissions
     * @param grandResults
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {
                //위치 값을 가져올 수 있음
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(getContext(), "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission() {

        /**
         * 런타임 퍼미션 처리
         */

        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(getContext(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 7);
        } catch (IOException ioException) {

            //네트워크 문제
            Toast.makeText(getContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


}
