package com.openclassrooms.realestatemanager.ui.enums;

public enum Action {
    ADD {
        @Override
        public <E> E accept(ActionVisitor<E> visitor) {
            return visitor.visitAdd();
        }
    },
    EDIT {
        @Override
        public <E> E accept(ActionVisitor<E> visitor) {
            return visitor.visitEdit();
        }
    },
    SEARCH {
        @Override
        public <E> E accept(ActionVisitor<E> visitor) {
            return visitor.visitSearch();
        }
    },
    HOME {
        @Override
        public <E> E accept(ActionVisitor<E> visitor) {
            return visitor.visitHome();
        }
    },
    DETAILS {
        @Override
        public <E> E accept(ActionVisitor<E> visitor) {
            return visitor.visitDetails();
        }
    }, MAP {
        @Override
        public <E> E accept(ActionVisitor<E> visitor) {
            return visitor.visitMap();
        }
    };

    public abstract <E>E accept(ActionVisitor<E> visitor);
}
