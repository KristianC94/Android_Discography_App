package mobwebtech2.com.bcu.kristian.discogsapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import mobwebtech2.com.bcu.kristian.discogsapp.Adapters.MemberAdapter;
import mobwebtech2.com.bcu.kristian.discogsapp.Interfaces.ArtistClient;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.Artist;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.Member;
import mobwebtech2.com.bcu.kristian.discogsapp.R;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.ArtistList;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistDetails extends AppCompatActivity implements View.OnClickListener {

    private ImageView artistImg;
    private TextView artistName, artistRName, artistProfile, profileTitle, memberTitle;
    private ListView memberList;
    private MemberAdapter memberAdapter;
    private FloatingActionButton saveBtn, tweetBtn, fbBtn, releasesBtn, homeBtn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference discogsDB;
    private int ID = 0;
    private String artistPic = "";
    private String name = "";
    private String uri_url = "";
    private ShareDialog shareDialog;
    private static final String api_url = "https://api.discogs.com";
    private static final String TAG = "debug";
    private static final String PREFS = "Prefs";
    private static final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_details);

        artistImg = findViewById(R.id.artistImg);
        artistName = findViewById(R.id.artistName);
        artistRName = findViewById(R.id.artistRealName);
        artistProfile = findViewById(R.id.profileTxt);
        profileTitle = findViewById(R.id.profileTitle);
        memberList = findViewById(R.id.memberList);
        memberTitle = findViewById(R.id.membersTitle);
        saveBtn = findViewById(R.id.artistSaveBtn);
        tweetBtn = findViewById(R.id.artistTweetBtn);
        fbBtn = findViewById(R.id.artistFbBtn);
        releasesBtn = findViewById(R.id.seeAlbumsBtn);
        homeBtn = findViewById(R.id.artistHomeBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        shareDialog = new ShareDialog(this);
        Twitter.initialize(this);

        saveBtn.setOnClickListener(this);
        tweetBtn.setOnClickListener(this);
        fbBtn.setOnClickListener(this);
        releasesBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        artistProfile.setMovementMethod(new ScrollingMovementMethod());

        String uid = firebaseAuth.getCurrentUser().getUid();
        discogsDB = FirebaseDatabase.getInstance().getReference(uid).child("artists");

        getData();
        getDetailsFromApi();
        checkSaved();
    }

    private void getData() {

        Bundle bundle = getIntent().getExtras();
        final ArtistList artistList = bundle.getParcelable("Artist details");

        ID = artistList.getId();
        name = artistList.getTitle();

        if (artistList.getCoverImage().equals("https://img.discogs.com/images/spacer.gif")) {

            Log.d(TAG, "No image");
        } else {

            Picasso.get().load(artistList.getCoverImage()).into(artistImg);
            artistPic = artistList.getCoverImage();
        }
    }

    private void getDetailsFromApi() {

        Bundle bundle = getIntent().getExtras();
        final ArtistList artistList = bundle.getParcelable("Artist details");

        final int artistID = artistList.getId();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(api_url).addConverterFactory
                (GsonConverterFactory.create());
        final Retrofit retrofit = builder.client(httpClient.build()).build();
        ArtistClient client = retrofit.create(ArtistClient.class);
        Call<Artist> call = client.getArtist(artistID);

        call.enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {

                Artist artist = response.body();
                List<Member> members = response.body().getMembers();

                artistName.setText(artist.getName());
                artistProfile.setText(artist.getProfile());
                uri_url = artist.getUri();

                if (artist.getRealName() != null) {

                    artistRName.setText(artist.getRealName());
                }

                if (artist.getProfile().equals("")) {

                    profileTitle.setText("");
                }

                if (members != null) {

                    memberAdapter = new MemberAdapter(ArtistDetails.this, members);
                    memberList.setAdapter(memberAdapter);
                } else {

                    memberTitle.setText("");
                }
            }

            @Override
            public void onFailure(Call<Artist> call, Throwable t) {

                Log.e(TAG, "Fail: " + t.getMessage());
            }
        });
    }

    private void checkSaved() {

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        boolean isSaved = prefs.getBoolean("" + ID + userID, false);

        if (isSaved) {

            saveBtn.setColorNormalResId(R.color.colorAccent);
            saveBtn.setLabelText("Remove artist");
            saveBtn.setImageResource(R.mipmap.remove);
        }
    }

    private void saveArtist() {

        ArtistList saveArtist = new ArtistList(name, ID, artistPic);
        discogsDB.child(name + " " + ID).setValue(saveArtist);

        SharedPreferences.Editor editor = getSharedPreferences(PREFS, MODE_PRIVATE).edit();
        editor.putBoolean("" + ID + userID, true);
        editor.apply();

        finish();
        startActivity(getIntent());

        Toast.makeText(this, "Saved " + name, Toast.LENGTH_LONG).show();
    }

    private void deleteArtist() {

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
                    .text("I'm really enjoying " + name + "'s music!").url(url);
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
            Intent i = new Intent(ArtistDetails.this, MenuPage.class);
            startActivity(i);
        }

        if (v == saveBtn) {

            if (saveBtn.getLabelText().contentEquals("Remove artist")) {

                deleteArtist();
            } else {

                saveArtist();
            }
        }

        if (v == releasesBtn) {


            Intent i = new Intent(ArtistDetails.this, TopAlbumsList.class);
            i.putExtra("Artist name", name);
            startActivity(i);
        }

        if (v == tweetBtn) {

            sendTweet();
        }

        if (v == fbBtn) {

            postToFB();
        }
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
