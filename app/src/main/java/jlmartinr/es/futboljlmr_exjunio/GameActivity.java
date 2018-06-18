package jlmartinr.es.futboljlmr_exjunio;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    Game j;
    //Boolean dificil;
    public int puntuacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        j=new Game(this);
        hideSystemUI();
        setContentView(j);

    }

    private void hideSystemUI() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            //A partir de kitkat
            j.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            //cuando se presiona volumen, por ejemplo, se cambia la visibilidad, hay que volver
            //a ocultar
            j.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    hideSystemUI();
                }
            });
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            // Pre-Jelly Bean, hay que ocultar la action bar
            getActionBar().hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onDestroy() {
        j.fin();
        super.onDestroy();
    }

}
