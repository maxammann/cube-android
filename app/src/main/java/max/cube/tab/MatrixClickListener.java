package max.cube.tab;

import android.os.AsyncTask;
import android.view.View;

import java.io.IOException;

import lm.Matrix;
import max.cube.Cube;


public abstract class MatrixClickListener implements View.OnClickListener{

    private final Cube cube;

    public MatrixClickListener(Cube cube) {
        this.cube = cube;
    }

    @Override
    public void onClick(final View v) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Matrix matrix = cube.newMatrix();
                    onClick(v, matrix);
                    matrix.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }


    public abstract void onClick(View v, Matrix matrix) throws IOException;
}
