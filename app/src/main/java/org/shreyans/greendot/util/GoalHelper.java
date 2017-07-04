package org.shreyans.greendot.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.shreyans.greendot.models.Dot;
import org.shreyans.greendot.models.Goal;
import org.shreyans.greendot.models.Goal_Table;

import java.util.Calendar;
import java.util.List;

public class GoalHelper {

    private static final String TAG = GoalHelper.class.getSimpleName();

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

    public static Goal createGoal(String name, int freq) {
        Goal goal = new Goal();
        goal.name = name;
        goal.freq = freq;
        goal.active = true;
        goal.save();
        return goal;
    }

    public static Boolean allGoalsCompleted() {
        List<Goal> goals = getActiveGoals();
        int week = CalendarHelper.getCurrentWeekNumber();
        for (Goal goal : goals) {
            Dot dot = DotHelper.getDotForGoalAndWeek(goal, week);
            if (dot.num != goal.freq) {
                return false;
            }
        }
        return true;
    }
}