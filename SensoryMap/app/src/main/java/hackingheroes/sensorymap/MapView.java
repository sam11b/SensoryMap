package hackingheroes.sensorymap;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.android.volley.toolbox.*;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.android.volley.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MapView extends AppCompatActivity implements OnMapReadyCallback {
    private DatabaseReference mDatabase;
    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        final RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=37.8716,-122.2727&radius=50&type=restaurant&key=AIzaSyApsJo_dGvaPYvUeQYFb0bGzI9jAdbHeNk";
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<String> c = new ArrayList<>();
                            JSONObject myObject = new JSONObject(response);
                            JSONArray results = myObject.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                String k =  results.getJSONObject(i).get("name").toString();
                                c.add(k);
                            }
                            //c is now a list of all closeby restaurants to the location @ 37.8716,-122.2727
                            //these can probably be pushed to the database? We can also discuss using a closer location
                        }catch (JSONException e) {
                            Log.d("ERROR", "error => " + e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", "error => " + error.toString());
            }
        });
        queue.add(postRequest);

    }

    public void listButtonPressed(View v) {
        // Make list button appear pressed
        Button listButton = findViewById(R.id.list_button);
        visualPressButton(listButton);

        // Make map button appear un-pressed
        Button mapButton = findViewById(R.id.map_button);
        visualUnPressButton(mapButton);
        Intent listIntent = new Intent(MapView.this, MainActivity.class);
        MapView.this.startActivity(listIntent);
    }

    public void mapButtonPressed(View v) {
        // Make list button appear un-pressed
        Button listButton = findViewById(R.id.list_button);
        visualUnPressButton(listButton);

        // Make map button appear pressed
        Button mapButton = findViewById(R.id.map_button);
        visualPressButton(mapButton);
    }

    @TargetApi(23)
    private void visualPressButton(Button b) {
        b.setBackground(getDrawable(R.drawable.button_pressed));
        b.setTextColor(getColor(R.color.colorPrimary));
    }

    @TargetApi(23)
    private void visualUnPressButton(Button b) {
        b.setBackground(getDrawable(R.drawable.button_border));
        b.setTextColor(getColor(R.color.text_light));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Fetch data from database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("locations");

        // Set listener to fetch from database
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot location: dataSnapshot.getChildren()) {
                    LocationObj loc = location.getValue(LocationObj.class);
                    loc.setID(location.getKey());
                    LatLng locationLatAndLon = new LatLng(loc.lat, loc.lon);
                    mMap.addMarker(new MarkerOptions().position(locationLatAndLon).title(loc.name));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Read failed");
            }
        });

        // Add a marker in Sydney, Australia, and move the camera.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    99);
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location == null) {
                                // Logic to handle location object
                                return;
                            }
                            double lat = location.getLatitude();
                            double lon = location.getLongitude();

                            LatLng currentLocation = new LatLng(lat, lon);
                            mMap.addMarker(new MarkerOptions().position(currentLocation).title("My Location")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                            mMap.setMinZoomPreference(14.0f);

                        }
                    });
        }
    }
}
