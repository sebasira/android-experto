package ar.com.sebasira.clase03;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by sebas on 01/09/17.
 */

public class SelectorValor extends RelativeLayout implements OnValueChange{

    /** TAG */
    private static final String TAG = "SelectorValor";

    /** Esta sería la vista base de nuestra vista, la que va a inflar el layout */
    View baseView;

    /** Caja de texto con el VALOR */
    TextView txtValor;

    /** Caja de texto con la ETIQUETA */
    TextView txtLabel;

    /** Boton que decrementa */
    View btnMenos;

    /** Boton que incrementa */
    View btnMas;

    /** Valor Máximo por defecto*/
    private static final int DEFAULT_MAX = 100;

    /** Valor Mínimo por defecto*/
    private static final int DEFAULT_MIN = 0;

    /** Valor Máximo */
    private int maxValor = DEFAULT_MAX;

    /** Valor Mínimo */
    private int minValor = DEFAULT_MIN;

    /** Listener de OnValueChange */
    private OnValueChange onValueChange_listener;

    /** Bandera para auto-incrementar */
    private boolean botonMasPresionado = false;

    /** Bandera para auto-decrementar */
    private boolean botonMenosPresionado = false;

    /** Handler para las tareas en segundo plano con comunicacion con el UI Thread */
    Handler handler = new Handler();

    /** Intervalo de repeticion para auto incrementar/decrementar */
    private final long REPEAT_INTERVAL_MS = 100;

    /* CONSTRUCTOR */
    /* *********** */
    /**
     * En este ejemplo sólo vamos a usar UN constructor que es el que crea la vista desde el XML,
     * inflando un layout. El otro constructor que se podría usar es el que crea la vista desde
     * el códgo (es decir desde otra clase) pero por hoy ese lo vamos a dejar de lado.
     *
     * En el constructor lo que se debe hacer es INICIALIZAR la vista, es decir inflar el layout que
     * la representa y tomar referencia a todas las vistas que la componen.
     * @param context
     * @param attrs
     */
    public SelectorValor(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }



    /* INIT */
    /* **** */
    /**
     * Este es el método que vamos a invocar desde los constructores para crear la vista
     */
    private void init(Context mContext, AttributeSet attrs){
        baseView = inflate(mContext, R.layout.selector_valor, this);
        txtValor = (TextView) baseView.findViewById(R.id.txtValor);
        txtLabel = (TextView) baseView.findViewById(R.id.txtLabel);

        btnMenos = baseView.findViewById(R.id.btnMenos);
        btnMas = baseView.findViewById(R.id.btnMas);

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementarValor();
            }
        });

        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementarValor();
            }
        });


        // Atributos Propios
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.SelectorValor, 0, 0);
        try {
            String label = ta.getString(R.styleable.SelectorValor_label);
            txtLabel.setText(label);
        } finally {
            ta.recycle();
        }


        // Auto incrementar/decrementar
        btnMenos.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                botonMenosPresionado = true;
                handler.post(new AutoDecrementador());
                return false;
            }
        });

        btnMenos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    botonMenosPresionado = false;
                }
                return false;
            }
        });


        btnMas.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                botonMasPresionado = true;
                handler.post(new AutoIncrementador());
                return false;
            }
        });

        btnMas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    botonMasPresionado = false;
                }
                return false;
            }
        });
    }


    /* SETEAR VALOR MAXIMO */
    /* ******************* */
    public void setMax(int newMax){
        this.maxValor = newMax;
    }

    /* SETEAR VALOR MINIMO */
    /* ******************* */
    public void setMin(int newMin){
        this.minValor = newMin;
    }

    /* OBTENER VALOR MAXIMO */
    /* ******************** */
    public int getMax(){
        return this.maxValor;
    }

    /* OBTENER VALOR MINIMO */
    /* ******************** */
    public int getMin(){
        return this.minValor;
    }

    /* OBTENER VALOR */
    /* ************* */
    /**
     * El valor es el contenido de la caja de texto del valor
     */
    public int getValor(){
        String strValor = txtValor.getText().toString();
        int valor = Integer.parseInt(strValor);

        return valor;
    }


    /* SETEAR VALOR */
    /* ************ */
    /**
     * Setear el valor es cambiar el contenido de la caja de texto de valor.
     *
     * Se debe verificar que este contenido dentro del rango del MENOR y el MAYOR
     */
    public void setValor(int newValor){
        if (newValor < minValor){
            Log.w(TAG, "Intento de setear un valor menor la mínimo");
            newValor = minValor;
        }else if(newValor > maxValor){
            Log.w(TAG, "Intento de setear un valor mayor la máximo");
            newValor = maxValor;
        }

        txtValor.setText(Integer.toString(newValor));

        OnValueChange();
    }


    /* OBTENER ETIQUETA */
    /* **************** */
    public String getLabel(){
        return txtLabel.getText().toString();
    }

    /* SETEAR ETIQUETA */
    /* *************** */
    public void setLabel(String newLabel){
        txtLabel.setText(newLabel);
    }


    /* INCREMENTAR VALOR */
    /* ***************** */
    private void incrementarValor(){
        String strValor = txtValor.getText().toString();
        int valor = Integer.parseInt(strValor);

        if (valor < maxValor){
            valor++;
            txtValor.setText(Integer.toString(valor));

            OnValueChange();
        }
    }


    /* DECREMENTAR VALOR */
    /* ***************** */
    private void decrementarValor(){
        String strValor = txtValor.getText().toString();
        int valor = Integer.parseInt(strValor);

        if (valor > minValor){
            valor--;
            txtValor.setText(Integer.toString(valor));

            OnValueChange();
        }
    }

/**************************************************************************************************/
/****** EVENT LISTENER **********/

    /* SETEAR CALLBACK PARA EL LSITENER DE VALUE CHANGE */
	/* ************************************************ */
    public void setOnValueChange_listener(OnValueChange cb){
        this.onValueChange_listener = cb;
    }

    /* CALLBACK PARA CUANDO CAMBIA EL VALOR */
	/* ********************************************* */
    @Override
    public void OnValueChange() {
        if (null != this.onValueChange_listener){
            this.onValueChange_listener.OnValueChange();
        }
    }

/**************************************************************************************************/
/****** RUNNABLES **********/

    /* AUTO INCREMENTADOR */
    /* ****************** */
    private class AutoIncrementador implements Runnable {
        @Override
        public void run() {
            if(botonMasPresionado){
                incrementarValor();
                handler.postDelayed( new AutoIncrementador(), REPEAT_INTERVAL_MS);
            }
        }
    }


    /* AUTO DECREMENTADOR */
    /* ****************** */
    private class AutoDecrementador implements Runnable {
        @Override
        public void run() {
            if(botonMenosPresionado){
                decrementarValor();
                handler.postDelayed( new AutoDecrementador(), REPEAT_INTERVAL_MS);
            }
        }
    }
}
