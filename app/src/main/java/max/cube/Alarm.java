package max.cube;


public class Alarm {

    private String name;
    private long wake;
    private boolean enabled;
    private boolean[] weekdays;

    public Alarm(String name, long wake, boolean enabled, boolean[] weekdays) {
        this.name = name;
        this.wake = wake;
        this.enabled = enabled;
        this.weekdays = weekdays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWake() {
        return wake;
    }

    public void setWake(Integer wake) {
        this.wake = wake;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "name='" + name + '\'' +
                ", wake=" + wake +
                ", enabled=" + enabled +
                '}';
    }

    public boolean isWeekday(int ordinal) {
        return weekdays[ordinal];
    }

    public void setWeekday(int ordinal, boolean enabled) {

        weekdays[ordinal] = enabled;
    }

}
