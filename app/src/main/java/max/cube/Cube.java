package max.cube;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import max.cube.dao.AlarmDao;
import max.cube.dao.DaoMaster;
import max.cube.dao.DaoSession;

public class Cube extends Application {
    private SQLiteDatabase db;
    private DaoSession daoSession;


    private String remoteHost = "max-clock";
    private int remotePort = 8080;

    public String getRemoteHost() {
        return remoteHost;
    }

    public int getRemotePort() {
        return remotePort;
    }

    @Override
    public void onCreate() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "alarms", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
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
        if (getRemoteHost() != null) {
            return new Matrix(remoteHost, remotePort);
        }

        return null;
    }
}
