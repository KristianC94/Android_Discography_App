package mobwebtech2.com.bcu.kristian.discogsapp.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mobwebtech2.com.bcu.kristian.discogsapp.Models.Tracklist;
import mobwebtech2.com.bcu.kristian.discogsapp.R;

public class TracklistAdapter extends ArrayAdapter<Tracklist> {
    public TracklistAdapter(Context context, List<Tracklist> tracklists) {
        super(context, 0, tracklists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tracklist tracklist = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.track_layout,
                    parent, false);
        }

        TextView name = convertView.findViewById(R.id.trackName);
        TextView length = convertView.findViewById(R.id.trackLength);

        name.setText(tracklist.getTitle());
        length.setText(tracklist.getDuration());

        return convertView;
    }
}
