package max.cube.synchronize;

import android.os.AsyncTask;
import android.widget.Toast;

import max.cube.Cube;

/**
* Created by max on 27.02.15.
*/
public class SynchronizeTask extends AsyncTask<Void, Void, Boolean> {
    private Cube cube;

    public SynchronizeTask(Cube cube) {
        this.cube = cube;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        new SynchronizeRunnable(cube.getAlarmDao().queryBuilder().listLazy(), cube.getRemoteHost(), cube.getRemotePort()).call();
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Toast.makeText(cube, "Synchronized with " + cube.getRemoteHost(), Toast.LENGTH_LONG).show();
        }
    }
}
