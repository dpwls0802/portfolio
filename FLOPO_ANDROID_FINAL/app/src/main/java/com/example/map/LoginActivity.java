package com.example.map;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

import java.util.HashMap;
import java.util.Map;

/**
 * 로그인 및 인트로 클래스.
 */
public class LoginActivity extends AppCompatActivity {

    //SessionCallback 선언
    private SessionCallback sessionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //상단의 액션 바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sessionCallback = new SessionCallback(); //SessionCallback 초기화
        Session.getCurrentSession().addCallback(sessionCallback); //현재 세션에 콜백 붙임
        // checkAndImplicitOpen()
        // 현재 앱에 유효한 카카오 로그인 토큰이 있다면 바로 로그인을 시켜주는 함수.
        // 즉, 이전에 로그인한 기록이 있다면, 다음 번에 앱을 켰을 때 자동으로 로그인을 시켜주는 것
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //카카오 로그인 액티비티에서 넘어온 경우일 때 실행
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //현재 액티비티 제거 시 콜백도 같이 제거
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    //SessionCallback은 ISessionCallback을 상속받은 카카오 로그인 콜백
    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() { //로그인 세션이 제대로 열렸을 때
            UserManagement.getInstance().me(new MeV2ResponseCallback() {

                @Override   //로그인에 실패했을 때. 인터넷 연결이 불안정한 경우도 여기에 해당한다.
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();

                    if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(),
                                "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "로그인 도중 오류가 발생했습니다: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override  //로그인 도중 세션이 비정상적인 이유로 닫혔을 때
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(getApplicationContext(),
                            "세션이 닫혔습니다. 다시 시도해 주세요: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override   //로그인에 성공했을 때
                public void onSuccess(MeV2Response result) {

                    String needsScopeAutority = ""; // 정보 제공이 허용된 항목의 이름을 저장하는 변수

                    // 이메일, 성별, 연령대, 생일 정보를 제공하는 것에 동의했는지 체크
                    if (result.getKakaoAccount().needsScopeAccountEmail()) {
                        needsScopeAutority = needsScopeAutority + "이메일";
                    }
                    if (result.getKakaoAccount().needsScopeGender()) {
                        needsScopeAutority = needsScopeAutority + ", 성별";
                    }
                    if (result.getKakaoAccount().needsScopeAgeRange()) {
                        needsScopeAutority = needsScopeAutority + ", 연령대";
                    }
                    if (result.getKakaoAccount().needsScopeBirthday()) {
                        needsScopeAutority = needsScopeAutority + ", 생일";
                    }

                    // 정보 제공이 허용되지 않은 항목이 있다면 -> 허용되지 않은 항목을 안내하고 회원탈퇴 처리
                    if (needsScopeAutority.length() != 0) {
                        if (needsScopeAutority.charAt(0) == ',') {
                            needsScopeAutority = needsScopeAutority.substring(2);
                        }
                        Toast.makeText(getApplicationContext(),
                                needsScopeAutority + "에 대한 권한이 허용되지 않았습니다. 개인정보 제공에 동의해주세요.", Toast.LENGTH_SHORT).show();

                        // 회원탈퇴 처리
                        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                            @Override
                            public void onFailure(ErrorResult errorResult) {
                                int result = errorResult.getErrorCode();

                                if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                    Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onSessionClosed(ErrorResult errorResult) {
                                Toast.makeText(getApplicationContext(), "로그인 세션이 닫혔습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNotSignedUp() {
                                Toast.makeText(getApplicationContext(), "가입되지 않은 계정입니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(Long result) {
                            }
                        });

                    } else {

                        // 모든 항목에 동의했다면 -> 유저 정보를 가져와서 MainActivity에 전달하고 MainActivity 실행.
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class); //인텐트 생성
                        intent.putExtra("name", result.getNickname());
                        intent.putExtra("profile", result.getProfileImagePath());

                        if (result.getKakaoAccount().hasEmail() == OptionalBoolean.TRUE)
                            intent.putExtra("email", result.getKakaoAccount().getEmail());
                        else
                            intent.putExtra("email", "none");
                        if (result.getKakaoAccount().hasAgeRange() == OptionalBoolean.TRUE)
                            intent.putExtra("ageRange", result.getKakaoAccount().getAgeRange().getValue());
                        else
                            intent.putExtra("ageRange", "none");
                        if (result.getKakaoAccount().legalGenderNeedsAgreement() == OptionalBoolean.TRUE)
                            intent.putExtra("gender", result.getKakaoAccount().getGender().getValue());
                        else
                            intent.putExtra("gender", "none");
                        if (result.getKakaoAccount().legalBirthDateNeedsAgreement() == OptionalBoolean.TRUE)
                            intent.putExtra("birthday", result.getKakaoAccount().getLegalBirthDate());
                        else
                            intent.putExtra("birthday", "none");

                        //스프링으로 데이터 전송
                        NetworkTask2 networkTask = new NetworkTask2();

                        Map<String, String> params = new HashMap<String, String>(); //서버에 데이터 전송
                        params.put("name", result.getNickname() + "");
                        params.put("email", result.getKakaoAccount().getEmail() + "");
                        params.put("ageRange", result.getKakaoAccount().getAgeRange().getValue() + "");
                        params.put("gender", result.getKakaoAccount().getGender().getValue() + "");

                        networkTask.execute(params);

                        startActivity(intent); //인텐트 실행
                        finish();

                    }
                }
            });
        }

        //스프링-안드로이드
        public class NetworkTask2 extends AsyncTask<Map<String, String>, Integer, String> {

            @Override
            protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

                // Http 요청 준비 작업
                HttpClient.Builder http = new HttpClient.Builder("POST", "http://211.183.9.77:8080/aa/android3"); //예진

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

                Gson gson = new Gson();

                String output = gson.toJson(params);
                //JsonObject object = new JsonObject();
                //object.addProperty("currentTime","settingTime");
                //String json = gson.toJson(object);
                //System.out.println(json);

                //Data data = gson.fromJson(s, Data.class);
                //Log.d("JSON_RESULT", data.getCurrentTime());
                //Log.d("JSON_RESULT", data.getSettingTime());
                //Log.d("JSON_RESULT", String.valueOf(data.getLatitude()));
                //Log.d("JSON_RESULT", String.valueOf(data.getLongitude()));

                //textTime.setText(y + "년 " + m + "월 " + d + "일 " + h + "시 " + mi + "분");
                //textTime.setText(y + "년 " + m + "월 " + d + "일 " + h + "시 " + mi + "분");
            }
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) { //로그인 세션이 정상적으로 열리지 않았을 때
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}


