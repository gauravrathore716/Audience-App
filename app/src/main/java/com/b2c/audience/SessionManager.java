package com.b2c.audience;

import android.content.Context;
import android.content.SharedPreferences;

import com.b2c.audience.model.ModelUserInfo;


public class SessionManager {

    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String DOB = "dob";
    public static final String PROFILE = "profile";



    private static final String PREF_DEVICE_TOKEN = "device_token";


    private static final String PREF_NAME = "Audience";
    private static SharedPreferences mSharedPreferences;
    private Context context;

    public SessionManager(Context context) {

        this.context = context;
    }

    private static SharedPreferences getSharedPref(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public static void setDeviceToken(Context context, String id) {
        SharedPreferences.Editor editor = getSharedPref(context).edit();
        editor.putString(PREF_DEVICE_TOKEN, id);
        editor.apply();
    }

    public static String getDeviceToken(Context context) {
        return getSharedPref(context).getString(PREF_DEVICE_TOKEN, "");
    }

    public static void saveUserInfo(Context context, ModelUserInfo userInfo) {

        SharedPreferences.Editor editor = getSharedPref(context).edit();
         //Storing login value as TRUE
        editor.putInt(ID, userInfo.getId());
        editor.putString(FIRST_NAME, userInfo.getFirst_name());
        editor.putString(NAME, userInfo.getName());
        editor.putString(EMAIL, userInfo.getEmail());
        editor.putString(DOB, userInfo.getDob());
        editor.putString(PROFILE, userInfo.getProfile_pic());

       //  commit changes
        editor.apply();
    }

    public static String getString(Context context, String key) {

        return getSharedPref(context).getString(key, "");
    }
    public static int getInt(Context context, String key) {

        return getSharedPref(context).getInt(key, 0);
    }

    public static String getUserImage(Context context) {
        return getSharedPref(context).getString(PROFILE, "");
    }

    public static void setUserImage(Context context, String id) {
        SharedPreferences.Editor editor = getSharedPref(context).edit();
        editor.putString(PROFILE, id);
        editor.commit();
    }

    public static void clearAll(Context context) {

        getSharedPref(context).edit()
                .clear()
                .apply();
    }


}
