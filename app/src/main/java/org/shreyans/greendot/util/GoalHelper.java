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

        Goal goal1 = new Goal();
        goal1.name = "Exercise";
        goal1.freq = 3;
        goal1.active = true;
        goal1.save();
        Log.d(TAG, "goal1: " + goal1);

        Goal goal2 = new Goal();
        goal2.name = "Write";
        goal2.freq = 5;
        goal2.active = true;
        goal2.save();
        Log.d(TAG, "goal2: " + goal2);

        // save that we've created the goals
        SharedPreferences db = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        db.edit().putBoolean(prefGoalsCreatedKey, true).apply();
    }
}