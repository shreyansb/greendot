// Thanks to: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView

package org.shreyans.greendot.activities;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.shreyans.greendot.R;
import org.shreyans.greendot.models.Dot;
import org.shreyans.greendot.models.Goal;
import org.shreyans.greendot.util.DotHelper;
import org.shreyans.greendot.util.CalendarHelper;

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
    public View getView(int position, View view, ViewGroup parent) {
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

        // get dots data from the Dot database
        final int currentWeek = CalendarHelper.getCurrentWeekNumber();
        final Dot dot = DotHelper.getDotForGoalAndWeek(goal, currentWeek);

        // update dots and heart to their current state
        setDotImages(dotImages, heart, goal, dot.num);

        // set up click handlers
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

                    setDotImages(dotImages, heart, goal, done);
                    DotHelper.setDoneForGoalAndWeek(goal, currentWeek, done);
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