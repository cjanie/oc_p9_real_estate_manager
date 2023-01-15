package com.openclassrooms.realestatemanager.ui.fragments.form.progressbar;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.fragments.form.FormStepEnum;
import com.openclassrooms.realestatemanager.ui.fragments.form.FormStepVisitor;

public class StepIconVisitor {

    public int getIconForStep(FormStepEnum step) {
        return step.accept(new FormStepVisitor<Integer>() {
            @Override
            public Integer visitMandatory() {
                return R.drawable.ic_baseline_lightbulb_24;
            }

            @Override
            public Integer visitMedia() {
                return R.drawable.ic_baseline_add_a_photo_24;
            }

            @Override
            public Integer visitGeolocation() {
                return R.drawable.ic_baseline_my_location_24;
            }

            @Override
            public Integer visitDescription() {
                return R.drawable.ic_baseline_description_24;
            }

            @Override
            public Integer visitDescriptionDetails() {
                return R.drawable.ic_baseline_square_foot_24;
            }

            @Override
            public Integer visitAddress() {
                return R.drawable.ic_baseline_location_city_24;
            }

            @Override
            public Integer visitGeocoding() {
                return R.drawable.ic_baseline_map_24;
            }

        });
    }

}
