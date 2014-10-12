package broccoli.donotplaythisgame;

import level1to5.ActivityLevel1;
import level1to5.ActivityLevel2;

/**
 * Created by dannydelott on 10/10/14.
 */
public class Levels {

    // Default value when getting SharedPreferences
    public static final int DEFAULT_INT = -1;

    // Add new ActivityLevel classes to this array to make it available to the ActivitySelector
    public static final Class[] levels = new Class[]{
            ActivityLevel1.class, ActivityLevel2.class
    };

    // Add a new String to this array to give a label to new levels in the ActivitySelector
    public static final int[] levelNumbers = new int[]{
            1, 2, 3, 4, 5,
            6, 7, 8, 9, 10,
            11, 12, 13, 14, 15,
            16, 17, 18, 19, 20
    };
}
