// Thanks to: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView

package org.shreyans.greendot.activities;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.shreyans.greendot.R;
import org.shreyans.greendot.events.GoalCompletedEvent;
import org.shreyans.greendot.events.GoalDeletedEvent;
import org.shreyans.greendot.models.Dot;
import org.shreyans.greendot.models.Goal;
import org.shreyans.greendot.util.DotHelper;
import org.shreyans.greendot.util.CalendarHelper;
import org.shreyans.greendot.util.GoalHelper;

import java.util.ArrayList;

/**
 * Manages the row for displaying and updating a single goal (not editing it though)
 */
public class SingleGoalAdapter extends ArrayAdapter<Goal> {

    private static final String TAG = SingleGoalAdapter.class.getSimpleName();

    public SingleGoalAdapter(Context context, ArrayList<Goal> goals) {
        super(context, 0, goals);
    }

    @Override
    public View getView(int position, View view, final ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.single_goal, parent, false);
        }

        // get the views we'll be updating
        final TextView goalName = (TextView) view.findViewById(R.id.goalName);
        final ImageView[] dotImages = getDotImages(view);
        final ImageView heart = (ImageView) view.findViewById(R.id.heart);

        // show goal name
        final Goal goal = getItem(position);
        goalName.setText(goal.name);
        goalName.setTag(Integer.valueOf(goal.id));
        view.setTag(Integer.valueOf(goal.id));

        // get dots data from the Dot database
        final int currentWeek = CalendarHelper.getCurrentWeekNumber();
        final Dot dot = DotHelper.getDotForGoalAndWeek(goal, currentWeek);

        // update dots and heart to their current state
        setDotImages(dotImages, heart, goal, dot.num);

        // set up a long click handler to delete a goal
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final Goal goal = GoalHelper.getGoalById((Integer)view.getTag());

                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext())
                        .setTitle("Delete " + goal.name + "?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GoalHelper.deleteGoalById(goal.id);
                                EventBus.getDefault().post(new GoalDeletedEvent());
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });

        // set up click handlers for the dots
        for (ImageView d: dotImages) {
            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dot dot = DotHelper.getDotForGoalAndWeek(goal, currentWeek);
                    // clickedDotNum and done are both 1-indexed
                    int clickedDotNum = getClickedDotNum(view);
                    int done;

                    if (clickedDotNum > dot.num) {
                        done = clickedDotNum;
                    } else {
                        done = clickedDotNum - 1;
                    }

                    DotHelper.saveNumDoneForGoalAndWeek(goal, currentWeek, done);
                    setDotImages(dotImages, heart, goal, done);

                    // only publish this event if the goal was completed
                    // via a new tap in the app, not from a previous completion
                    if (done == goal.freq) {
                        int[] heartPosition = new int[2];
                        heart.getLocationOnScreen(heartPosition);
                        heartPosition[0] += heart.getWidth() / 2;
                        heartPosition[1] -= heart.getHeight() / 2;
                        EventBus.getDefault().post(new GoalCompletedEvent(heartPosition));
                    }


                }
            });
        }

        return view;
    }

    /* updates the image resources for dots and heart for a specific goal */
    private void setDotImages(ImageView[] dots, ImageView heart, Goal goal, int done) {
        for (int i = 0; i < dots.length; i++) {

            dots[i].setVisibility(View.VISIBLE);
            if (i > goal.freq - 1) {
                dots[i].setVisibility(View.INVISIBLE);
                continue;
            }

            if (i < done) {
                dots[i].setImageResource(R.drawable.circle_filled);
            } else {
                dots[i].setImageResource(R.drawable.circle_empty);
            }
        }

        if (done == goal.freq) {
            heart.setImageResource(R.drawable.heart_filled);
        } else {
            heart.setImageResource(R.drawable.heart_empty);
        }
    }

    /* returns all the dot ImageViews */
    private ImageView[] getDotImages(View view) {
        LinearLayout dotContainer = (LinearLayout) view.findViewById(R.id.dotContainer);
        final ImageView[] dotImages = new ImageView[dotContainer.getChildCount()];
        for (int i=0; i<dotContainer.getChildCount(); i++) {
            dotImages[i] = (ImageView) dotContainer.getChildAt(i);
        }
        return dotImages;
    }

    /* given a dot ImageView, return it's number (1-indexed), based on the 'android:tag' */
    private int getClickedDotNum(View view) {
        String dotNumStr = view.getTag().toString().replace("dot", "");
        return Integer.parseInt(dotNumStr);
    }
}