package hackingheroes.sensorymap;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.EditText;

public class ReviewActivity extends AppCompatActivity {

    private String id;
    private String name;
    private Review review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // Retrieve unique identifier
        Intent intent = getIntent();
        id = intent.getStringExtra("Id");
        name = intent.getStringExtra("Name");

        // Set header text
        TextView headerText = findViewById(R.id.review_header);
        headerText.setText(String.format(getString(R.string.review_header), name));

        //Create a review object
        review = new Review();
    }

    public void quietButtonPressed(View v) {
        // Make map button appear pressed
        Button mapButton = findViewById(R.id.quiet_tag);
        pressSoundButton(mapButton);
        review.addTag("QUIET");
    }

    public void noiseButtonPressed(View v) {
        // Make map button appear pressed
        Button mapButton = findViewById(R.id.noisy_tag);
        pressSoundButton(mapButton);
        review.addTag("NOISY");
    }

    public void whiteNoiseButtonPressed(View v) {
        // Make map button appear pressed
        Button mapButton = findViewById(R.id.white_noise);
        pressSoundButton(mapButton);
        review.addTag("WHITE NOISE");
    }

    public void liveMusicButtonPressed(View v) {
        // Make map button appear pressed
        Button mapButton = findViewById(R.id.live_music_tag);
        pressSoundButton(mapButton);
        review.addTag("LIVE MUSIC");
    }

    public void weirdSmellMusicButtonPressed(View v) {
        // Make map button appear pressed
        Button mapButton = findViewById(R.id.weird_smell_tag);
        pressSmellButton(mapButton);
        review.addTag("WEIRD SMELL");
    }

    public void smokeyButtonPressed(View v) {
        // Make map button appear pressed
        Button mapButton = findViewById(R.id.smokey);
        pressSmellButton(mapButton);
        review.addTag("SMOKEY");
    }

    public void brightButtonPressed(View v) {
        // Make map button appear pressed
        Button mapButton = findViewById(R.id.bright_tag);
        pressSightButton(mapButton);
        review.addTag("BRIGHT");
    }

    public void darkButtonPressed(View v) {
        // Make map button appear pressed
        Button mapButton = findViewById(R.id.dark_tag);
        pressSightButton(mapButton);
        review.addTag("DARK");
    }

    public void strobeButtonPressed(View v) {
        // Make map button appear pressed
        Button mapButton = findViewById(R.id.strobe_lights_tab);
        pressSightButton(mapButton);
        review.addTag("STROBE LIGHTS");
    }

    public void handicapButtonPressed(View v) {
        // Make map button appear pressed
        Button mapButton = findViewById(R.id.handicap_tag);
        pressPhysicalButton(mapButton);
        review.addTag("HANDICAP ACCESSIBLE");
    }

    public void NohandicapButtonPressed(View v) {
        // Make map button appear pressed
        Button mapButton = findViewById(R.id.no_handicap_tag);
        pressPhysicalButton(mapButton);
        review.addTag("NOT HANDICAP ACCESSIBLE");
    }

    public void submitClicked(View v) {
        Button b = findViewById(R.id.submit_button);
        visualPressButton(b);

        EditText edit = findViewById(R.id.comments);
        review.setComment(edit.getText().toString());
        if (review.comment.equals("")) {
            review.setComment("No individual comment");
        }

        //THIS IS WHERE WE WILL PUSH THE REVIEW TO THE DATABASE
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(String.format("locations/%s", id));

        // Get LocationObj object from database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LocationObj loc = dataSnapshot.getValue(LocationObj.class);
                loc.addReview(review);
                DatabaseReference locRef = database.getReference(String.format("locations/%s", id));
                locRef.setValue(loc);
                closeReview();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Error retrieving details");
            }
        });

        //String reviewID = myRef.push().getKey();
        //myRef.child(reviewID).setValue(review);

    }

    private void closeReview() {
        this.finish();
    }

    @TargetApi(23)
    private void visualPressButton(Button b) {
        b.setBackground(getDrawable(R.drawable.button_pressed));
        b.setTextColor(getColor(R.color.colorPrimary));
    }

    @TargetApi(23)
    private void pressSoundButton(Button b) {
        b.setBackground(getDrawable(R.drawable.sound_pressed));
        b.setTextColor(getColor(R.color.colorPrimary));
    }

    @TargetApi(23)
    private void pressSmellButton(Button b) {
        b.setBackground(getDrawable(R.drawable.smell_pressed));
        b.setTextColor(getColor(R.color.colorPrimary));
    }

    @TargetApi(23)
    private void pressSightButton(Button b) {
        b.setBackground(getDrawable(R.drawable.sight_pressed));
        b.setTextColor(getColor(R.color.colorPrimary));
    }

    @TargetApi(23)
    private void pressPhysicalButton(Button b) {
        b.setBackground(getDrawable(R.drawable.physical_pressed));
        b.setTextColor(getColor(R.color.colorPrimary));
    }


    @TargetApi(23)
    private void visualUnPressButton(Button b) {
        b.setBackground(getDrawable(R.drawable.button_border));
        b.setTextColor(getColor(R.color.text_light));
    }
}
