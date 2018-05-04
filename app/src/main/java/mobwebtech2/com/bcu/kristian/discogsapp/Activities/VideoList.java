package mobwebtech2.com.bcu.kristian.discogsapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import mobwebtech2.com.bcu.kristian.discogsapp.Adapters.VideoListAdapter;
import mobwebtech2.com.bcu.kristian.discogsapp.Interfaces.AlbumClient;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.Album;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.Video;
import mobwebtech2.com.bcu.kristian.discogsapp.R;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoList extends AppCompatActivity {

    private ListView videoList;
    private TextView videoListText;
    private VideoListAdapter videoListAdapter;
    private static final String api_url = "https://api.discogs.com";
    private static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list);

        videoList = findViewById(R.id.videoTitlesList);
        videoListText = findViewById(R.id.videoListTitle);

        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Video video = (Video) parent.getItemAtPosition(position);
                Intent i = new Intent(VideoList.this, VideoView.class);
                i.putExtra("video details", video);
                startActivity(i);
            }
        });

        getListOfVideos();
    }

    private void getListOfVideos() {

        int albumID = getIntent().getIntExtra("albumID", 0);

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
                List<Video> videos = response.body().getVideos();

                if (videos != null) {

                    videoListText.setText("Music videos from " + album.getTitle() + " by "
                            + album.getArtistsSort());
                    videoListAdapter = new VideoListAdapter(VideoList.this, videos);
                    videoList.setAdapter(videoListAdapter);
                } else {

                    videoListText.setText("No music videos from " + album.getTitle() + " by "
                            + album.getArtistsSort());
                }
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {

                Log.e(TAG, "Fail: " + t.getMessage());
            }
        });
    }
}
