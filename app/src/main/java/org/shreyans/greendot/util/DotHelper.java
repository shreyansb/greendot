package org.shreyans.greendot.util;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.shreyans.greendot.models.Dot;
import org.shreyans.greendot.models.Dot_Table;
import org.shreyans.greendot.models.Goal;

public class DotHelper {

    private static final String TAG = DotHelper.class.getSimpleName();

    public static Dot getDotForGoalAndWeek(Goal goal, int week) {
        Dot dot = SQLite.select()
                .from(Dot.class)
                .where(Dot_Table.goal_id.is(goal.id))
                .and(Dot_Table.week.is(week))
                .querySingle();

        // pass a blank Dot back if one wasn't found in the db
        if (dot == null) {
            dot = new Dot();
            dot.goal = goal;
            dot.week = week;
            dot.save();
        }

        return dot;
    }

    public static void setDoneForGoalAndWeek(Goal goal, int week, int done) {
        Dot dot = getDotForGoalAndWeek(goal, week);
        dot.num = done;
        dot.save();
    }
}