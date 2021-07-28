package com.cw.exitriga.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.cw.exitriga.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            // Firebase Token
//            sessionManager.setSavedFcmtoken(FirebaseInstanceId.getInstance().getToken());

            //Device Id
            String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//            sessionManager.setSavedDeviceid(m_androidId);

            Thread background = new Thread() {
                public void run() {
                    try {
                        sleep(3 * 1000);

                        Intent in = new Intent(SplashActivity.this, ChooseLanguagesActivity.class);
                        startActivity(in);
                        finish();

                    } catch (Exception e) {
                    }
                }
            };
            // start thread
            background.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
