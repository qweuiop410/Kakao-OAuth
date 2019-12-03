package com.example.kakaooauth;

import android.app.Application;
import android.util.Log;

import com.kakao.auth.KakaoSDK;

public class GlobalApplication extends Application {
    private static GlobalApplication instance;

    public static GlobalApplication getGlobalApplicationContext() {
        Log.d("     MSG     : ","GlobalApplication getGlobalApplicationContext");

        if (instance == null) {
            throw new IllegalStateException("This Application does not inherit com.kakao.GlobalApplication");
        }

        return instance;
    }

    @Override
    public void onCreate() {
        Log.d("     MSG     : ","GlobalApplication onCreate");
        super.onCreate();
        instance = this;

        // Kakao Sdk 초기화
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    @Override
    public void onTerminate() {
        Log.d("     MSG     : ","GlobalApplication onTerminate");
        super.onTerminate();
        instance = null;
    }
}