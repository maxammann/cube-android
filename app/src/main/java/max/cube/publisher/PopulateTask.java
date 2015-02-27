package max.cube.publisher;

import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import de.greenrobot.dao.query.LazyList;
import max.cube.MainActivity;
import max.cube.R;
import max.cube.dao.Alarm;
import max.cube.synchronize.SynchronizeRunnable;
import max.cube.tab.alarm.AlarmsFragment;
import max.cube.tab.alarm.AlarmsLazyListAdapter;

/**
 * Created by max on 27.02.15.
 */
class PopulateTask extends AsyncTask<Void, Void, AlarmsLazyListAdapter> {

    private final MainActivity activity;
    private final AlarmsFragment fragment;
    private ProgressBar progress;

    PopulateTask(MainActivity activity, AlarmsFragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
        this.progress = (ProgressBar) fragment.getRoot().findViewById(R.id.progress);
    }

    @Override
    protected void onPreExecute() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected AlarmsLazyListAdapter doInBackground(Void... params) {
        LazyList<Alarm> query = activity.getAlarmDao().queryBuilder().listLazy();

        new SynchronizeRunnable(query, activity.getRemoteAddress()).call();

        final AlarmsLazyListAdapter adapter = new AlarmsLazyListAdapter(activity, query, activity.getAlarmDao());
        adapter.isEmpty();

        return adapter;
    }

    @Override
    protected void onPostExecute(AlarmsLazyListAdapter adapter) {
        ViewGroup root = fragment.getRoot();
        ListView list = fragment.getList();

        View startMessage = root.findViewById(R.id.start_message);

        startMessage.setVisibility(!adapter.isEmpty() ? View.GONE : View.VISIBLE);

        list.setAdapter(adapter);

        progress.setVisibility(View.GONE);
    }
}
