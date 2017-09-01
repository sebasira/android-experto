package ar.com.sebasira.clase03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SelectorValor selVal_cervezas = (SelectorValor) findViewById(R.id.selVal_cervezas);
        selVal_cervezas.setOnValueChange_listener(new OnValueChange() {
            @Override
            public void OnValueChange() {
                Toast.makeText(getApplicationContext(), "Valor cambiado", Toast.LENGTH_SHORT).show();
            }
        });


        Button customToast = (Button) findViewById(R.id.customToast);
        customToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                ViewGroup customLayout = (ViewGroup) findViewById(R.id.custom_toast_layout);
                View toastLayout = inflater.inflate(R.layout.custom_toast, customLayout);

                TextView txtToastMsj = (TextView) toastLayout.findViewById(R.id.txtToastMsj);
                txtToastMsj.setText("Valor Cambiado");

                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(toastLayout);
                toast.show();
            }
        });
    }
}
