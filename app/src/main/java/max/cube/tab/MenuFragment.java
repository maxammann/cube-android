package max.cube.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;

import lm.Lm;
import lm.Matrix;
import max.cube.Cube;
import max.cube.R;

public class MenuFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Cube cube = ((Cube) getActivity().getApplication());

        final View fragment = inflater.inflate(R.layout.fragment_menu, container, false);



        fragment.findViewById(R.id.next).setOnClickListener(new MatrixClickListener(cube) {
            @Override
            public void onClick(View v, Matrix matrix) throws IOException {
                matrix.next();
            }
        });

        fragment.findViewById(R.id.previous).setOnClickListener(new MatrixClickListener(cube) {
            @Override
            public void onClick(View v, Matrix matrix) throws IOException {
                matrix.send(matrix.request(Lm.Request.Type.MENU_PREVIOUS));
            }
        });


        fragment.findViewById(R.id.pause).setOnClickListener(new MatrixClickListener(cube) {
            @Override
            public void onClick(View v, Matrix matrix) throws IOException {
                matrix.send(matrix.request(Lm.Request.Type.PAUSE));
            }
        });

        fragment.findViewById(R.id.unpause).setOnClickListener(new MatrixClickListener(cube) {
            @Override
            public void onClick(View v, Matrix matrix) throws IOException {
                matrix.send(matrix.request(Lm.Request.Type.UNPAUSE));
            }
        });

        fragment.findViewById(R.id.screen).setOnClickListener(new MatrixClickListener(cube) {
            @Override
            public void onClick(View v, Matrix matrix) throws IOException {
                matrix.screen(((TextView) fragment.findViewById(R.id.screenText)).getText().toString());
            }
        });
        return fragment;
    }


}
