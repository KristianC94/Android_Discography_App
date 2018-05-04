package mobwebtech2.com.bcu.kristian.discogsapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import mobwebtech2.com.bcu.kristian.discogsapp.R;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.AlbumList;

public class AlbumSaved extends Fragment {

    private ListView albumList;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDB;
    private FirebaseListAdapter listAdapter;
    private static final String TAG = "debug";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.album_saved, container, false);

        albumList = rootView.findViewById(R.id.savedAlbumList);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDB = FirebaseDatabase.getInstance();

        albumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                AlbumList albumList = (AlbumList) parent.getItemAtPosition(position);
                Intent i = new Intent(v.getContext(), AlbumDetails.class);
                i.putExtra("Album details", albumList);
                startActivity(i);
            }
        });

        fillList();

        return rootView;
    }

    public void fillList() {

        String uid = firebaseAuth.getCurrentUser().getUid();
        Query query = firebaseDB.getReference(uid).child("albums");

        Log.d(TAG, query.toString());

        FirebaseListOptions<AlbumList> listOptions = new FirebaseListOptions.Builder<AlbumList>()
                .setLayout(R.layout.list_layout).setQuery(query, AlbumList.class).build();

        listAdapter = new FirebaseListAdapter(listOptions) {
            @Override
            protected void populateView(View v, Object model, int position) {

                TextView albumTitle = v.findViewById(R.id.titleTxt);
                ImageView albumCover = v.findViewById(R.id.listIV);

                AlbumList album = (AlbumList) model;
                albumTitle.setText(album.getTitle().toString());
                Picasso.get().load(album.getCoverImage().toString()).into(albumCover);
            }
        };

        albumList.setAdapter(listAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        listAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        listAdapter.startListening();
    }
}
