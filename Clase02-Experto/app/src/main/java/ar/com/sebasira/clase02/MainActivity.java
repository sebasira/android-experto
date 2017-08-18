package ar.com.sebasira.clase02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edt_pass;
    CheckBox chk_ver;

    EditText edt_next;
    EditText edt_done;
    EditText edt_go;
    EditText edt_search;
    EditText edt_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /****************************************************************************************/
        /** INPUT TYPE
            ----------
         */
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        chk_ver = (CheckBox) findViewById(R.id.chk_ver);

        // Seteamos un Listener en el cambio de estado, para cambiar la propiedad del EditText
        chk_ver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    edt_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    edt_pass.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        /****************************************************************************************/
        /** IME OPTIONS
            -----------
         */
        edt_next = (EditText) findViewById(R.id.edt_next);
        edt_done = (EditText) findViewById(R.id.edt_done);
        edt_go = (EditText) findViewById(R.id.edt_go);
        edt_search = (EditText) findViewById(R.id.edt_search);
        edt_send = (EditText) findViewById(R.id.edt_send);


        edt_next.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean procesado = false;

                if (actionId == EditorInfo.IME_ACTION_NEXT){
                    Toast.makeText(getApplicationContext(), "Presionaste Next", Toast.LENGTH_SHORT).show();
                    procesado = true;
                }

                return procesado;
            }
        });

        edt_next.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean procesado = false;

                if (actionId == EditorInfo.IME_ACTION_NEXT){
                    Toast.makeText(getApplicationContext(), "Presionaste NEXT", Toast.LENGTH_SHORT).show();

                    // Ir a BARS ACTIVITY
                    Intent i = new Intent(getApplicationContext(), BarsActivity.class);
                    startActivity(i);

                    procesado = true;
                }

                // Mostrar le sentido de el boolean que retorna
                return procesado;
            }
        });

        edt_go.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean procesado = false;

                if (actionId == EditorInfo.IME_ACTION_GO){
                    Toast.makeText(getApplicationContext(), "Presionaste GO", Toast.LENGTH_SHORT).show();

                    // Ir a RADIO BUTTON ACTIVITY
                    Intent i = new Intent(getApplicationContext(), RadioButtonActivity.class);
                    startActivity(i);

                    procesado = true;
                }

                return procesado;
            }
        });

        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean procesado = false;

                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    Toast.makeText(getApplicationContext(), "Presionaste SEARCH", Toast.LENGTH_SHORT).show();
                    procesado = true;
                }

                return procesado;
            }
        });

        edt_send.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean procesado = false;

                if (actionId == EditorInfo.IME_ACTION_SEND){
                    Toast.makeText(getApplicationContext(), "Presionaste SEND", Toast.LENGTH_SHORT).show();

                    // Ir a DIALOGS ACTIVITY
                    Intent i = new Intent(getApplicationContext(), DialogsActivity.class);
                    startActivity(i);

                    procesado = true;
                }

                return procesado;
            }
        });


        /****************************************************************************************/
        /** AUTO COMPLETAR
            ---------------
         */
        AutoCompleteTextView autocomplete_txt = (AutoCompleteTextView) findViewById(R.id.autocomplete_txt);

        // Listado de opciones de auto-completar
        String[] contactos = {"Jose", "Martin", "Maria", "Jorge", "Mariana", "Andres", "Antonia"};

        // Creamos el adaptador, adaptando el listado de strings en una lista con un único text view
        // y finalmente seteamos ese adaptador en la caja de texto con auto-completar
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactos);
        autocomplete_txt.setAdapter(adapter);
    }
}
