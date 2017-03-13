package org.shreyans.greendot.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.shreyans.greendot.R;

public class CreateGoalFragment extends Fragment {

    private static final String TAG = CreateGoalFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_create_goal, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        EditText goalName = (EditText) view.findViewById(R.id.goalName);
        EditText goalFreq = (EditText) view.findViewById(R.id.goalFreq);
        Log.d(TAG, "*** create goal fragment created");
    }

}
