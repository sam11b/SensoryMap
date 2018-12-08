package hackingheroes.sensorymap;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_row, parent, false);

        return new ReviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        holder.text.setText(reviewList.get(position).getComment());
        holder.tags.setText(String.format(
                "Tags: %s",
                reviewList.get(position).tagsString()));
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public TextView tags;

        public ReviewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.review_text);
            tags = view.findViewById(R.id.review_tags);
        }

    }
}
