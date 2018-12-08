package hackingheroes.sensorymap;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hackingheroes.sensorymap.LocationFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link LocationObj} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyLocationRecyclerViewAdapter extends RecyclerView.Adapter<MyLocationRecyclerViewAdapter.ViewHolder> {

    private final List<LocationObj> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyLocationRecyclerViewAdapter(List<LocationObj> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mTypeView.setText(mValues.get(position).type);
        holder.mTag1.setText(mValues.get(position).tags.get(0));
        if (mValues.get(position).tags.get(0).equals("WEIRD SMELL") || mValues.get(position).tags.get(0).equals("SMOKEY")) {
            holder.mTag1.setBackgroundColor(Color.parseColor("#AA9883E5"));
        }
        else if (mValues.get(position).tags.get(0).equals("HANDICAP ACCESSIBLE") || mValues.get(position).tags.get(0).equals("NOT HANDICAP ACCESSIBLE")) {
            holder.mTag1.setBackgroundColor(Color.parseColor("#AAFF4E00"));
        }
        else if (mValues.get(position).tags.get(0).equals("BRIGHT") || mValues.get(position).tags.get(0).equals("DARK") || mValues.get(position).tags.get(0).equals("STROBE LIGHTS")) {
            holder.mTag1.setBackgroundColor(Color.parseColor("#AAFF9F1C"));
        }
        else {
            holder.mTag1.setBackgroundColor(Color.parseColor("#AA50C9CE"));
        }
        holder.mTag2.setText(mValues.get(position).tags.get(1));
        if (mValues.get(position).tags.get(1).equals("WEIRD SMELL") || mValues.get(position).tags.get(1).equals("SMOKEY")) {
            holder.mTag2.setBackgroundColor(Color.parseColor("#AA9883E5"));
        }
        else if (mValues.get(position).tags.get(1).equals("HANDICAP ACCESSIBLE") || mValues.get(position).tags.get(1).equals("NOT HANDICAP ACCESSIBLE")) {
            holder.mTag2.setBackgroundColor(Color.parseColor("#AAFF4E00"));
        }
        else if (mValues.get(position).tags.get(1).equals("BRIGHT") || mValues.get(position).tags.get(1).equals("DARK") || mValues.get(position).tags.get(1).equals("STROBE LIGHTS")) {
            holder.mTag2.setBackgroundColor(Color.parseColor("#AAFF9F1C"));
        }
        else {
            holder.mTag2.setBackgroundColor(Color.parseColor("#AA50C9CE"));
        }
        holder.mTag3.setText(mValues.get(position).tags.get(2));
        if (mValues.get(position).tags.get(2).equals("WEIRD SMELL") || mValues.get(position).tags.get(2).equals("SMOKEY")) {
            holder.mTag3.setBackgroundColor(Color.parseColor("#AA9883E5"));
        }
        else if (mValues.get(position).tags.get(2).equals("HANDICAP ACCESSIBLE") || mValues.get(position).tags.get(2).equals("NOT HANDICAP ACCESSIBLE")) {
            holder.mTag3.setBackgroundColor(Color.parseColor("#AAFF4E00"));
        }
        else if (mValues.get(position).tags.get(2).equals("BRIGHT") || mValues.get(position).tags.get(2).equals("DARK") || mValues.get(position).tags.get(2).equals("STROBE LIGHTS")) {
            holder.mTag3.setBackgroundColor(Color.parseColor("#AAFF9F1C"));
        }
        else {
            holder.mTag3.setBackgroundColor(Color.parseColor("#AA50C9CE"));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentClick(holder.mItem);
                }
            }
        });
    }

    private String findDistance(LocationObj loc) {
        // Find user's location
        // Compute cartesian distance
        // Format with string
        return "";
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mTypeView;
        public final TextView mTag1;
        public final TextView mTag2;
        public final TextView mTag3;
        public LocationObj mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mTypeView = (TextView) view.findViewById(R.id.type);
            mTag1 = (TextView) view.findViewById(R.id.tag1);
            mTag2 = (TextView) view.findViewById(R.id.tag2);
            mTag3 = (TextView) view.findViewById(R.id.tag3);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTypeView.getText() + "'";
        }
    }
}
