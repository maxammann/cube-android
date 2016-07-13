package max.cube.publisher;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import de.greenrobot.dao.query.LazyList;
import max.cube.Cube;
import max.cube.R;
import max.cube.dao.Alarm;
import max.cube.synchronize.SynchronizeRunnable;
import max.cube.tab.alarm.AlarmsFragment;
import max.cube.tab.alarm.AlarmsLazyListAdapter;

class PopulateTask extends AsyncTask<Void, Void, AlarmsLazyListAdapter> {

    private final Cube cube;
    private final AlarmsFragment fragment;

    private final TextView start_message;
    private final ProgressBar progress;
    private final View add_alarm;

    PopulateTask(Cube cube, AlarmsFragment fragment) {
        this.cube = cube;
        this.fragment = fragment;
        this.progress = (ProgressBar) fragment.getRoot().findViewById(R.id.progress);
        this.start_message = (TextView) fragment.getRoot().findViewById(R.id.start_message);
        this.add_alarm = fragment.getRoot().findViewById(R.id.add_alarm);
    }

    @Override
    protected void onPreExecute() {
        progress.setVisibility(View.VISIBLE);
        start_message.setVisibility(View.GONE);
        add_alarm.setVisibility(View.GONE);
    }

    @Override
    protected AlarmsLazyListAdapter doInBackground(Void... params) {
        LazyList<Alarm> query = cube.getAlarmDao().queryBuilder().listLazy();

        new SynchronizeRunnable(query, cube.getRemoteHost(), cube.getRemotePort()).call();

        final AlarmsLazyListAdapter adapter = new AlarmsLazyListAdapter(cube, query, cube, cube.getAlarmDao());
        adapter.isEmpty();

        return adapter;
    }

    @Override
    protected void onPostExecute(AlarmsLazyListAdapter adapter) {
        ListView list = fragment.getList();

        start_message.setVisibility(!adapter.isEmpty() ? View.GONE : View.VISIBLE);

        list.setAdapter(adapter);

        progress.setVisibility(View.GONE);
        add_alarm.setVisibility(View.VISIBLE);
    }
}
