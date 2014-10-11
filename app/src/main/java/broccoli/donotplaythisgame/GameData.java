package broccoli.donotplaythisgame;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dannydelott on 10/11/14.
 */
public class GameData {

    /**
     * Creates a new SharedPreference in the given Context.
     *
     * @param c    Context (usually the Activity calling the method)
     * @param name Name of the SharedPreference (will be used to edit and everything else)
     * @param mode private or global? (see SharedPreferences documentation)
     * @return SharedPreferences object
     */
    public static SharedPreferences createNewSharedPreference(Context c, String name, int mode) {
        SharedPreferences pref = c.getSharedPreferences(name, mode);
        if (pref == null) {
            return null;
        }
        return pref;
    }

    /**
     * Adds a new key/value to the given SharedPreference.
     *
     * @param preferences SharedPreferences object to put int into
     * @param key         Name of integer
     * @param value       Value of integer
     */
    public static void putInt(SharedPreferences preferences, String key, int value) {

        SharedPreferences.Editor editor = preferences.edit();

        // puts the key/value into the SharedPreferences
        editor.putInt(key, (Integer) value);

        // applies changes
        editor.apply();
    }

}
