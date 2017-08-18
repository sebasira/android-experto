package ar.com.sebasira.clase02;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RadioButtonActivity extends AppCompatActivity {

    RadioGroup groupDesayuno;
    Button btnPedir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button);

        groupDesayuno = (RadioGroup) findViewById(R.id.groupDesayuno);
        groupDesayuno.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.opt_cafe){
                    Log.e("RadioGroup", "Seleccio Cafe");
                }

                if (checkedId == R.id.opt_cortado){
                    Log.e("RadioGroup", "Seleccio Cortado");
                }

                if (checkedId == R.id.opt_mate){
                    Log.e("RadioGroup", "Seleccio Mate");
                }
            }
        });


        btnPedir = (Button) findViewById(R.id.btnPedir);
        btnPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idSelected = groupDesayuno.getCheckedRadioButtonId();

                if (idSelected == R.id.opt_cafe){
                    Toast.makeText(getApplicationContext(), "Su café está en marcha", Toast.LENGTH_SHORT).show();
                }

                if (idSelected == R.id.opt_cortado){
                    Toast.makeText(getApplicationContext(), "Su cortado está en camino", Toast.LENGTH_SHORT).show();
                }

                if (idSelected == R.id.opt_mate){
                    Toast.makeText(getApplicationContext(), "Calentando el agua para el mate", Toast.LENGTH_SHORT).show();
                }

                if (idSelected == -1){
                    Toast.makeText(getApplicationContext(), "Seleccione una infusión antes de Ordenarla", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
