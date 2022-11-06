package com.openclassrooms.realestatemanager.ui.fragments;

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
    }, ADDRESS {
        @Override
        public <E> E accept(FormStepVisitor<E> visitor) {
            return visitor.visitAddress();
        }
    };

    public abstract <E> E accept(FormStepVisitor<E> visitor);
}
