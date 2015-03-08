package max.cube.tab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import lm.Matrix;
import max.cube.MainActivity;
import max.cube.R;

public class MenuFragment extends Fragment {

    public MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View menu = inflater.inflate(R.layout.fragment_menu, container, false);
        final View viewById = menu.findViewById(R.id.next);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            if (mainActivity.getRemoteAddress() != null) {
                                Matrix matrix = new Matrix(mainActivity.getRemoteAddress(), 6969);

                                matrix.next();

                                matrix.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();
            }
        });
        return menu;
    }
}
