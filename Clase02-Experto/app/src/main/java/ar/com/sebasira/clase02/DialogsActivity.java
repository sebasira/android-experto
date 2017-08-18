package ar.com.sebasira.clase02;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class DialogsActivity extends AppCompatActivity {

    Button btnAlert;
    Button btnUserInput;
    Button btnTime;
    Button btnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);


        btnAlert = (Button) findViewById(R.id.btnAlert);
        btnUserInput = (Button) findViewById(R.id.btnUserInput);
        btnTime = (Button) findViewById(R.id.btnTime);
        btnDate = (Button) findViewById(R.id.btnDate);

        /****************************************************************************************/
        /** ALERT DIALOG
            ------------
         */
        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DialogsActivity.this)
                        .setTitle("¿Seguro que desea formatear el Telefono?")
                        .setMessage("Se perderan todas sus fotos y mensajes y esta operación no podrá deshacerse")
                        .setPositiveButton("Formatear", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                ProgressDialog progressDialog;
                                progressDialog = new ProgressDialog(DialogsActivity.this);
                                progressDialog.setTitle("Formateando");
                                progressDialog.setMessage("Por favor espere...");
                                progressDialog.setIndeterminate(true);
                                progressDialog.setCancelable(true);
                                progressDialog.show();

                            }
                        })
                        .setNegativeButton("Mejor No", null)
                        .show();
            }
        });

        /****************************************************************************************/
        /** USER INPUT DIALOG
            -----------------
         */
        btnUserInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DialogsActivity.this);

                dialogBuilder.setTitle("Formulario de Registro");
                dialogBuilder.setMessage("Ingrese su nombre");


                // Creamos un EditText programaticamente (no desde el layout), y luego se lo añadimos
                // al Builder.
                final EditText userInput = new EditText(DialogsActivity.this);
                userInput.setInputType(InputType.TYPE_CLASS_TEXT);
                dialogBuilder.setView(userInput);

                dialogBuilder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getApplicationContext(), "Usuario " +
                                        userInput.getText().toString() + " registrado",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                dialogBuilder.setNegativeButton("CANCELAR", null);

                dialogBuilder.show();
            }
        });


        /****************************************************************************************/
        /** TIME PICKER
            -----------
         */
        btnTime = (Button) findViewById(R.id.btnTime);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker = new TimePickerDialog(DialogsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker arg0, int hour, int minutes) {
                        Toast.makeText(getApplicationContext(), "Alarma para las " + hour + ":" + minutes,
                                Toast.LENGTH_SHORT).show();
                    }
                }, 17, 05, true);
                timePicker.show();
            }
        });

        /****************************************************************************************/
        /** DATE PICKER
            -----------
         */
        btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(DialogsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                Toast.makeText(getApplicationContext(), "Fecha de Nacimiento " +
                                                dayOfMonth + "/" + monthOfYear + "/" + year,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, 2017, 8, 18);
                datePicker.show();
            }
        });
    }
}
