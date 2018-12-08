package hackingheroes.sensorymap;

import java.util.ArrayList;
import java.util.List;

public class Review {

    public List<String> tags;
    public String comment;


    public Review(){
        this.comment = "";
        this.tags = new ArrayList<>();
    }

    public Review(String comment){
        this.comment = comment;
        this.tags = new ArrayList<>();
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() { return this.comment; }

    public List<String> getTags() { return this.tags; }

    public String tagsString() {
        String tagString = "";
        if (tags.size() > 0) {
            tagString += tags.get(0);
        }
        if (tags.size() > 1) {
            for (int i = 1; i < tags.size(); i++) {
                tagString += ", " + tags.get(i);
            }
        }

        return tagString;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag){
        this.tags.add(tag);
    }
}
