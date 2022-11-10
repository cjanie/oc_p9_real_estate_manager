package com.openclassrooms.realestatemanager.businesslogic.enums;

public enum SearchParameter {

    TYPE {
        @Override
        public <E> E accept(SearchParameterVisitor<E> visitor) {
            return visitor.visitType();
        }
    },

    LOCATION {
        @Override
        public <E> E accept(SearchParameterVisitor<E> visitor) {
            return visitor.visitLocation();
        }
    }, MAX_PRICE_IN_DOLLARS {
        @Override
        public <E> E accept(SearchParameterVisitor<E> visitor) {
            return visitor.visitMaxPriceInDollars();
        }
    };

    public abstract <E> E accept(SearchParameterVisitor<E> visitor);

}
