package rocks.crisjr.partiusketch;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import rocks.crisjr.partiusketch.controller.MapController;

public class CreateActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private boolean menuCollapsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button;

        /* create map */
        setContentView(R.layout.activity_create);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // add collapsible abilities to menu
        button = (Button) findViewById(R.id.buttonColl);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapseMenu();
            }
        });

        // add returning ability to back button
        button = (Button) findViewById(R.id.buttonBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity();*/
            }
        });
    }


    /**
     * Adds a collapsible ability to the sidebar menu
     */
    void collapseMenu() {
        MapController controller = new MapController(getApplicationContext());
        LinearLayout layoutMenu = (LinearLayout) findViewById(R.id.layoutMenu);
        Button buttonColl = (Button) findViewById(R.id.buttonColl);
        Button buttonFilter = (Button) findViewById(R.id.buttonFilter);
        Button buttonBack = (Button) findViewById(R.id.buttonBack);

        if (menuCollapsed) {
            buttonFilter.setVisibility(View.VISIBLE);
            buttonBack.setVisibility(View.VISIBLE);
            buttonColl.setText("<<<");
            layoutMenu.getLayoutParams().width = controller.convertDiptoPix(150);
        } else {
            buttonFilter.setVisibility(View.INVISIBLE);
            buttonBack.setVisibility(View.INVISIBLE);
            buttonColl.setText(">>>");
            layoutMenu.getLayoutParams().width = controller.convertDiptoPix(50);
        }

        menuCollapsed = !menuCollapsed;
    }


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
        myMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        myMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
