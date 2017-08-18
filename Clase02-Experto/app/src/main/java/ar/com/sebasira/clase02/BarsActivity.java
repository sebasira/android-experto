package ar.com.sebasira.clase02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

public class BarsActivity extends AppCompatActivity {

    RatingBar rating;
    Button btnSetRating;
    SeekBar seekBar;
    Button btnSetSeek_1;
    Button btnSetSeek_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bars);

        /****************************************************************************************/
        /** RATING BAR
            ----------
         */
        rating = (RatingBar) findViewById(R.id.rating);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    Toast.makeText(getApplicationContext(), "Rating cambiado por el USUARIO! Valor: " + rating,
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Rating cambiado PROGRAMATICAMENTE! Valor: " + rating,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnSetRating = (Button) findViewById(R.id.btnSetRating);
        btnSetRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating.setRating((float)3.5);
            }
        });


        /****************************************************************************************/
        /** SEEKBAR
            -------
         */
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Se deben implementar obligatoriamente, pero no necesariamente tienen que hacer algo
                Log.e("Seekbar", "Cambio en Progreso: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Se deben implementar obligatoriamente, pero no necesariamente tienen que hacer algo
                Log.e("Seekbar", "Inicio Tracking");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Se deben implementar obligatoriamente, pero no necesariamente tienen que hacer algo
                Log.e("Seekbar", "Fin Tracking");

                Toast.makeText(getApplicationContext(), "Valor Cambiado: " + seekBar.getProgress(),
                        Toast.LENGTH_SHORT).show();

            }
        });

        btnSetSeek_1 = (Button) findViewById(R.id.btnSetSeek_1);
        btnSetSeek_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(50);
            }
        });

        btnSetSeek_2 = (Button) findViewById(R.id.btnSetSeek_2);
        btnSetSeek_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setSecondaryProgress(80);
            }
        });

    }
}
