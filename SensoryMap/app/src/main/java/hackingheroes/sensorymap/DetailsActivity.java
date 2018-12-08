package hackingheroes.sensorymap;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private String id;
    private LocationObj loc;

    private RecyclerView mRecyclerView;
    private ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Retrieve unique identifier
        Intent intent = getIntent();
        id = intent.getStringExtra("Id");

        // Retrieve info from database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(String.format("locations/%s", id));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LocationObj loc = dataSnapshot.getValue(LocationObj.class);
                setDetails(loc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Error retrieving details");
            }
        });
    }

    public void shareDetails(View v) {

        //Creating sharing Intent
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        //Building the Share TEXTBody
        String shareBody = this.loc.name + " \n \nTags: \n";
        shareBody += stringifyTags(this.loc.tags);
        shareBody += "\n \n Reviews: \n";

        for (int i = 0; i < this.loc.reviews.size(); i++) {
            Review currentReview =  this.loc.reviews.get(i);

            shareBody += currentReview.comment + "\n";
            shareBody += stringifyTags(currentReview.tags) + "\n";
        }

        //Add the shareBody and shareSubject to the share Intent
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, this.loc.name);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

        //Create a Chooser
        DetailsActivity.this.startActivity (Intent.createChooser(sharingIntent, "Share LocationObj via"));
    }

    private String stringifyTags(List<String> tags) {

        String result = "";
        for (int i = 0; i < this.loc.tags.size(); i ++) {
            result += this.loc.tags.get(i) + ", ";
        }
        return result;
    }

    private void setDetails(LocationObj loc) {
        this.loc = loc;

        // Set location name
        TextView locationName = findViewById(R.id.details_name);
        locationName.setText(loc.name);

        // Set location type
        TextView locationType = findViewById(R.id.details_description);
        locationType.setText(loc.type);

        // Set tags
        TextView tag1 = findViewById(R.id.details_tag1);
        tag1.setText(loc.tags.get(0));
        if (loc.tags.get(0).equals("WEIRD SMELL") || loc.tags.get(0).equals("SMOKEY")) {
            tag1.setBackgroundColor(Color.parseColor("#AA9883E5"));
        }
        else if (loc.tags.get(0).equals("HANDICAP ACCESSIBLE") || loc.tags.get(0).equals("NOT HANDICAP ACCESSIBLE")) {
            tag1.setBackgroundColor(Color.parseColor("#AAFF4E00"));
        }
        else if (loc.tags.get(0).equals("BRIGHT") || loc.tags.get(0).equals("DARK") || loc.tags.get(0).equals("STROBE LIGHTS")) {
            tag1.setBackgroundColor(Color.parseColor("#AAFF9F1C"));
        }
        else {
            tag1.setBackgroundColor(Color.parseColor("#AA50C9CE"));
        }
        TextView tag2 = findViewById(R.id.details_tag2);
        tag2.setText(loc.tags.get(1));
        if (loc.tags.get(1).equals("WEIRD SMELL") || loc.tags.get(1).equals("SMOKEY")) {
            tag2.setBackgroundColor(Color.parseColor("#AA9883E5"));
        }
        else if (loc.tags.get(1).equals("HANDICAP ACCESSIBLE") || loc.tags.get(1).equals("NOT HANDICAP ACCESSIBLE")) {
            tag2.setBackgroundColor(Color.parseColor("#AAFF4E00"));
        }
        else if (loc.tags.get(1).equals("BRIGHT") || loc.tags.get(1).equals("DARK") || loc.tags.get(1).equals("STROBE LIGHTS")) {
            tag2.setBackgroundColor(Color.parseColor("#AAFF9F1C"));
        }
        else {
            tag2.setBackgroundColor(Color.parseColor("#AA50C9CE"));
        }
        TextView tag3 = findViewById(R.id.details_tag3);
        tag3.setText(loc.tags.get(2));
        if (loc.tags.get(2).equals("WEIRD SMELL") || loc.tags.get(2).equals("SMOKEY")) {
            tag3.setBackgroundColor(Color.parseColor("#AA9883E5"));
        }
        else if (loc.tags.get(2).equals("HANDICAP ACCESSIBLE") || loc.tags.get(2).equals("NOT HANDICAP ACCESSIBLE")) {
            tag3.setBackgroundColor(Color.parseColor("#AAFF4E00"));
        }
        else if (loc.tags.get(2).equals("BRIGHT") || loc.tags.get(2).equals("DARK") || loc.tags.get(2).equals("STROBE LIGHTS")) {
            tag3.setBackgroundColor(Color.parseColor("#AAFF9F1C"));
        }
        else {
            tag3.setBackgroundColor(Color.parseColor("#AA50C9CE"));
        }

        // Set reviews
        mRecyclerView = findViewById(R.id.reviews_list);
        mReviewAdapter = new ReviewAdapter(loc.getReviews());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mReviewAdapter);

        /*if (loc.reviews.size() > 0) {
            TextView review = findViewById(R.id.review1);
            review.setText(loc.reviews.get(0).comment);
            String tags = "";
            if (loc.reviews.get(0).tags.size() > 0) {
                for (String tag : loc.reviews.get(0).tags) {
                    tags = tags + ", " + tag;
                }
                tags = tags.substring(2);
            }
            TextView reviewTags = findViewById(R.id.review1_tags);
            reviewTags.setText(String.format(getString(R.string.review_tags), tags));
        }
        if (loc.reviews.size() > 1) {
            TextView review = findViewById(R.id.review2);
            review.setText(loc.reviews.get(1).comment);
            String tags = "";
            for (String tag: loc.reviews.get(1).tags) {
                tags = tags + ", " + tag;
            }
            tags = tags.substring(2);
            TextView reviewTags = findViewById(R.id.review2_tags);
            reviewTags.setText(String.format(getString(R.string.review_tags), tags));
        }*/
    }

    public void closeDetails(View v) {
        this.finish();
    }

    public void leaveReview(View v) {
        Intent reviewIntent = new Intent(DetailsActivity.this, ReviewActivity.class);
        reviewIntent.putExtra("Id", id);
        reviewIntent.putExtra("Name", loc.name);
        DetailsActivity.this.startActivity(reviewIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
