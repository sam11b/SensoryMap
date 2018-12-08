package hackingheroes.sensorymap;

import android.annotation.TargetApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LocationObj {

    public String name;
    public double lon;
    public double lat;
    public String type;
    public String id;
    List<String> tags;
    List<Review> reviews;

    public LocationObj(){
        this.name = "";
        this.tags = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.lat = 0;
        this.lon = 0;
    }

    public LocationObj(String name) {
        this.name = name;
        this.tags = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.lat = 0;
        this.lon = 0;
        this.type = "";
    }

    public void setID(String id) { this.id = id; }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() { return this.reviews; }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        updateTags(review);
    }

    @TargetApi(24)
    private void updateTags(Review review) {
        HashMap<String, Integer> tagInstance = new HashMap<>();

        // Add tags from reviews
        for (Review rev: this.reviews) {
            for (String tag: rev.tags) {
                if (tagInstance.containsKey(tag)) {
                    Integer counter = tagInstance.get(tag);
                    tagInstance.replace(tag, counter + 1);
                } else {
                    tagInstance.put(tag, 1);
                }
            }
        }

        // Find counts of most common tags
        List<Integer> sortedCounts = new ArrayList<Integer>(tagInstance.values());
        Collections.sort(sortedCounts, Collections.<Integer>reverseOrder());
        int tag1 = sortedCounts.get(0);
        int tag2 = sortedCounts.get(1);
        int tag3 = sortedCounts.get(2);

        // Find most common tags
        boolean tag1found = false;
        boolean tag2found = false;
        boolean tag3found = false;
        ArrayList<String> newTags = new ArrayList<>();
        for (String tag: tagInstance.keySet()) {
            if (!tag1found && tagInstance.get(tag) == tag1) {
                newTags.add(tag);
                tag1found = true;
            } else if (!tag2found && tagInstance.get(tag) == tag2) {
                newTags.add(tag);
                tag2found = true;
            } else if (!tag3found && tagInstance.get(tag) == tag3) {
                newTags.add(tag);
                tag3found = true;
            }
        }

        // Replace old tags with new tags
        setTags(newTags);
    }

    public void setLatAndLon(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
