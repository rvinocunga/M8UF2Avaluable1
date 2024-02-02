package com.example.m8uf2avaluable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity {
    private MapView mapa;
    private MapController mapController;
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.contexto = this.getApplicationContext();
        Configuration.getInstance().load(contexto, PreferenceManager.getDefaultSharedPreferences(contexto));

        setContentView(R.layout.activity_main);

        this.mapa = this.findViewById(R.id.mapa);
        this.mapController = (MapController) this.mapa.getController();
        // Aplicar zoom al mapa
        this.mapController.setZoom(18);

        // Centrar mapa en coordenadas torre
        // this.mapController.setCenter(new GeoPoint(41.403333333333, 2.1894444444444));

        // Afegir marcadors al mapa
        // this.creaMarcadorSimple();
    }

    public void centrarTorreAgbar(View view) {
        this.mapController.animateTo(new GeoPoint(41.403333333333, 2.1894444444444));
    }
}