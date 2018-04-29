package max.cube;


public enum Weekday {
    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    private int ordinal = 0;

    Weekday(int ord) {
        this.ordinal = ord;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public static Weekday byOrdinal(int ord) {
        for (Weekday m : Weekday.values()) {
            if (m.ordinal == ord) {
                return m;
            }
        }
        return null;
    }
}
