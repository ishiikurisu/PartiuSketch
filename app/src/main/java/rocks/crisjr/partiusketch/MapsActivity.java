package rocks.crisjr.partiusketch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import rocks.crisjr.partiusketch.controller.BasicController;

public class MapsActivity
extends FragmentActivity
implements OnMapReadyCallback {

    private GoogleMap myMap;
    private boolean menuCollapsed = false;
    private BasicController controller = new BasicController();
    final private int CREATE_EVENT_REQUEST = 0;
    final private int SEARCH_EVENT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* create map */
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /* add collapsible abilities to menu */
        Button button = (Button) findViewById(R.id.buttonCollapse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapseMenu();
            }
        });
    }

    /**
     * Adds collapsible abilities to sidebar menu.
     */
    void collapseMenu() {
        LinearLayout layoutMenu = (LinearLayout) findViewById(R.id.layoutMenu);
        Button buttonCreate = (Button) findViewById(R.id.buttonCreate);
        Button buttonSearch = (Button) findViewById(R.id.buttonSearch);
        Button buttonCollapse = (Button) findViewById(R.id.buttonCollapse);

        controller.setContext(getApplicationContext());
        if (menuCollapsed) {
            buttonCreate.setVisibility(View.VISIBLE);
            buttonSearch.setVisibility(View.VISIBLE);
            buttonCollapse.setText("<<<");
            layoutMenu.getLayoutParams().width = controller.convertDiptoPix(150);
        } else {
            buttonCreate.setVisibility(View.INVISIBLE);
            buttonSearch.setVisibility(View.INVISIBLE);
            buttonCollapse.setText(">>>");
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

    /**
     * Method to check if Google Services is working
     */
    @Override
    protected void onResume() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        Context context = getApplicationContext();
        String result = "nope :(";

        super.onResume();
        if (api.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {
            result = "yep :)";
        }

        // Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to receive the results of the child activities
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode)
        {
            case SEARCH_EVENT_REQUEST:
            case CREATE_EVENT_REQUEST:
                if (resultCode == RESULT_OK)
                    controller = intent.getExtras().getParcelable("controller");
                break;
        }
    }

    /**
     * Callback to "Search Events" button. Sends the application's main controller
     * to the Search Activity.
     * @param view
     */
    public void searchEvents(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("controller", (Parcelable) controller);
        startActivityForResult(intent, SEARCH_EVENT_REQUEST);
    }

    /**
     * Callback to "Create Events" button
     * @param view
     */
    public void createEvents(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        intent.putExtra("controller", (Parcelable) controller);
        startActivityForResult(intent, CREATE_EVENT_REQUEST);
    }
}
