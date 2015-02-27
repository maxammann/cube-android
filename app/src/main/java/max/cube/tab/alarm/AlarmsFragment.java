package max.cube.tab.alarm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import max.cube.publisher.AlarmPublisher;
import max.cube.MainActivity;
import max.cube.R;
import max.cube.publisher.DatabaseAlarmPublisher;


public class AlarmsFragment extends Fragment {


    private AlarmPublisher alarmPublisher;
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
                addAlarm.setAlarmPublisher(alarmPublisher);
                addAlarm.show(getActivity().getSupportFragmentManager(), "add_alarm");
            }
        });

        list.setOnItemLongClickListener(new DeleteListener(alarmPublisher, (MainActivity) getActivity()));

        alarmPublisher.populateView();
        return root;
    }

    public ListView getList() {
        return list;
    }

    public ViewGroup getRoot() {
        return root;
    }

    public void setupPublisher(MainActivity mainActivity) {
        alarmPublisher = new DatabaseAlarmPublisher(mainActivity, this);
    }
}
