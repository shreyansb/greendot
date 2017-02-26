package org.shreyans.greendot.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.shreyans.greendot.R;
import org.shreyans.greendot.models.Goal;
import org.shreyans.greendot.util.GoalHelper;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///* reset goals
        GoalHelper.deleteAllGoals(this);
        if (!GoalHelper.goalsCreated(this)) {
            GoalHelper.createGoals(this);
        }
        //*/

        // Set the date header
        //TextView header = (TextView) this.findViewById(R.id.header);

        // find active Goals
        List<Goal> goals = GoalHelper.getActiveGoals();

        // show the list of goals
        SingleGoalAdapter adapter = new SingleGoalAdapter(this, (ArrayList)goals);
        ListView goalList = (ListView) this.findViewById(R.id.goalsListView);
        goalList.setAdapter(adapter);
    }



}
