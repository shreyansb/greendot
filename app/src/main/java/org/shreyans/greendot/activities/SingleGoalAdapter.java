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
import org.shreyans.greendot.util.WeekCalendar;

import java.util.ArrayList;

public class SingleGoalAdapter extends ArrayAdapter<Goal> {

    private static final String TAG = SingleGoalAdapter.class.getSimpleName();
    private Context context;

    public SingleGoalAdapter(Context context, ArrayList<Goal> goals) {
        super(context, 0, goals);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.single_goal, parent, false);
        }

        // show goal name
        final Goal goal = getItem(position);
        final TextView goalName = (TextView) view.findViewById(R.id.goalName);
        goalName.setText(goal.name);

        // get dots data from the Dot database
        final int currentWeek = WeekCalendar.getCurrentWeek();
        final Dot dot = DotHelper.getDotForGoalAndWeek(goal, currentWeek);

        // manage individual style and click handler
        final ImageView[] dotImages = getDotImages(view);
        setDotImages(dotImages, goal, dot.num);

        for (ImageView d: dotImages) {
            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dot dot = DotHelper.getDotForGoalAndWeek(goal, currentWeek);
                    int dotIndex = getDotIndex(view);
                    int done;

                    if (dotIndex > dot.num) {
                        Log.d(TAG, "done " + dotIndex);
                        done = dotIndex;
                    } else {
                        Log.d(TAG, "undoing, now done " + (dotIndex - 1));
                        done = dotIndex - 1;
                    }

                    setDotImages(dotImages, goal, done);
                    DotHelper.setDoneForGoalAndWeek(goal, currentWeek, done);

                    if (done == goal.freq - 1) {
                        Log.d(TAG, "<3");
                    }
                }
            });
        }

        return view;
    }

    private void setDotImages(ImageView[] dots, Goal goal, int done) {
        for (int i = 0; i < dots.length; i++) {
            if (i >= goal.freq) {
                dots[i].setVisibility(View.INVISIBLE);
                continue;
            }

            if (i <= done) {
                dots[i].setImageResource(R.drawable.circle_filled);
            } else {
                dots[i].setImageResource(R.drawable.circle_empty);
            }
        }
    }

    public ImageView[] getDotImages(View view) {
        LinearLayout dotContainer = (LinearLayout) view.findViewById(R.id.dotContainer);
        final ImageView[] dotImages = new ImageView[dotContainer.getChildCount()];
        for (int i=0; i<dotContainer.getChildCount(); i++) {
            dotImages[i] = (ImageView) dotContainer.getChildAt(i);
        }
        return dotImages;
    }

    // given a dot ImageView, return it's index, based on the 'android:tag'
    public int getDotIndex(View view) {
        String dotNumStr = view.getTag().toString().replace("dot", "");
        int dotNum = Integer.parseInt(dotNumStr) - 1;
        return dotNum;
    }
}