package com.openclassrooms.realestatemanager.ui.utils;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.openclassrooms.realestatemanager.R;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class UseBlurLib {

    public void blur(BlurView blurView, ViewGroup viewGroup) {
        blurView.setupWith(viewGroup)
                .setBlurAlgorithm(new RenderScriptBlur(viewGroup.getContext()))
                .setBlurRadius(2f);
    }
}
