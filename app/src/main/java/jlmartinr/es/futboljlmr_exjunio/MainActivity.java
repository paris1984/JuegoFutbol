package jlmartinr.es.futboljlmr_exjunio;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /*Objetos*/
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    LinearLayout layout4;
    LinearLayout layout5;

    TextView textViewv1;
    TextView textViewv2;
    TextView textViewv3;
    TextView textViewv4;
    TextView textViewv5;

    /*Configuracion juego*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewv2 = findViewById(R.id.textView2);
        textViewv3 = findViewById(R.id.textView3);
        textViewv4 = findViewById(R.id.textView4);
        textViewv5 = findViewById(R.id.textView5);

        layout1 = findViewById(R.id.l1);
        layout1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                //intent.putExtra("dificil",dificil.isChecked());
                startActivityForResult(intent,1);
                //MainActivity.this.startActivity(intent);
                return true;
            }
        });

        layout2 = findViewById(R.id.l2);
        layout2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(intent,2);
                return true;
            }
        });

        layout3 = findViewById(R.id.l3);
        layout3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(intent,3);
                return true;
            }
        });

        layout4 = findViewById(R.id.l4);
        layout4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(intent,4);
                return true;
            }
        });

        layout5 = findViewById(R.id.l5);
        layout5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(intent,5);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int puntuacion = data.getIntExtra("puntuacion",0);
        String nombre="";
        if(requestCode==1){
            textViewv1 = findViewById(R.id.puntuacion1);
            nombre = "Messi";
            String text = textViewv1.getText().toString();
            int i = Integer.parseInt(text) + puntuacion;
            textViewv1.setText(Integer.toString(i));
        }else if(requestCode==2){
            textViewv2 = findViewById(R.id.puntuacion2);
            nombre = "Cristiano";
            String text = textViewv2.getText().toString();
            int i = Integer.parseInt(text) + puntuacion;
            textViewv2.setText(Integer.toString(i));
        }else if(requestCode==3){
            textViewv3 = findViewById(R.id.puntuacion3);
            nombre = "Neymar";
            String text = textViewv3.getText().toString();
            int i = Integer.parseInt(text) + puntuacion;
            textViewv3.setText(Integer.toString(i));
        }else if(requestCode==4){
            textViewv4 = findViewById(R.id.puntuacion4);
            nombre = "Iniesta";
            String text = textViewv4.getText().toString();
            int i = Integer.parseInt(text) + puntuacion;
            textViewv4.setText(Integer.toString(i));
        }else if(requestCode==5){
            textViewv5 = findViewById(R.id.puntuacion5);
            nombre = "Griezman";
            String text = textViewv5.getText().toString();
            int i = Integer.parseInt(text) + puntuacion;
            textViewv5.setText(Integer.toString(i));
        }
        NotificationCompat.Builder constructorNotif = new NotificationCompat.Builder(this);
        constructorNotif.setSmallIcon(R.drawable.soccerball);
        constructorNotif.setContentTitle("Tus puntos");
        constructorNotif.setContentText(nombre + " ha recibido los siguientes puntos:"+puntuacion);

        NotificationManager notificador =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificador.notify(1, constructorNotif.build());
    }
}
