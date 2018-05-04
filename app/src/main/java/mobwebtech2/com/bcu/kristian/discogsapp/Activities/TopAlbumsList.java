package mobwebtech2.com.bcu.kristian.discogsapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import mobwebtech2.com.bcu.kristian.discogsapp.Adapters.TopAlbumAdapter;
import mobwebtech2.com.bcu.kristian.discogsapp.Interfaces.LastFMClient;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.LastFmAlbum;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.LastFmImage;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.Topalbums;
import mobwebtech2.com.bcu.kristian.discogsapp.R;
import mobwebtech2.com.bcu.kristian.discogsapp.Responses.JSONResponseLastFM;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopAlbumsList extends AppCompatActivity {

    private ListView topAlbumsList;
    private TopAlbumAdapter topAlbumAdapter;
    private static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_albums);

        topAlbumsList = findViewById(R.id.topAlbumList);

        topAlbumsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LastFmAlbum album = (LastFmAlbum) parent.getItemAtPosition(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(album.getUrl()));
                startActivity(i);
            }
        });

        fillList();
    }

    private void fillList() {

        String api_url = "http://ws.audioscrobbler.com";
        String method = "artist.gettopalbums";
        String artist = getIntent().getStringExtra("Artist name");
        String api_key = "00629b7f0a85d0d96a390b0fc788e930";
        String format = "json";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(api_url).addConverterFactory
                (GsonConverterFactory.create());
        final Retrofit retrofit = builder.client(httpClient.build()).build();
        LastFMClient client = retrofit.create(LastFMClient.class);
        Call<JSONResponseLastFM> call = client.getTopalbums(method, artist, api_key, format);

        call.enqueue(new Callback<JSONResponseLastFM>() {
            @Override
            public void onResponse(Call<JSONResponseLastFM> call, Response<JSONResponseLastFM> response) {

                Topalbums albums = response.body().getTopalbums();
                List<LastFmAlbum> topAlbums = albums.getAlbum();

                Log.d(TAG, "" + response);
                Log.d(TAG, "Albums found");
                Log.d(TAG, "Number of albums:" + topAlbums.size());

                topAlbumAdapter = new TopAlbumAdapter(TopAlbumsList.this, topAlbums);
                topAlbumsList.setAdapter(topAlbumAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponseLastFM> call, Throwable t) {

                Log.e(TAG, "Fail: " + t.getMessage());
            }
        });
    }
}
