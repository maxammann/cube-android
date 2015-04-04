package max.cube;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.net.InetAddress;

import lm.Matrix;
import max.cube.dao.AlarmDao;
import max.cube.dao.DaoMaster;
import max.cube.dao.DaoSession;
import max.cube.discovery.DiscoveryTask;

public class Cube extends Application {
    private SQLiteDatabase db;
    private DaoSession daoSession;


    private InetAddress remoteAddress;
    public InetAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(InetAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    public void onCreate() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "alarms", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        new DiscoveryTask(this).execute();
    }

    @Override
    public void onTerminate() {
        db.close();
        daoSession = null;
    }

    public AlarmDao getAlarmDao() {
        return daoSession.getAlarmDao();
    }

    public Matrix newMatrix() throws IOException {
        if (getRemoteAddress() != null) {
            return new Matrix(getRemoteAddress(), 6969);
        }

        return null;
    }
}
