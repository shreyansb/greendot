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

    public static Goal getGoalById(int id) {
        Goal goal = SQLite.select()
                .from(Goal.class)
                .where(Goal_Table.id.is(id))
                .querySingle();
        return goal;
    }

    public static void deleteGoalById(int id) {
        SQLite.delete()
                .from(Goal.class)
                .where(Goal_Table.id.is(id))
                .execute();
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
        return db.getBoolean(prefGoalsCreatedKey, false);
    }

    public static Goal createGoal(String name, int freq) {
        Goal goal = new Goal();
        goal.name = name;
        goal.freq = freq;
        goal.active = true;
        goal.save();
        Log.d(TAG, "created goal: " + goal.name + " | id: " + goal.id);
        return goal;
    }

    public static void createGoals(Context context) {
        Log.d(TAG, "*** creating goals ***");

        createGoal("\uD83C\uDFCB", 3);  // exercise
        createGoal("\uD83D\uDCDD", 5);  // write
        createGoal("\uD83C\uDF51", 3);  // sex
        createGoal("\uD83D\uDCF8", 1);  // photos
        createGoal("\uD83D\uDCA8", 7);  // breathing exercises
        createGoal("\uD83D\uDEAD", 5);  // no smoking

        // save that we've created the goals
        SharedPreferences db = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        db.edit().putBoolean(prefGoalsCreatedKey, true).apply();
    }
}