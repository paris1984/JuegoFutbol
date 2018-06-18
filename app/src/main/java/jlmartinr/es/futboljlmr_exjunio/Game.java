package jlmartinr.es.futboljlmr_exjunio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

public class Game extends SurfaceView implements SurfaceHolder.Callback, SurfaceView.OnTouchListener {

    private SurfaceHolder holder;
    private BucleJuego bucle;

    public int Puntos = 0;

    private float VELOCIDAD;

    private String direccion ="ABAJO";

    final float SEGUNDOS_EN_RECORRER_PANTALLA_HORIZONTAL = 5;

    //coordenadas y del fondo actual y del siguiente
    int yImgActual, yImgSiguiente;

    /*índices del array de imagenes para alternar el fondo*/
    int img_actual = 0;

    /*Nave*/
    Bitmap balon;
    float xBalon; //Coordenada X de la nave, variará con gestos de tipo fling
    float yBalon; //Coordenada Y de la nave

    /* Fin de Game */
    private boolean victoria = false, derrota = false;

    boolean hayToque = false;
    private ArrayList<Touch> toques = new ArrayList<Touch>();


    Bitmap bmp;
    private Bitmap fondo;
    int recursos_imagenes[] = {R.drawable.football_pitch};
    Bitmap imagenes[] = new Bitmap[1]; // Arrays de imágenes
    public int AltoPantalla;
    public int AnchoPantalla;
    private GameActivity actividad;

    private static final String TAG = Game.class.getSimpleName();

    public Game(Activity context) {
        super(context);

        actividad = (GameActivity) context;
        holder = getHolder();
        holder.addCallback(this);

        CalculaTamañoPantalla();

        /*Carga la nave*/
        balon = BitmapFactory.decodeResource(getResources(), R.drawable.soccerball);

        /*posición inicial de la Nave */
        xBalon = AnchoPantalla / 2 - balon.getWidth() / 2;//posición inicial de la Nave
        yBalon = AltoPantalla / 2;
        VELOCIDAD = AltoPantalla / SEGUNDOS_EN_RECORRER_PANTALLA_HORIZONTAL / 9;//BucleJuego.MAX_FPS;
        /* Inicialización de coordenadas de fondo (Se ejecuta primero actualizar()*/
        yImgActual = -1;
        yImgSiguiente = -AltoPantalla - 1;
        CargaBackground();

        //listener para onTouch
        setOnTouchListener(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // se crea la superficie, creamos el game loop

        // Para interceptar los eventos de la SurfaceView
        getHolder().addCallback(this);

        // creamos el game loop
        bucle = new BucleJuego(getHolder(), this);

        // Hacer la Vista focusable para que pueda capturar eventos
        setFocusable(true);

        //comenzar el bucle
        bucle.start();

    }

    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */
    private int mMarioHeight=0;

    public void actualiza_fondo() {
        //nueva posición del fondo

    }

    public void actualizar() {

        actualiza_fondo();
        if (!derrota) {
            if(direccion.equals("ABAJO")){
                yBalon = yBalon + VELOCIDAD;
                if(yBalon>=AltoPantalla-balon.getWidth()){
                    derrota = true;
                }
            }else{
                yBalon = yBalon - VELOCIDAD;
                if(yBalon<=0){
                    derrota = true;
                }
            }

        }


    }

    /**
     * Este método dibuja el siguiente paso de la animación correspondiente
     */
    public void renderizar(Canvas canvas) {
        if (canvas != null) {
            //pinceles
            Paint myPaint = new Paint();
            myPaint.setStyle(Paint.Style.FILL);
            myPaint.setColor(Color.WHITE);

            Paint myPaint2 = new Paint();
            myPaint2.setStyle(Paint.Style.FILL);
            myPaint2.setTextSize(50);

            //dibujamos el fondo
            canvas.drawBitmap(imagenes[img_actual], 0, yImgActual, null);


            //Si ha ocurrido un toque en la pantalla "Touch", dibujar un círculo
            if (hayToque) {
                synchronized (this) {
                    for (Touch t : toques) {
                        canvas.drawCircle(t.x, t.y, 100, myPaint);
                        //canvas.drawText(t.index + "", t.x, t.y, myPaint2);
                    }
                }
            }

            if (!derrota)
                canvas.drawBitmap(balon, xBalon, yBalon, null);

            //escribe los puntos
            myPaint.setTextSize(AnchoPantalla / 25); //25 es el número de letras aprox que sale en una línea
            canvas.drawText("PUNTOS " + Puntos, 50, 50, myPaint);

            if (victoria) {
                myPaint.setAlpha(0);
                myPaint.setColor(Color.RED);
                myPaint.setTextSize(AnchoPantalla / 10);
                canvas.drawText("VICTORIA!!", 50, AltoPantalla / 2 - 100, myPaint);
                myPaint.setTextSize(AnchoPantalla / 20);
                //canvas.drawText("Las tropas enemigas han sido derrotadas", 50, AltoPantalla / 2 + 100, myPaint);
            }

            if (derrota) {
                /*myPaint.setAlpha(0);
                myPaint.setColor(Color.RED);
                myPaint.setTextSize(AnchoPantalla / 10);
                canvas.drawText("DERROTA!!", 50, AltoPantalla / 2 - 100, myPaint);
                myPaint.setTextSize(AnchoPantalla / 30);
                canvas.drawText("Se salio el balon, pulsa en la pantalla para salir !!!!", 50, AltoPantalla / 2 + 100, myPaint);*/
                actividad.puntuacion = Puntos;
                Intent i=new Intent();
                i.putExtra("puntuacion",Puntos);
                actividad.setResult(actividad.RESULT_OK,i);
                actividad.finish();
                bucle.fin();
            }

        }
    }

    public void CargaBackground() {
        //cargamos todos los fondos en un array
        for (int i = 0; i < 1; i++) {
            fondo = BitmapFactory.decodeResource(getResources(), recursos_imagenes[i]);
            if (imagenes[i] == null)
                imagenes[i] = fondo.createScaledBitmap(fondo, AnchoPantalla, AltoPantalla, true);
            fondo.recycle();
        }
    }

    public void CalculaTamañoPantalla() {
        if (Build.VERSION.SDK_INT > 13) {
            Display display = actividad.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            AnchoPantalla = size.x;
            AltoPantalla = size.y;
        } else {
            Display display = actividad.getWindowManager().getDefaultDisplay();
            AnchoPantalla = display.getWidth();  // deprecated
            AltoPantalla = display.getHeight();  // deprecated
        }
        Log.i(Game.class.getSimpleName(), "alto:" + AltoPantalla + "," + "ancho:" + AnchoPantalla);
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Juego destruido!");
        // cerrar el thread y esperar que acabe
        boolean retry = true;
        while (retry) {
            try {
                bucle.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(derrota){
            actividad.puntuacion = Puntos;
            Intent i=new Intent();
            i.putExtra("puntuacion",Puntos);
            actividad.setResult(actividad.RESULT_OK,i);
            actividad.finish();
            bucle.fin();

        }else {
            int index;
            int x, y;

            // Obtener el pointer asociado con la acción
            index = MotionEventCompat.getActionIndex(event);

            x = (int) MotionEventCompat.getX(event, index);
            y = (int) MotionEventCompat.getY(event, index);

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    hayToque = true;

                    synchronized (this) {
                        toques.add(index, new Touch(index, x, y));
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    synchronized (this) {
                        toques.remove(index);
                    }


                case MotionEvent.ACTION_UP:
                    synchronized (this) {
                        toques.clear();
                    }
                    hayToque = false;
                    Puntos++;
                    direccion = direccion.equals("ABAJO") ? "ARRIBA" : "ABAJO";
                    //se comprueba si se ha soltado el botón
            }

        }
        return true;
    }

    public void fin() {


        for (int i = 0; i < 1; i++)
            imagenes[i].recycle();
        balon.recycle();
    }



}
