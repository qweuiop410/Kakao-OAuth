package com.example.kakaooauth;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private Button btn_custom_login;
    //private LoginButton btn_kakao_login;
    private Button btn_kakao_login;
    private Button btn_kakao_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        getHashKey(mContext);

        //btn_kakao_login = (LoginButton) findViewById(R.id.btn_kakao_login);
        btn_kakao_login = (Button) findViewById(R.id.btn_custom_login);
        btn_kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session session = Session.getCurrentSession();
                session.addCallback(new SessionCallback());
                session.open(AuthType.KAKAO_LOGIN_ALL, MainActivity.this);

                //btn_kakao_login.performClick(); //기본 제공되는 버튼 클릭
            }
        });

        btn_kakao_logout = (Button) findViewById(R.id.btn_custom_logout);
        btn_kakao_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("     Click22   : ", "  Click   ");
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                //btn_kakao_login.performClick(); //기본 제공되는 버튼 클릭
            }
        });
    }

    // 프로젝트의 해시키를 반환
    @Nullable
    public static String getHashKey(Context context) {
        final String TAG = "    KeyHash     :   ";
        String keyHash = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }

        if (keyHash != null) {
            return keyHash;
        } else {
            return null;
        }
    }

    private class SessionCallback implements ISessionCallback {

        // 로그인에 성공한 상태
        @Override
        public void onSessionOpened() {
            Log.d("     Success : ","      Success     ");
            requestMe();
        }

        // 로그인에 실패한 상태
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("     SessionCallback ::", "onSessionOpenFailed : " + exception.getMessage());
        }

        // 사용자 정보 요청
        public void requestMe() {
            // 사용자정보 요청 결과에 대한 Callback
            UserManagement.requestMe(new MeResponseCallback() {
                // 세션 오픈 실패. 세션이 삭제된 경우,
                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.e("     SessionCallback ::", "onSessionClosed : " + errorResult.getErrorMessage());
                }

                // 회원이 아닌 경우,
                @Override
                public void onNotSignedUp() {
                }

                // 사용자정보 요청에 성공한 경우,
                @Override
                public void onSuccess(UserProfile userProfile) {

                    String nickname = userProfile.getNickname();
                    String profileImagePath = userProfile.getProfileImagePath();
                    String thumnailPath = userProfile.getThumbnailImagePath();
                    long id = userProfile.getId();

                    Log.e(" nickname : ", nickname + "");
                    Log.e(" profileImage : ", profileImagePath  + "");
                    Log.e(" thumnailPath : ", thumnailPath + "");
                    Log.e(" id : ", id + "");

                    Intent intent = new Intent(MainActivity.this,SuccessActivity.class);//new Intent(현재액티비티.this,전달할 액티비티.class)
                    intent.putExtra("nickName",nickname);
                    intent.putExtra("imgPath",profileImagePath);
                    startActivity(intent);
                }

                // 사용자 정보 요청 실패
                @Override
                public void onFailure(ErrorResult errorResult) {
                }
            });
        }
    }
}