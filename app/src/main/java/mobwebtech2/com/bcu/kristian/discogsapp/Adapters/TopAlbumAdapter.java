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

import mobwebtech2.com.bcu.kristian.discogsapp.Models.LastFmAlbum;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.LastFmImage;
import mobwebtech2.com.bcu.kristian.discogsapp.R;

public class TopAlbumAdapter extends ArrayAdapter<LastFmAlbum> {
    public TopAlbumAdapter(Context context, List<LastFmAlbum> responses) {
        super(context, 0, responses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LastFmAlbum album = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent,
                    false);
        }

        ImageView albumImg = convertView.findViewById(R.id.listIV);
        TextView albumTxt = convertView.findViewById(R.id.titleTxt);

        albumTxt.setText(album.getName());

        List<LastFmImage> images = album.getImage();
        LastFmImage image = images.get(2);

        if (!image.getText().equals("")) {

            String img = image.getText();
            Picasso.get().load(img).into(albumImg);
        }

        return convertView;
    }
}
