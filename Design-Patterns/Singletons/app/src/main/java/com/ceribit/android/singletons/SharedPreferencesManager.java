package com.ceribit.android.singletons;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    // settings
    private static final String APP_SETTINGS = "APPLICATION_SETTINGS";

    // properties
    private static final String EDIT_TEXT_TITLE = "EDIT_TEXT_TITLE";

    private static final String EDIT_TEXT_DESCRIPTION = "EDIT_TEXT_DESCRIPTION";

    // shared preferences instance
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private SharedPreferencesManager() {}

    public static void init(Context context){
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    public static SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }

    public static boolean setTitle(String title){
        editor.putString(EDIT_TEXT_TITLE, title);
        return editor.commit();
    }

    public static String getTitle(){
        return sharedPreferences.getString(EDIT_TEXT_TITLE, "");
    }

    public static boolean setDescription(String description){
        editor.putString(EDIT_TEXT_DESCRIPTION, description);
        return editor.commit();
    }

    public static String getDescription(){
        return sharedPreferences.getString(EDIT_TEXT_DESCRIPTION, "");
    }

}
