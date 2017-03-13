package org.shreyans.greendot.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.shreyans.greendot.R;
import org.shreyans.greendot.fragments.CreateGoalFragment;
import org.shreyans.greendot.models.Goal;
import org.shreyans.greendot.util.CalendarHelper;
import org.shreyans.greendot.util.GoalHelper;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);

        /* reset goals
        GoalHelper.deleteAllGoals(this);
        if (!GoalHelper.goalsCreated(this)) {
            GoalHelper.createGoals(this);
        }
        */
    }

    @Override
    public void onResume() {
        super.onResume();

        // Set the date header
        TextView header = (TextView) this.findViewById(R.id.header);
        header.setText(CalendarHelper.getWeekHeaderString());

        // find active Goals
        List<Goal> goals = GoalHelper.getActiveGoals();

        // show the list of goals
        SingleGoalAdapter adapter = new SingleGoalAdapter(this, (ArrayList<Goal>)goals);
        ListView goalList = (ListView) this.findViewById(R.id.goalsListView);
        goalList.setAdapter(adapter);

        // set up the button to create a new goal
        FloatingActionButton newGoalButton = (FloatingActionButton) this.findViewById(R.id.newGoalButton);
        newGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show the create goal fragment
                Log.d(TAG, "??? about to show fragment");
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.createGoalPlaceholder, new CreateGoalFragment());
                ft.commit();
            }
        });
    }
}
