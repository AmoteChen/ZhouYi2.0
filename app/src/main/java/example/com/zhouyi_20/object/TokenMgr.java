package example.com.zhouyi_20.object;

import android.content.SharedPreferences;

import java.util.Set;

public class TokenMgr {
    private static SharedPreferences.Editor editor;

    public static void initToken(SharedPreferences sp) {
        editor = sp.edit();
    }

    public static void putString(final String key, final String value) {
        editor.putString(key, value);
    }

    public static void putInt(final String key, final int value) {
        editor.putInt(key, value);
    }

    public static void putLong(final String key, final long value) {
        editor.putLong(key, value);
    }

    public static void putFloat(final String key, final float value) {
        editor.putFloat(key, value);
    }

    public static void putBoolean(final String key, final boolean value) {
        editor.putBoolean(key, value);
    }

    public static void putStringSet(final String key, final Set<String> value) {
        editor.putStringSet(key, value);
    }

    public static void commit() {
        editor.commit();
    }
}
