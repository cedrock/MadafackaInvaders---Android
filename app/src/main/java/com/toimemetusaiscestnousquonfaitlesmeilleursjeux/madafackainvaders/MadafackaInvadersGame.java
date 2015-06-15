package com.toimemetusaiscestnousquonfaitlesmeilleursjeux.madafackainvaders;

/**
 * Created by c.distelmans on 14-06-15.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.util.Log;
import android.view.WindowManager;

import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;


public class MadafackaInvadersGame extends AndroidGame{

    public static String map;
    public static int screenRotation;
    boolean firstTimeCreate=true;

    @Override
    public Screen getInitScreen() {

        if(firstTimeCreate){
            firstTimeCreate=false;
        }

        InputStream is = getResources().openRawResource(R.raw.map1);
        map=convertStreamToString(is);

        return new SplashLoadingScreen(this);
    }

    @Override
    public void onBackPressed() {
        getCurrentScreen().backButton();
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            Log.w("LOG", e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.w("LOG", e.getMessage());
            }
        }
        return sb.toString();
    }

    @Override
    public void onResume() {
        super.onResume();

        WindowManager windowMgr = (WindowManager) this
                .getSystemService(Activity.WINDOW_SERVICE);
        screenRotation = windowMgr.getDefaultDisplay().getRotation();
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
