package com.openclassrooms.realestatemanager.ui.fragments.form;

public enum FormStepEnum {
    MANDATORY {
        @Override
        public <E> E accept(FormStepVisitor<E> visitor) {
            return visitor.visitMandatory();
        }
    },
    MEDIA {
        @Override
        public <E> E accept(FormStepVisitor<E> visitor) {
            return visitor.visitMedia();
        }
    },
    GEOLOCATION {
        @Override
        public <E> E accept(FormStepVisitor<E> visitor) {
            return visitor.visitGeolocation();
        }
    },
    DESCRIPTION {
        @Override
        public <E> E accept(FormStepVisitor<E> visitor) {
            return visitor.visitDescription();
        }
    },
    DESCRIPTION_DETAILS {
        @Override
        public <E> E accept(FormStepVisitor<E> visitor) {
            return visitor.visitDescriptionDetails();
        }
    }
    , ADDRESS {
        @Override
        public <E> E accept(FormStepVisitor<E> visitor) {
            return visitor.visitAddress();
        }
    }
    , GEOCODING {
        @Override
        public <E> E accept(FormStepVisitor<E> visitor) {
            return visitor.visitGeocoding();
        }
    };

    public abstract <E> E accept(FormStepVisitor<E> visitor);
}
