package max.cube;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import max.cube.tab.DummySectionFragment;
import max.cube.tab.alarm.AlarmsFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private final MainActivity mainActivity;

    public TabsPagerAdapter(FragmentManager fm, MainActivity mainActivity) {
        super(fm);
        this.mainActivity = mainActivity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AlarmsFragment alarmsFragment = new AlarmsFragment();
                alarmsFragment.setupPublisher(mainActivity);
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
