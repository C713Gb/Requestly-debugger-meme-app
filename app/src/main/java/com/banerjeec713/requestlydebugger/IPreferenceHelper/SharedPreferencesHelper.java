package com.banerjeec713.requestlydebugger.IPreferenceHelper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private final SharedPreferences sharedPreferences;
    private static SharedPreferencesHelper mInstance = null;
    public static final String TIMES = "times";
    public static final String LIKES = "likes";
    public static final String DISLIKES = "dislikes";

    public static synchronized SharedPreferencesHelper getInstance(Context context) {
        return mInstance == null ? mInstance = new SharedPreferencesHelper(context) : mInstance;
    }

    private SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void incrementTimes() {
        int time = getTimes();
        time++;
        setTimes(time);
    }

    public void incrementLikes() {
        int likes = getLikes();
        likes++;
        setLikes(likes);
    }

    public void incrementDislikes() {
        int likes = getDislikes();
        likes++;
        setDislikes(likes);
    }

    public void setTimes(int time) {
        sharedPreferences.edit()
                .putString(TIMES, Integer.toString(time))
                .apply();
    }

    public void setLikes(int likes) {
        sharedPreferences.edit()
                .putString(LIKES, Integer.toString(likes))
                .apply();
    }

    public void setDislikes(int likes) {
        sharedPreferences.edit()
                .putString(DISLIKES, Integer.toString(likes))
                .apply();
    }

    public int getTimes() {
        return Integer.parseInt(sharedPreferences.getString(TIMES, "0"));
    }

    public int getLikes() {
        return Integer.parseInt(sharedPreferences.getString(LIKES, "0"));
    }

    public int getDislikes() {
        return Integer.parseInt(sharedPreferences.getString(DISLIKES, "0"));
    }

}
