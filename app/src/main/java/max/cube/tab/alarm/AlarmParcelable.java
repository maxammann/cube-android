package max.cube.tab.alarm;

import android.os.Parcel;
import android.os.Parcelable;

import max.cube.dao.Alarm;


class AlarmParcelable implements Parcelable {
    private final Alarm alarm;

    public AlarmParcelable(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(alarm);
    }

    public static final Creator CREATOR = new Creator() {
        public AlarmParcelable createFromParcel(Parcel in) {
            return (AlarmParcelable) in.readValue(Alarm.class.getClassLoader());
        }

        public AlarmParcelable[] newArray(int size) {
            return new AlarmParcelable[size];
        }
    };

    public Alarm getAlarm() {
        return alarm;
    }
}
