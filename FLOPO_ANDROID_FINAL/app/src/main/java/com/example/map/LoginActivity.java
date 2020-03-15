package com.example.map;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;


public class LoginActivity extends AppCompatActivity {
    //SessionCallback 선언
    private SessionCallback sessionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //상단의 액션 바 숨기기00
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
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
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

                @Override
                public void onFailure(ErrorResult errorResult) {//로그인에 실패했을 때. 인터넷 연결이 불안정한 경우도 여기에 해당한다.
                    int result = errorResult.getErrorCode();

                    if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) { //로그인 도중 세션이 비정상적인 이유로 닫혔을 때
                    Toast.makeText(getApplicationContext(), "세션이 닫혔습니다. 다시 시도해 주세요: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) { //로그인에 성공했을 때
                    String needsScopeAutority = ""; // 정보 제공이 허용되지 않은 항목의 이름을 저장하는 변수

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

                    if (needsScopeAutority.length() != 0) { // 정보 제공이 허용되지 않은 항목이 있다면 -> 허용되지 않은 항목을 안내하고 회원탈퇴 처리
                        if (needsScopeAutority.charAt(0) == ',') {
                            needsScopeAutority = needsScopeAutority.substring(2);
                        }
                        Toast.makeText(getApplicationContext(), needsScopeAutority + "에 대한 권한이 허용되지 않았습니다. 개인정보 제공에 동의해주세요.", Toast.LENGTH_SHORT).show(); // 개인정보 제공에 동의해달라는 Toast 메세지 띄움


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

                    } else { // 모든 항목에 동의했다면 -> 유저 정보를 가져와서 MainActivity에 전달하고 MainActivity 실행.
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
                        if (result.getKakaoAccount().hasGender() == OptionalBoolean.TRUE)
                            intent.putExtra("gender", result.getKakaoAccount().getGender().getValue());
                        else
                            intent.putExtra("gender", "none");
                        if (result.getKakaoAccount().hasBirthday() == OptionalBoolean.TRUE)
                            intent.putExtra("birthday", result.getKakaoAccount().getBirthday());
                        else
                            intent.putExtra("birthday", "none");

                        startActivity(intent);
                        finish();

                    }
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) { //로그인 세션이 정상적으로 열리지 않았을 때
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}


