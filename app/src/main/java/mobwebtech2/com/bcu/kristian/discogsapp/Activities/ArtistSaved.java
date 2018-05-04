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
import mobwebtech2.com.bcu.kristian.discogsapp.Models.ArtistList;

public class ArtistSaved extends Fragment {

    private ListView artistList;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDB;
    private FirebaseListAdapter listAdapter;
    private static final String TAG = "debug";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_saved, container, false);

        artistList = rootView.findViewById(R.id.savedArtistList);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDB = FirebaseDatabase.getInstance();

        artistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ArtistList artistList = (ArtistList) parent.getItemAtPosition(position);
                Intent i = new Intent(v.getContext(), ArtistDetails.class);
                i.putExtra("Artist details", artistList);
                startActivity(i);
            }
        });

        fillList();

        return rootView;
    }

    public void fillList() {

        String uid = firebaseAuth.getCurrentUser().getUid();
        Query query = firebaseDB.getReference(uid).child("artists");

        Log.d(TAG, query.toString());

        FirebaseListOptions<ArtistList> listOptions = new FirebaseListOptions.Builder<ArtistList>()
                .setLayout(R.layout.list_layout).setQuery(query, ArtistList.class).build();

        listAdapter = new FirebaseListAdapter(listOptions) {
            @Override
            protected void populateView(View v, Object model, int position) {

                TextView artistName = v.findViewById(R.id.titleTxt);
                ImageView artistPic = v.findViewById(R.id.listIV);

                ArtistList artist = (ArtistList) model;
                artistName.setText(artist.getTitle().toString());
                Picasso.get().load(artist.getCoverImage().toString()).into(artistPic);
            }
        };

        artistList.setAdapter(listAdapter);
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
