package model;

public enum RoomType {
    SINGLE {
        @Override
        public String toString() {
            return "Single bed";
        }
    },
    DOUBLE {
        @Override
        public String toString() {
            return "Double bed";
        }
    }
}
