package max.cube.synchronize;

import android.os.AsyncTask;
import android.widget.Toast;

import max.cube.MainActivity;

/**
* Created by max on 27.02.15.
*/
public class SynchronizeTask extends AsyncTask<Void, Void, Boolean> {
    private MainActivity mainActivity;

    public SynchronizeTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        new SynchronizeRunnable(mainActivity.getAlarmDao().queryBuilder().listLazy(), mainActivity.getRemoteAddress());
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Toast.makeText(mainActivity, "Synchronized with " + mainActivity.getRemoteAddress().getHostAddress(), Toast.LENGTH_LONG).show();
        }
    }
}
