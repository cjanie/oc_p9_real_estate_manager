package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.entities.Media;
import com.openclassrooms.realestatemanager.ui.LocationActivity;
import com.openclassrooms.realestatemanager.ui.enums.Action;
import com.openclassrooms.realestatemanager.ui.SettingsActivity;
import com.openclassrooms.realestatemanager.ui.fragments.Next;
import com.openclassrooms.realestatemanager.ui.fragments.UseSharedPreferenceFragment;
import com.openclassrooms.realestatemanager.ui.fragments.form.progressbar.ProgressBarHandler;
import com.openclassrooms.realestatemanager.ui.fragments.form.progressbar.StepIconVisitor;
import com.openclassrooms.realestatemanager.ui.utils.WorkStore;
import com.openclassrooms.realestatemanager.ui.viewmodels.FormViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.GeocodingViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.FormViewModelFactory;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.GeocodingViewModelFactory;

import java.util.List;

public abstract class FormFragment extends UseSharedPreferenceFragment implements
        FormMandatoryFragment.HandleFormMandatoryFields,
        FormAddressFragment.HandleAddressFields,
        FormDescriptionDetailsFragment.HandleDescriptionDetailsData,
        FormDescriptionFragment.HandleDescriptionData,
        FormMediaFragment.HandleMediaData,
        FormGeolocationPermissionFragmentForm.HandleGeolocation,
        SaveEstateDataUpdate,
        Next,
        FormData,
        FormDelete {

    private final int LAYOUT_ID = R.layout.fragment_form;

    protected LinearLayout formStepsProgressBar;

    protected FormViewModel formViewModel;

    private GeocodingViewModel geocodingViewModel;

    protected SharedViewModel sharedViewModel;

    private LocationActivity locationActivity;

    private ProgressBarHandler progressBarHandler;

    public FormFragment(LocationActivity locationActivity) {
        this.locationActivity = locationActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);

        this.formStepsProgressBar = root.findViewById(R.id.layout_form_steps_progress_bar);
        this.progressBarHandler = new ProgressBarHandler(this.formStepsProgressBar);
        this.configureProgressBar(inflater);

        FormViewModelFactory formViewModelFactory = ((Launch)this.getActivity().getApplication()).formViewModelFactory();
        this.formViewModel = new ViewModelProvider(this, formViewModelFactory).get(FormViewModel.class);

        GeocodingViewModelFactory geocodingViewModelFactory = ((Launch)this.getActivity().getApplication()).geolocationViewModelFactory();
        this.geocodingViewModel = new ViewModelProvider(this, geocodingViewModelFactory).get(GeocodingViewModel.class);

        this.sharedViewModel = new ViewModelProvider(this.requireActivity()).get(SharedViewModel.class);

        this.showFragment(this.getFragmentForStep(FormStepEnum.MANDATORY));

        this.setAgent();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.setAgent();
    }

    private void configureProgressBar(LayoutInflater inflater) {
        this.formStepsProgressBar.setWeightSum(FormStepEnum.values().length);

        for(int i = 0; i<FormStepEnum.values().length; i++) {
            final int stepIndex = i;
            View view = inflater.inflate(R.layout.layout_form_step_checked_icon, this.formStepsProgressBar, false);
            ImageView imageView = view.findViewById(R.id.form_step_icon);
            imageView.setImageDrawable(this.getContext().getDrawable(new StepIconVisitor().getIconForStep(FormStepEnum.values()[i])));
            imageView.setColorFilter(this.getContext().getColor(R.color.black));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showFragment(getFragmentForStep(FormStepEnum.values()[stepIndex]));
                }
            });
            this.formStepsProgressBar.addView(view);
        }

    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame_layout_form, fragment).commit();
    }

    private void setAgent() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String agentName = sharedPreferences.getString(this.getString(R.string.user_name_preference_key), "");
        if(agentName.isEmpty()) {
            Intent intent = new Intent(this.getContext(), SettingsActivity.class);
            this.startActivity(intent);
        } else {
            this.formViewModel.setAgentName(agentName);
        }
    }

    @Override
    public void setMandatoryFields(Estate estate) {
        // To view model
        // Mandatory properties should not be null
        if(this.isCompleteMandatory(estate)) {
            this.formViewModel.setEstateDataMandatory(
                    estate.getType(), estate.getLocation(), estate.getPrice(), estate.getDevise()
            );
        };

        // To progress bar
        this.handleProgressBarStepMandatory(this.isCompleteMandatory(estate));
    }

    @Override
    public void setEstateAdressData(String streetNumberAndName, String addressComplements, String zipCode, String country) {
        this.formViewModel.setEstateDataAddress(streetNumberAndName, addressComplements, zipCode, country);
        this.handleProgressBarStepAddress(this.isCompleteAddress(streetNumberAndName, addressComplements, zipCode, country));
    }

    @Override
    public void setEstateDescriptionData(String description) {
        this.formViewModel.setEstateDataDescription(description);
        this.handleProgressBarStepDescriptionDetails(this.isCompleteDescription(description));
    }

    @Override
    public void setDescriptionDetailsData(Integer surface, Integer numberOfRooms, Integer numberOfBathrooms, Integer numberOfBedrooms) {
        this.formViewModel.setEstateDataDescriptionDetails(surface, numberOfRooms, numberOfBathrooms, numberOfBedrooms);
        this.handleProgressBarStepDescriptionDetails(this.isCompleteDescriptionDetails(surface));
    }

    @Override
    public void setEstateMediaData(List<Media> mediaList) {
        this.formViewModel.setEstateDataMedia(mediaList);
        this.handleProgressBarStepMedia(this.isCompleteMedia(mediaList));
    }

    @Override
    public void setGeolocation(Double latitude, Double longitude) {
        this.formViewModel.setEstateDataGeolocation(latitude, longitude);
        this.handleProgressBarStepGeolocation(this.isCompleteGeolocation(latitude, longitude));
    }

    private void setEstateGeocodingData(double latitude, double longitude) {
        this.formViewModel.setEstateDataGeolocation(latitude, longitude);
        this.handleProgressBarStepGeocoding(this.isCompleteGeolocation(latitude, longitude));
    }

    protected void saveIdForGeocodingRequestWorker(Long id) {
        WorkStore workStore = ((Launch) this.getContext().getApplicationContext()).workStore();
        workStore.addTask(id.toString());
    }

    @Override
    public Estate getData() {
        return this.formViewModel.getEstateData();
    }

    // To handle the progress bar
    protected boolean isCompleteMandatory(Estate estate) {
        return estate.getType() != null
                && estate.getLocation() != null
                && estate.getPrice() != null
                && estate.getDevise() != null;
    }

    protected boolean isCompleteAddress(Estate estate) {
        return estate.getStreetNumberAndStreetName() != null
                && estate.getZipCode() != null
                && estate.getCountry() != null;
    }

    protected boolean isCompleteAddress(String streetNumberAndName, String addressComplements, String zipCode, String country) {
        return streetNumberAndName != null && zipCode != null && country != null;
    }

    protected boolean isCompleteDescription(Estate estate) {
        return estate.getDescription() != null;
    }

    protected boolean isCompleteDescription(String description) {
        return description != null;
    }

    protected boolean isCompleteDescriptionDetails(Estate estate) {
        return estate.getSurface() != null;
    }

    protected boolean isCompleteDescriptionDetails(Integer surface) {
        return surface != null;
    }


    protected boolean isCompleteMedia(Estate estate) {
        return !estate.getMediaList().isEmpty();
    }

    protected boolean isCompleteMedia(List<Media> mediaList) {
        return !mediaList.isEmpty();
    }

    protected boolean isCompleteGeolocation(Estate estate) {
        return estate.getLatitude() != null && estate.getLongitude() != null;
    }

    protected boolean isCompleteGeolocation(Double latitude, Double longitude) {
        return latitude != null && longitude != null;
    }

   // Progress bar steps
    protected void handleProgressBarStepMandatory(boolean isComplete) {
        this.progressBarHandler.handleStepsProgressBar(FormStepEnum.MANDATORY.ordinal(), isComplete);
    }

    protected void handleProgressBarStepAddress(boolean isComplete) {
        this.progressBarHandler.handleStepsProgressBar(FormStepEnum.ADDRESS.ordinal(), isComplete);
    }

    protected void handleProgressBarStepDescription(boolean isComplete) {
        this.progressBarHandler.handleStepsProgressBar(FormStepEnum.DESCRIPTION.ordinal(), isComplete);
    }

    protected void handleProgressBarStepDescriptionDetails(boolean isComplete) {
        this.progressBarHandler.handleStepsProgressBar(FormStepEnum.DESCRIPTION_DETAILS.ordinal(), isComplete);
    }

    protected void handleProgressBarStepMedia(boolean isComplete) {
        this.progressBarHandler.handleStepsProgressBar(FormStepEnum.MEDIA.ordinal(), isComplete);
    }

    protected void handleProgressBarStepGeolocation(boolean isComplete) {
        this.progressBarHandler.handleStepsProgressBar(FormStepEnum.GEOLOCATION.ordinal(), isComplete);
    }

    protected void handleProgressBarStepGeocoding(boolean isComplete) {
        this.progressBarHandler.handleStepsProgressBar(FormStepEnum.GEOCODING.ordinal(), isComplete);
    }


    @Override
    public void next(FormStep actualFragment) {
        FormStepEnum step = actualFragment.getCurrentStep();
        int stepIndex = step.ordinal();
        int nextStepIndex = stepIndex + 1;
        if(stepIndex < FormStepEnum.values().length - 1) {
            // Show the fragment
            FormStep nextStepFragment = this.getFragmentForStep(FormStepEnum.values()[nextStepIndex]);
            this.showFragment((Fragment) nextStepFragment);


            // Show the progress bar new state

            ImageView stepIcon = this.formStepsProgressBar.getChildAt(stepIndex).findViewById(R.id.form_step_icon);
            stepIcon.setAlpha(0.5f);

        } else {
            this.sharedViewModel.updateAction(Action.HOME);
        }
    }

    private FormSaveSkipFragment getFragmentForStep(FormStepEnum step) {
        return step.accept(new FormStepVisitor<FormSaveSkipFragment>() {

            @Override
            public FormSaveSkipFragment visitMandatory() {
                return new FormMandatoryFragment(
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this
                );
            }

            @Override
            public FormSaveSkipFragment visitMedia() {
                return new FormMediaFragment(
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this
                );
            }

            @Override
            public FormSaveSkipFragment visitGeolocation() {
                return new FormGeolocationPermissionFragmentForm(
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this.locationActivity);
            }

            @Override
            public FormSaveSkipFragment visitDescription() {
                return new FormDescriptionFragment(
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this
                );
            }

            @Override
            public FormSaveSkipFragment visitDescriptionDetails() {
                return new FormDescriptionDetailsFragment(
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this
                );
            }

            @Override
            public FormSaveSkipFragment visitAddress() {
                return new FormAddressFragment(
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this,
                        FormFragment.this
                );
            }

            @Override
            public FormSaveSkipFragment visitGeocoding() {
                return new FormGeocodingFragment(FormFragment.this, FormFragment.this, FormFragment.this);
            }
        });
    }

    @Override
    public boolean delete(FormField formField) {
        return formField.accept(new FormFieldVisitor<Boolean>() {
            @Override
            public Boolean visitDescription() {
                formViewModel.setEstateDataDescription(null);
                handleProgressBarStepDescription(false);
                saveEstateDataUpdate();
                return true;
            }

            @Override
            public Boolean visitSurface() {
                Integer rooms = formViewModel.getEstateData().getNumberOfRooms();
                Integer bathrooms = formViewModel.getEstateData().getNumberOfBathrooms();
                Integer bedrooms = formViewModel.getEstateData().getNumberOfBedrooms();

                formViewModel.setEstateDataDescriptionDetails(null, rooms, bathrooms, bedrooms);
                handleProgressBarStepDescriptionDetails(false);
                saveEstateDataUpdate();
                return true;
            }

            @Override
            public Boolean visitRooms() {
                Integer surface = formViewModel.getEstateData().getSurface();
                Integer bathrooms = formViewModel.getEstateData().getNumberOfBathrooms();
                Integer bedrooms = formViewModel.getEstateData().getNumberOfBedrooms();

                formViewModel.setEstateDataDescriptionDetails(surface,null, bathrooms, bedrooms);
                handleProgressBarStepDescriptionDetails(surface != null ? true : false);
                saveEstateDataUpdate();
                return true;
            }

            @Override
            public Boolean visitBathrooms() {
                Integer surface = formViewModel.getEstateData().getSurface();
                Integer rooms = formViewModel.getEstateData().getNumberOfRooms();
                Integer bedrooms = formViewModel.getEstateData().getNumberOfBedrooms();

                formViewModel.setEstateDataDescriptionDetails(surface, rooms, null, bedrooms);
                handleProgressBarStepDescriptionDetails(surface != null ? true : false);
                saveEstateDataUpdate();
                return true;
            }

            @Override
            public Boolean visitBedrooms() {
                Integer surface = formViewModel.getEstateData().getSurface();
                Integer rooms = formViewModel.getEstateData().getNumberOfRooms();
                Integer bathrooms = formViewModel.getEstateData().getNumberOfBathrooms();

                formViewModel.setEstateDataDescriptionDetails(surface, rooms, bathrooms, null);
                handleProgressBarStepDescriptionDetails(surface != null ? true : false);
                saveEstateDataUpdate();
                return true;
            }
        });
    }

}
