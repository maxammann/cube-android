package max.cube.tab.alarm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import de.greenrobot.dao.query.LazyList;
import max.cube.AlarmPopulator;
import max.cube.R;
import max.cube.dao.Alarm;
import max.cube.dao.AlarmDao;


public class AlarmsFragment extends Fragment implements AlarmPopulator {

    public AlarmDao alarmDao;

    private ListView list;
    private ViewGroup root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_alarms, container, false);
        list = (ListView) root.findViewById(R.id.alarm_list);


        root.findViewById(R.id.add_alarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAlarmDialogFragment addAlarm = new AddAlarmDialogFragment();
                addAlarm.alarms = AlarmsFragment.this;
                addAlarm.show(getActivity().getSupportFragmentManager(), "add_alarm");
            }
        });

        list.setOnItemLongClickListener(new DeleteListener(alarmDao, this, getActivity()));

        populateView();
        return root;
    }

    @Override
    public void populateView() {
        new PopulateTask().execute();
    }

    @Override
    public void push(final Alarm alarm) {
        alarm.setName(alarm.getName());
        alarm.setWake(alarm.getWake());

        new AsyncTask<Alarm, Void, Void>() {

            @Override
            protected Void doInBackground(Alarm... params) {
                alarmDao.insert(params[0]);
                return null;
            }
        }.execute(alarm);
    }

    private class PopulateTask extends AsyncTask<Void, Void, AlarmsLazyListAdapter> {
        @Override
        protected AlarmsLazyListAdapter doInBackground(Void... params) {
            if (alarmDao == null) {
                return null;
            }

            LazyList<Alarm> query = alarmDao.queryBuilder().listLazy();
            final AlarmsLazyListAdapter adapter = new AlarmsLazyListAdapter(getActivity(), query, alarmDao);
            adapter.isEmpty();

            return adapter;
        }

        @Override
        protected void onPostExecute(AlarmsLazyListAdapter adapter) {
            if (adapter == null) {
                return;
            }

            View startMessage = root.findViewById(R.id.start_message);

            startMessage.setVisibility(!adapter.isEmpty() ? View.GONE : View.VISIBLE);

            list.setAdapter(adapter);
        }
    }
}
