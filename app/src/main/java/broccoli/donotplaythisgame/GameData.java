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
     * @return TRUE if created, FALSE if already exists or error
     */
    public static boolean createNewSharedPreference(Context c, String name, int mode) {
        SharedPreferences gameData = c.getSharedPreferences(name, mode);
        if (gameData == null) {
            return false;
        }
        return true;
    }

    /**
     * Adds a new key/value to the given SharedPreference.
     *
     * @param c    Context (usually the Activity calling the method)
     * @param name Name of the SharedPreference (will be used to edit and everything else)
     * @param mode private or global? (see SharedPreferences documentation)
     * @param type Type of object going into the SharedPreference (see SharedPreferenceType)
     * @param key Name of SharedPreference extra
     * @param value Value to store at the key
     *
     * @return TRUE if successful, FALSE on error
     */
    public static boolean putExtra(Context c, String name, int mode, SharedPreferenceType type,
                                   String key, Object value) {

        // gets the preferences
        SharedPreferences preferences = c.getSharedPreferences(name, mode);
        SharedPreferences.Editor editor = preferences.edit();

        // puts the key/value into the SharedPreferences
        switch(type){
            case INTEGER:
                editor.putInt(key, (Integer) value);
                break;
            default:
                break;
        }

        // applies changes
        editor.apply();

        return true;
    }
}
