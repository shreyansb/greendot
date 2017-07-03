package org.shreyans.greendot.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.shreyans.greendot.R;
import org.shreyans.greendot.fragments.CreateGoalFragment;
import org.shreyans.greendot.models.Goal;
import org.shreyans.greendot.util.CalendarHelper;
import org.shreyans.greendot.util.GoalHelper;
import org.shreyans.greendot.events.GoalCreatedEvent;
import org.shreyans.greendot.events.GoalDeletedEvent;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static boolean createGoalFragmentVisible = false;
    private SingleGoalAdapter adapter;
    private ListView goalList;

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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
        adapter = new SingleGoalAdapter(this, (ArrayList<Goal>)goals);
        goalList = (ListView) this.findViewById(R.id.goalsListView);
        goalList.setAdapter(adapter);

        // set up the button to create a new goal
        FloatingActionButton newGoalButton = (FloatingActionButton) this.findViewById(R.id.newGoalButton);

        // show the create goal fragment when the FAB is clicked
        newGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show the create goal fragment
                if (!createGoalFragmentVisible) {
                    Log.d(TAG, "??? about to show fragment");
                    FragmentManager fm = getSupportFragmentManager();
                    CreateGoalFragment createGoalFragment = CreateGoalFragment.newInstance();
                    createGoalFragment.show(fm, "fragment_create_goal");
                }
            }
        });
    }

    // event handler after a new goal is created
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGoalCreated(GoalCreatedEvent event) {
        refreshGoals();
    }

    // event handler after a new goal is created
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGoalCreated(GoalDeletedEvent event) {
        refreshGoals();
    }

    public void refreshGoals() {
        List<Goal> goals = GoalHelper.getActiveGoals();
        adapter.clear();
        adapter.addAll(goals);
        adapter.notifyDataSetChanged();
    }

}
