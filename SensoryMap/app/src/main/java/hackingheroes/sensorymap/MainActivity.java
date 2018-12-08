package hackingheroes.sensorymap;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationFragment.OnListFragmentInteractionListener {

    //Database reference
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("locations");

        // Code to populate locations on our database

        /*String location1 = myRef.push().getKey();
        LocationObj subway = new LocationObj("Subway");
        subway.setLocation(37.875265, -122.259676);
        subway.setID(location1);
        Review newReview = new Review("I love sandwiches!");
        ArrayList<String> tags = new ArrayList<>();
        newReview.addTag("BRIGHT");
        newReview.addTag("HANDICAP ACCESSIBLE");
        newReview.addTag("QUIET");
        subway.addReview(newReview);
        myRef.child(location1).setValue(subway);*/
//
//        String location2 = myRef.push().getKey();
//        LocationObj cafe3 = new LocationObj("Cafe 3");
//        cafe3.setID(location2);
//        cafe3.addTag("noisy");
//        Review newReview2 = new Review("Lovely");
//        cafe3.addReview(newReview2);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Object location = dataSnapshot.getValue(LocationObj.class);
                //Log.i("SnapShot", "Value is: " + location);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });




        // Add fragment to main view
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_placeholder, new LocationFragment());
        // Complete the changes added above
        ft.commit();
    }

    public void filterButtonPressed(View v) {
        Intent filterIntent = new Intent(MainActivity.this, FilterActivity.class);
        MainActivity.this.startActivity(filterIntent);
    }

    public void listButtonPressed(View v) {
        // Make list button appear pressed
        Button listButton = findViewById(R.id.list_button);
        visualPressButton(listButton);

        // Make map button appear un-pressed
        Button mapButton = findViewById(R.id.map_button);
        visualUnPressButton(mapButton);
    }

    public void mapButtonPressed(View v) {
        // Make list button appear un-pressed
        Button listButton = findViewById(R.id.list_button);
        visualUnPressButton(listButton);

        // Make map button appear pressed
        Button mapButton = findViewById(R.id.map_button);
        visualPressButton(mapButton);

        Intent mapIntent = new Intent(MainActivity.this, MapView.class);
        MainActivity.this.startActivity(mapIntent);
    }

    public void settingsButtonPressed(View v) {
        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
        MainActivity.this.startActivity(settingsIntent);
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
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof LocationFragment) {
            LocationFragment frag = (LocationFragment) fragment;
            frag.setOnListFragmentInteractionListener(this);
        }
    }

    public void onListFragmentClick(LocationObj loc) {
        Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
        // TODO: change this to unique identifier
        detailsIntent.putExtra("Id", loc.id);
        MainActivity.this.startActivity(detailsIntent);
    }
}
