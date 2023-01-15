package com.openclassrooms.realestatemanager.ui.fragments.form.progressbar;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.openclassrooms.realestatemanager.R;

public class ProgressBarHandler {

    private LinearLayout progressBar;

    public ProgressBarHandler(LinearLayout progressBar) {
        this.progressBar = progressBar;
    }

    public void handleStepsProgressBar(int stepIndex, boolean isComplete) {
        ImageView stepIcon = this.progressBar.getChildAt(stepIndex).findViewById(R.id.form_step_icon);
        if(isComplete) {
            int colorSuccess = this.progressBar.getContext().getColor(R.color.green);
            stepIcon.setColorFilter(colorSuccess);
        } else {
            int colorDefault = this.progressBar.getContext().getColor(R.color.black);
            stepIcon.setColorFilter(colorDefault);
        }
    }
}
