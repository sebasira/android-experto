package ar.com.sebasira.clase04_map;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;

    private GoogleApiClient googleApiClient;

    private Marker myMarker;

    private Location myLocation;

    private static final int PERMISSIONS_REQUEST_GPS = 1705;

    private LocationRequest locationRequest;

    final static int CADA_10seg = 10000;
    final static int CADA_5seg = 5000;

    Marker otherMarker;


    /* ON CREATE */
    /* ********* */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Extraemos la meta-data del manifiesto y la mostramos en un Toast
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String miNombre = bundle.getString("mi_nombre");

            Toast.makeText(getApplicationContext(), "Bienvenido " + miNombre, Toast.LENGTH_LONG).show();

        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }


        // Botonera
        Button btnSatelite = (Button) findViewById(R.id.btnSatelite);
        Button btnHibrido = (Button) findViewById(R.id.btnHibrido);
        Button btnNormal = (Button) findViewById(R.id.btnNormal);
        Button btnRuta = (Button) findViewById(R.id.btnRuta);
        Button btnZoom = (Button) findViewById(R.id.btnZoom);
        Button btnCompass = (Button) findViewById(R.id.btnCompass);
        Button btnMapToolbar = (Button) findViewById(R.id.btnMapToolbar);
        Button btnMyLocation = (Button) findViewById(R.id.btnMyLocation);
        Button btnIniciar = (Button) findViewById(R.id.btnIniciar);
        Button btnDetener = (Button) findViewById(R.id.btnDetener);

        btnSatelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mMap){
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }
        });

        btnHibrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mMap){
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
            }
        });

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mMap){
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });


        btnRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mMap){
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
            }
        });

        btnZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mMap){
                    if (mMap.getUiSettings().isZoomControlsEnabled()){
                        mMap.getUiSettings().setZoomControlsEnabled(false);
                    }else{
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                    }
                }
            }
        });

        btnCompass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mMap){
                    if (mMap.getUiSettings().isCompassEnabled()){
                        mMap.getUiSettings().setCompassEnabled(false);
                    }else{
                        mMap.getUiSettings().setCompassEnabled(true);
                    }
                }
            }
        });


        btnMapToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mMap){
                    if (mMap.getUiSettings().isMapToolbarEnabled()){
                        mMap.getUiSettings().setMapToolbarEnabled(false);
                    }else{
                        mMap.getUiSettings().setMapToolbarEnabled(true);
                    }
                }
            }
        });

        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mMap){
                    if (mMap.getUiSettings().isMyLocationButtonEnabled()){
                        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            mMap.setMyLocationEnabled(false);
                        }
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);

                    }else{
                        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            mMap.setMyLocationEnabled(true);
                        }
                        mMap.getUiSettings().setMyLocationButtonEnabled(true);
                    }
                }
            }
        });


        solicitarPermisoGPS();


        // LOCALIZACION - GoogleApiClient
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                        }

                        if (myLocation != null){
                            LatLng newPos = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                            myMarker.setPosition(newPos);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPos,17));
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        googleApiClient.connect();
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(), "Error de Conexion con Google",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(LocationServices.API)
                .build();

        // LOCATION REQUEST - Actualizacion de Posicion
        locationRequest = new LocationRequest();
        locationRequest.setInterval(CADA_10seg);
        locationRequest.setFastestInterval(CADA_5seg);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                            locationRequest, MapsActivity.this);
                }
            }
        });


        btnDetener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,
                            MapsActivity.this);
                }
            }
        });
    }

    /* ON START */
    /* ******** */
    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    /* ON STOP */
    /* ******* */
    @Override
    protected void onStop() {
        super.onStop();

        if (null != googleApiClient){
            if (googleApiClient.isConnected()){
                googleApiClient.disconnect();
            }
        }

    }

    /* ON MAP READY */
    /* ************ */
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-32.9477, -60.6305);
        myMarker = mMap.addMarker(new MarkerOptions().position(sydney).title("Este es el Monumento"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (null == otherMarker){
                    otherMarker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Nuevo Marcador")
                            .snippet("Este marcador fue agregado con el dedo"));
                }else{
                    otherMarker.setPosition(latLng);
                }
            }
        });
    }


    /* SOLICITAR PERMISO GPS */
    /* ********************* */
    private void solicitarPermisoGPS (){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_GPS);
        } else {
            // YA ESTOY AUTORIZADO
        }
    }


    /* ON PERMISSION REQUEST RESPONSE */
    /* ****************************** */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_GPS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    Toast.makeText(getApplicationContext(), "Sin permiso no se puede usar la app",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            myLocation = location;
        }

        if (myLocation != null){
            LatLng newPos = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            myMarker.setPosition(newPos);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPos,17));
        }
    }
}
