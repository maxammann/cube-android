package max.cube;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import max.cube.dao.AlarmDao;
import max.cube.tab.alarm.AlarmsFragment;
import max.cube.tab.DummySectionFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private final AlarmDao alarmDao;

    public TabsPagerAdapter(FragmentManager fm, AlarmDao alarmDao) {
        super(fm);
        this.alarmDao = alarmDao;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AlarmsFragment alarmsFragment = new AlarmsFragment();
                alarmsFragment.alarmDao = alarmDao;

                return alarmsFragment;

            default:
                Fragment fragment = new DummySectionFragment();
                Bundle args = new Bundle();
                args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
                fragment.setArguments(args);
                return fragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Alarms";
            default:
                return "Dummy";
        }
    }
}
