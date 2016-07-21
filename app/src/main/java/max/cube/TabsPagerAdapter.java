package max.cube;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import max.cube.alarm.AlarmsFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AlarmsFragment();
//            case 1:
//                return new MenuFragment();
//            default:
//                Fragment fragment = new DummySectionFragment();
//                Bundle args = new Bundle();
//                args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//                fragment.setArguments(args);
//                return fragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Alarms";
            case 1:
                return "Menu";
            default:
                return "Dummy";
        }
    }
}
