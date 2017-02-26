package org.shreyans.greendot.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.shreyans.greendot.models.Goal;
import org.shreyans.greendot.models.Goal_Table;

import java.util.List;

public class GoalHelper {

    private static final String TAG = GoalHelper.class.getSimpleName();

    private static final String prefsSuffix = "_v1";
    private static final String sharedPrefName = "prefs" + prefsSuffix;
    private static final String prefGoalsCreatedKey = "goalsCreated" + prefsSuffix;

    public static List<Goal> getActiveGoals() {
        List<Goal> goals = SQLite.select()
                .from(Goal.class)
                .where(Goal_Table.active.is(true))
                .queryList();
        return goals;
    }

    public static void logGoals(List<Goal> goals) {
        Log.d(TAG, goals.size() + " active goals:");
        for (Goal g: goals) {
            Log.d(TAG, g.name + " @ " + g.freq);
        }
    }

    public static void deleteAllGoals(Context context) {
        SQLite.delete()
                .from(Goal.class)
                .where(Goal_Table.active.is(true))
                .execute();
        SharedPreferences db = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        db.edit().putBoolean(prefGoalsCreatedKey, false).apply();
    }

    public static boolean goalsCreated(Context context) {
        SharedPreferences db = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        boolean goalsCreated = db.getBoolean(prefGoalsCreatedKey, false);

        Log.d(TAG, "--- goals created: " + goalsCreated);
        return goalsCreated;
    }

    public static void createGoals(Context context) {
        Log.d(TAG, "*** creating goals ***");

        // exercise
        Goal goal1 = new Goal();
        goal1.name = "\uD83C\uDFCB";
        goal1.freq = 3;
        goal1.active = true;
        goal1.save();
        Log.d(TAG, "goal1: " + goal1);

        // write
        Goal goal2 = new Goal();
        goal2.name = "\uD83D\uDCDD";
        goal2.freq = 7;
        goal2.active = true;
        goal2.save();
        Log.d(TAG, "goal2: " + goal2);

        // sex
        Goal goal3 = new Goal();
        goal3.name = "\uD83C\uDF51";
        goal3.freq = 3;
        goal3.active = true;
        goal3.save();
        Log.d(TAG, "goal3: " + goal3);

        // photos
        Goal goal4 = new Goal();
        goal4.name = "\uD83D\uDCF8";
        goal4.freq = 1;
        goal4.active = true;
        goal4.save();
        Log.d(TAG, "goal4: " + goal4);

        // save that we've created the goals
        SharedPreferences db = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        db.edit().putBoolean(prefGoalsCreatedKey, true).apply();
    }
}