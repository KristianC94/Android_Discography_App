package mobwebtech2.com.bcu.kristian.discogsapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mobwebtech2.com.bcu.kristian.discogsapp.R;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.ArtistList;

public class ArtistAdapter extends ArrayAdapter<ArtistList> {
    public ArtistAdapter(Context context, List<ArtistList> responses) {
        super(context, 0, responses);
    }

    private static final String TAG = "pic_check";

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ArtistList artist = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent,
                    false);
        }

        ImageView artistImg = convertView.findViewById(R.id.listIV);
        TextView artistTxt = convertView.findViewById(R.id.titleTxt);

        String img = artist.getThumb();

        if (img.equals("")) {

            Log.d(TAG, "Empty images");
            artistImg.setImageResource(R.mipmap.noimg);
        } else {

            Picasso.get().load(img).into(artistImg);
        }

        artistTxt.setText(artist.getTitle());

        return convertView;
    }
}
