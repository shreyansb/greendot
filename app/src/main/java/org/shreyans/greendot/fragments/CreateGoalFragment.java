package org.shreyans.greendot.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.shreyans.greendot.R;
import org.shreyans.greendot.events.GoalCreatedEvent;
import org.shreyans.greendot.util.GoalHelper;

public class CreateGoalFragment extends DialogFragment {

    private static final String TAG = CreateGoalFragment.class.getSimpleName();

    public CreateGoalFragment() {
        // empty constructor required, according to:
        // https://github.com/codepath/android_guides/wiki/Using-DialogFragment
    }

    public static CreateGoalFragment newInstance() {
        Log.d(TAG, "in CreateGoalFragment.newInstance");
        CreateGoalFragment f = new CreateGoalFragment();
        return f;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "in CreateGoalFragment.onDestroyView");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "in CreateGoalFragment.onStop");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "in CreateGoalFragment.onCreateView");
        return inflater.inflate(R.layout.fragment_create_goal, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "in CreateGoalFragment.onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        final EditText goalName = (EditText) view.findViewById(R.id.goalName);
        final EditText goalFreq = (EditText) view.findViewById(R.id.goalFreq);

        Button saveGoal = (Button) view.findViewById(R.id.saveGoalButton);
        saveGoal.setText("Save Goal");
        saveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View saveGoalButton) {
                saveGoalButton.setEnabled(false);

                // check that the goal name is only emojis
                // (ideally it's only 1 emoji but it's hard to check that)
                String mGoalName = goalName.getText().toString().trim();
                Log.d(TAG, "goal name: " + mGoalName);
                if (mGoalName.length() < 1) {
                    Toast.makeText(getActivity().getBaseContext(), "Pick an emoji for the goal name", Toast.LENGTH_SHORT).show();
                    saveGoalButton.setEnabled(true);
                    return;
                }

                // get and check goal frequency
                int mGoalFreq = Integer.parseInt(goalFreq.getText().toString());
                Log.d(TAG, "goal freq: " + mGoalFreq);
                if (mGoalFreq < 1 || mGoalFreq > 7) {
                    Toast.makeText(getActivity().getBaseContext(), "Goal frequency must be between 1-7", Toast.LENGTH_SHORT).show();
                    saveGoalButton.setEnabled(true);
                    return;
                }

                GoalHelper.createGoal(mGoalName, mGoalFreq);
                EventBus.getDefault().post(new GoalCreatedEvent());
                saveGoalButton.setEnabled(true);
                dismiss();
            }
        });
    }
}
