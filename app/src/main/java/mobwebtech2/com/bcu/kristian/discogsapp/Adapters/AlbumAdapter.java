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
import mobwebtech2.com.bcu.kristian.discogsapp.Models.AlbumList;

public class AlbumAdapter extends ArrayAdapter<AlbumList> {
    public AlbumAdapter(Context context, List<AlbumList> responses) {
        super(context, 0, responses);
    }

    private static final String TAG = "debug";

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AlbumList album = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent,
                    false);
        }

        ImageView albumImg = convertView.findViewById(R.id.listIV);
        TextView albumTxt = convertView.findViewById(R.id.titleTxt);

        String img = album.getThumb();

        if (img.equals("")) {

            Log.d(TAG, "Empty images");
            albumImg.setImageResource(R.mipmap.noimg);
        } else {

            Picasso.get().load(img).into(albumImg);
        }

        albumTxt.setText(album.getTitle());

        return convertView;
    }
}
