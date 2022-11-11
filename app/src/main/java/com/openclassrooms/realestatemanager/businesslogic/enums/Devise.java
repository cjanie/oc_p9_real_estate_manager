package com.openclassrooms.realestatemanager.businesslogic.enums;

public enum Devise {
    EURO {
        @Override
        public <E> E accept(DeviseVisitor<E> visitor) {
            return visitor.visitEuro();
        }
    }, DOLLAR {
        @Override
        public <E> E accept(DeviseVisitor<E> visitor) {
            return visitor.visitDollar();
        }
    };

    public abstract <E> E accept(DeviseVisitor<E> visitor);
}
