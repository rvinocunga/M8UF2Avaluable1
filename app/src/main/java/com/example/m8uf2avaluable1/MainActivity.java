package com.example.m8uf2avaluable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity implements LocationListener{
    private MapView mapa;
    private MapController mapController;
    private Context contexto;
    private LocationManager locationManager;
    private double longUsuari;
    private double latUsuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.contexto = this.getApplicationContext();
        Configuration.getInstance().load(contexto, PreferenceManager.getDefaultSharedPreferences(contexto));

        setContentView(R.layout.activity_main);

        this.mapa = this.findViewById(R.id.mapa);
        this.mapController = (MapController) this.mapa.getController();

        //Manager
        this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Solicitar permisos
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // codi si tenim permís
            this.iniciaLoacalitzacio();
        } else {
            // en cas de no tenir, el demanem
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Aplicar zoom al mapa
        this.mapController.setZoom(18);

        // Centrar mapa en coordenadas Barcelona
        this.mapController.setCenter(new GeoPoint(41.3887900, 2.1589900));

        // Afegir marcadors al mapa
        this.creaMarcadorSimple();
    }

    private void creaMarcadorSimple() {
        // Crear marcador Torre Agbar
        Marker markerTorreAgbar = new Marker(this.mapa);
        GeoPoint point = new GeoPoint(41.403333333333, 2.1894444444444);
        markerTorreAgbar.setPosition(point);
        markerTorreAgbar.setTitle("Edifici Agbar");
        markerTorreAgbar.setSubDescription("<p>Situat a la Plaça de les Glòries Catalanes de Barcelona</p><ul><li> Av. Diagonal, 211 </li><li> Barcelona </li></ul>");
        //markerTorreAgbar.setImage(this.getDrawable(R.drawable.torreagbar));

        //añadir marcador al mapa
        this.mapa.getOverlays().add(markerTorreAgbar);
    }

    @SuppressLint("MissingPermission")
    private void iniciaLoacalitzacio() {
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000l, 0f, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            this.iniciaLoacalitzacio();
        }else {
            Toast.makeText(this, "L'aplicació no pot funcionar sense aquest permís.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.longUsuari = location.getLongitude();
        this.latUsuari = location.getLatitude();
    }
    public void centrarUsuari(View view) {
        this.mapController.animateTo(new GeoPoint(latUsuari, longUsuari));
    }

}