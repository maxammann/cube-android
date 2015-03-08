package max.cube.synchronize;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import de.greenrobot.dao.query.LazyList;
import lm.Lm;
import lm.Matrix;
import max.cube.dao.Alarm;

public class SynchronizeRunnable implements Callable<Boolean> {

    private final LazyList<Alarm> alarmQuery;
    private InetAddress remoteAddress;

    public SynchronizeRunnable(LazyList<Alarm> alarmQuery, InetAddress remoteAddress) {
        this.alarmQuery = alarmQuery;
        this.remoteAddress = remoteAddress;
    }

    @Override
    public Boolean call() {
        try {
            if (remoteAddress != null) {
                Matrix matrix = new Matrix(remoteAddress, 6969);

                Alarm[] alarms = alarmQuery.toArray(new Alarm[alarmQuery.size()]);

                ArrayList<Lm.Alarm> remoteAlarms = new ArrayList<Lm.Alarm>(alarms.length);

                for (Alarm alarm : alarms) {
                    remoteAlarms.add(Lm.Alarm.newBuilder().setName(alarm.getName()).setEnabled(alarm.getEnabled()).setTime(alarm.getWake()).build());
                }

                matrix.setAlarms(remoteAlarms);
                matrix.close();
                return true;
            }
        } catch (IOException e) {
            Log.i("CUBE", "Failed to discover server!");
        }

        return false;
    }
}
