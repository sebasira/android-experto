package ar.com.sebasira.clase01_materialdesign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recylerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recylerview = (RecyclerView) findViewById(R.id.recylerview);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recylerview.setLayoutManager(llm);

        List<Peliculas> peliculas = new ArrayList<>();
        peliculas.add(new Peliculas("Los Vengadores", "Superheroes de Marvel"));
        peliculas.add(new Peliculas("Batman", "Heroe de Ciudad Gotica"));
        peliculas.add(new Peliculas("SpiderMan", "El trepa muros"));

        // Creamos la adaptacion para mostrar las Pelicuas
        PelicuasListAdapter pelisAdapter= new PelicuasListAdapter(peliculas);

        // Agregamos las peliculas adaptadas al RecyclerView
        recylerview.setAdapter(pelisAdapter);
    }


/************************************************************************************/
/**
 * ADAPTADOR DE PELICULAS PARA EL RECYCLER VIEW
 **/

    class PelicuasListAdapter extends RecyclerView.Adapter<PelicuasListAdapter.PeliculaViewHolder> {

        List<Peliculas> peliculasList;

        /* CONSTRUCTOR */
        /* *********** */
        PelicuasListAdapter(List<Peliculas> listado) {
            this.peliculasList = listado;
        }


        /* GET ITEM COUNT */
        /* ************** */
        /**
         * Le dice al RecyclerView cuántos elementos tiene el listado a mostrar
         */
        @Override
        public int getItemCount() {
            return peliculasList.size();
        }


        /* ON CREATE VIEW HOLDER */
        /* ********************* */
        /**
         * Se ejecuta cuando se crea la vista del "renglón" y su función es inflar el layout que hayamos
         * definido para el mismo y crear el ViewHolder que es el que se RECICLA para optimizar los
         * recursos del dispositivo
         */
        @Override
        public PeliculaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recylclerview_item_card, viewGroup, false);
            PeliculaViewHolder vh = new PeliculaViewHolder(v);

            return vh;
        }


        /* ON BIND VIEW HOLDER */
        /* ******************* */
        /**
         * Muestra en el ViewHolder, los datos del elemento correspondiente
         */
        @Override
        public void onBindViewHolder(PeliculaViewHolder peliViewHolder, final int i) {
            peliViewHolder.peliTitulo.setText(peliculasList.get(i).titulo);
            peliViewHolder.peliDescripcion.setText(peliculasList.get(i).descripcion);
        }


        /* ON ATACHED TO RECYCLER VIEW */
        /* *************************** */
        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }


        /** PELICULA VIEW HOLDER CLASS */
        /** ************************** */
        /**
         * This class is a representation of each ITEM in the recylcer view. In this case, I'm
         * using cards
         */
        public class PeliculaViewHolder extends RecyclerView.ViewHolder {
            private TextView peliTitulo;
            private TextView peliDescripcion;


            /* CONSTRUCTOR */
            /* *********** */
            PeliculaViewHolder(View itemView) {
                super(itemView);
                peliTitulo = (TextView) itemView.findViewById(R.id.peliTitulo);
                peliDescripcion = (TextView) itemView.findViewById(R.id.peliDescripcion);
            }
        }
    }
}
