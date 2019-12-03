package com.example.kakaooauth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class SuccessActivity extends AppCompatActivity {

    private TextView mTextView_nickName;
    private TextView mTextView_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        String nickName =  bundle.getString("nickName");

        mTextView_nickName = findViewById(R.id.TextView_kakaoName);
        mTextView_welcome = findViewById(R.id.TextView_welcome);

        mTextView_nickName.setText(nickName + " 님");

        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(10)
                .playOn(mTextView_welcome);

        TextView textview_logOut = findViewById(R.id.TextView_logout);
        textview_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent intent = new Intent(SuccessActivity.this,MainActivity.class);//new Intent(현재액티비티.this,전달할 액티비티.class)
                        startActivity(intent);
                    }
                });
            }
        });
    }
}