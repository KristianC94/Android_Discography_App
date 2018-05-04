package mobwebtech2.com.bcu.kristian.discogsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mobwebtech2.com.bcu.kristian.discogsapp.Models.Video;
import mobwebtech2.com.bcu.kristian.discogsapp.R;

public class VideoListAdapter extends ArrayAdapter<Video> {
    public VideoListAdapter(Context context, List<Video> videos) {
        super(context, 0, videos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Video video = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.track_layout,
                    parent, false);
        }

        TextView name = convertView.findViewById(R.id.trackName);
        TextView length = convertView.findViewById(R.id.trackLength);

        name.setText(video.getTitle());
        length.setText("" + video.getDuration());

        return convertView;
    }
}
