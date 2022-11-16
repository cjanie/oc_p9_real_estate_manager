package com.openclassrooms.realestatemanager.ui.fragments.form;

public enum FormField {
    DESCRIPTION {
        @Override
        public <E> E accept(FormFieldVisitor<E> visitor) {
            return visitor.visitDescription();
        }
    },
    SURFACE {
        @Override
        public <E> E accept(FormFieldVisitor<E> visitor) {
            return visitor.visitSurface();
        }
    },
    ROOMS {
        @Override
        public <E> E accept(FormFieldVisitor<E> visitor) {
            return visitor.visitRooms();
        }
    },
    BATHROOMS {
        @Override
        public <E> E accept(FormFieldVisitor<E> visitor) {
            return visitor.visitBathrooms();
        }
    },
    BEDROOMS {
        @Override
        public <E> E accept(FormFieldVisitor<E> visitor) {
            return visitor.visitBedrooms();
        }
    };

    public abstract <E>E accept(FormFieldVisitor<E> visitor);
}
