package mobwebtech2.com.bcu.kristian.discogsapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import mobwebtech2.com.bcu.kristian.discogsapp.Adapters.TracklistAdapter;
import mobwebtech2.com.bcu.kristian.discogsapp.Interfaces.AlbumClient;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.Album;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.Tracklist;
import mobwebtech2.com.bcu.kristian.discogsapp.R;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.AlbumList;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlbumDetails extends AppCompatActivity implements View.OnClickListener {

    private ImageView albumImg;
    private TextView albumTitle, albumArtist, albumGenre, trackListTitle;
    private ListView trackList;
    private TracklistAdapter tracklistAdapter;
    private FloatingActionButton homeBtn, saveBtn, tweetBtn, fbBtn, youtubeBtn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference discogsDB;
    private String albumCover = "";
    private String name = "";
    private int ID = 0;
    private String uri_url = "";
    private ShareDialog shareDialog;
    private static final String api_url = "https://api.discogs.com";
    private static final String TAG = "debug";
    private static final String PREFS = "Prefs";
    private static final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_details);

        albumImg = findViewById(R.id.albumImg);
        albumTitle = findViewById(R.id.albumName);
        albumArtist = findViewById(R.id.albumArtist);
        albumGenre = findViewById(R.id.albumGenre);
        trackListTitle = findViewById(R.id.tracklistTitle);
        trackList = findViewById(R.id.trackList);
        homeBtn = findViewById(R.id.albumHomeBtn);
        saveBtn = findViewById(R.id.albumSaveBtn);
        tweetBtn = findViewById(R.id.albumTweetBtn);
        fbBtn = findViewById(R.id.albumFbBtn);
        youtubeBtn = findViewById(R.id.youtubeBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        shareDialog = new ShareDialog(this);
        Twitter.initialize(this);

        homeBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        tweetBtn.setOnClickListener(this);
        fbBtn.setOnClickListener(this);
        youtubeBtn.setOnClickListener(this);

        String uid = firebaseAuth.getCurrentUser().getUid();
        discogsDB = FirebaseDatabase.getInstance().getReference(uid).child("albums");

        getData();
        getDetailsFromApi();
        checkSaved();
    }

    private void getData() {

        Bundle bundle = getIntent().getExtras();
        final AlbumList albumList = bundle.getParcelable("Album details");

        String img = albumList.getCoverImage();
        Picasso.get().load(img).into(albumImg);
        albumCover = albumList.getCoverImage();
        name = albumList.getTitle();
        ID = albumList.getId();
    }

    private void getDetailsFromApi() {

        Bundle bundle = getIntent().getExtras();
        final AlbumList albumList = bundle.getParcelable("Album details");

        int albumID = albumList.getId();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(api_url).addConverterFactory
                (GsonConverterFactory.create());
        final Retrofit retrofit = builder.client(httpClient.build()).build();
        AlbumClient client = retrofit.create(AlbumClient.class);
        Call<Album> call = client.getAlbum(albumID);

        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {

                Album album = response.body();
                List<Tracklist> tracklists = response.body().getTracklist();

                String genres = album.getStyles().toString();

                albumTitle.setText(album.getTitle());
                albumArtist.setText(album.getArtistsSort());
                albumGenre.setText(genres);
                uri_url = album.getUri();

                if (tracklists != null) {

                    tracklistAdapter = new TracklistAdapter(AlbumDetails.this, tracklists);
                    trackList.setAdapter(tracklistAdapter);
                } else {

                    trackListTitle.setText("");
                }
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {

                Log.e(TAG, "Fail: " + t.getMessage());
            }
        });
    }

    private void checkSaved() {

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        boolean isSaved = prefs.getBoolean("" + ID + userID, false);

        if (isSaved) {

            saveBtn.setColorNormalResId(R.color.colorAccent);
            saveBtn.setLabelText("Remove album");
            saveBtn.setImageResource(R.mipmap.remove);
        }
    }

    private void saveAlbum() {

        AlbumList saveAlbum = new AlbumList(albumCover, name, ID);
        discogsDB.child(name + " " + ID).setValue(saveAlbum);

        SharedPreferences.Editor editor = getSharedPreferences(PREFS, MODE_PRIVATE).edit();
        editor.putBoolean("" + ID + userID, true);
        editor.apply();

        finish();
        startActivity(getIntent());

        Toast.makeText(this, "Saved " + name, Toast.LENGTH_LONG).show();
    }

    private void deleteAlbum() {

        discogsDB.child(name + " " + ID).removeValue();

        SharedPreferences.Editor editor = getSharedPreferences(PREFS, MODE_PRIVATE).edit();
        editor.remove("" + ID + userID);
        editor.apply();

        finish();
        startActivity(getIntent());

        Toast.makeText(this, "Removed " + name, Toast.LENGTH_LONG).show();
    }

    private void sendTweet() {

        try {

            URL url = new URL(uri_url);

            TweetComposer.Builder tweet = new TweetComposer.Builder(this)
                    .text("I'm listening to " + albumTitle.getText().toString().trim() +
                            " by " + albumArtist.getText().toString().trim() + "!").url(url);
            tweet.show();
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }
    }

    private void postToFB() {

        if (ShareDialog.canShow(ShareLinkContent.class)) {

            ShareLinkContent fbPost = new ShareLinkContent.Builder().setContentUrl(Uri.parse(uri_url)).build();

            shareDialog.show(fbPost);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == homeBtn) {

            finish();
            Intent i = new Intent(AlbumDetails.this, MenuPage.class);
            startActivity(i);
        }

        if (v == saveBtn) {

            if (saveBtn.getLabelText().contentEquals("Remove album")) {

                deleteAlbum();
            } else {

                saveAlbum();
            }
        }

        if (v == tweetBtn) {

            sendTweet();
        }

        if (v == fbBtn) {

            postToFB();
        }

        if (v == youtubeBtn) {

            Intent i = new Intent(AlbumDetails.this, VideoList.class);
            i.putExtra("albumID", ID);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
