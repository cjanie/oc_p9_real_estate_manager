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
