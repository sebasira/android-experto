    package ar.com.sebasira.clase01_materialdesign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

    public class MainActivity extends AppCompatActivity {


    /* ON CREATE */
    /* ********* */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
            Tomamos la referencia a la TOOLBAR para setearle sus atributos, al igual
            que lo hacíamos con la ActionBar
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Seteamos el título y el subtitulo. Esto lo podemos customizar en cada activity o fragment
        getSupportActionBar().setTitle("Titulo");
        getSupportActionBar().setSubtitle("Subtitulo");

        // Para tener la flecha de navegación en la Toolbar. Ese botón, por defecto cumple la misma
        // función que la flecha hacia atras (si hubiera otros fragment o activitys a las cuales
        // navegar hacia atras.
        // Como se trata de la actividad RAIZ, si queremos darle un funcionamiento, lo podemos hacer
        // como si fuese un item del options-menu/action-menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        Button btnRV = (Button) findViewById(R.id.btnRV);
        btnRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                startActivity(i);
            }
        });
    }



    /* ON OPTIONS ITEM SELECTED */
    /* ************************ */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Capturamos la accion del boton de HOME
        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
