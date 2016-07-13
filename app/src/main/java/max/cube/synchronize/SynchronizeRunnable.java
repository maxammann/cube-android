package max.cube.synchronize;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;

import de.greenrobot.dao.query.LazyList;
import max.cube.Matrix;
import max.cube.dao.Alarm;

public class SynchronizeRunnable implements Callable<Boolean> {

    private final LazyList<Alarm> alarmQuery;

    private String remoteHost;
    private int remotePort;

    public SynchronizeRunnable(LazyList<Alarm> alarmQuery, String remoteHost, int remotePort) {
        this.alarmQuery = alarmQuery;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    @Override
    public Boolean call() {
        try {
                Matrix matrix = new Matrix(remoteHost, remotePort);
                matrix.setAlarms(alarmQuery);
                return true;
        } catch (IOException e) {
            Log.i("CUBE", "Failed to synchronize!");
        }

        return false;
    }
}
