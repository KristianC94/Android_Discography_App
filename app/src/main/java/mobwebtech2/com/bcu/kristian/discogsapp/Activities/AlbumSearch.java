package mobwebtech2.com.bcu.kristian.discogsapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import mobwebtech2.com.bcu.kristian.discogsapp.Adapters.AlbumAdapter;
import mobwebtech2.com.bcu.kristian.discogsapp.Interfaces.AlbumListClient;
import mobwebtech2.com.bcu.kristian.discogsapp.R;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.AlbumList;
import mobwebtech2.com.bcu.kristian.discogsapp.Responses.JSONResponseAlbum;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlbumSearch extends Fragment implements View.OnClickListener {

    private EditText searchTxt;
    private Button searchBtn;
    private ListView albumList;
    private AlbumAdapter albumAdapter;
    private static final String api_url = "https://api.discogs.com";
    private static final String TAG = "debug";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.album_search, container, false);

        searchTxt = rootView.findViewById(R.id.albumSearch);
        searchBtn = rootView.findViewById(R.id.albumSearchBtn);
        albumList = rootView.findViewById(R.id.albumList);

        searchBtn.setOnClickListener(this);

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

        String searchTerm = "Michael Jackson Thriller";
        String searchCat = "release";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(api_url).addConverterFactory
                (GsonConverterFactory.create());
        final Retrofit retrofit = builder.client(httpClient.build()).build();
        AlbumListClient client = retrofit.create(AlbumListClient.class);
        Call<JSONResponseAlbum> call = client.getAlbumList(searchTerm, searchCat);

        call.enqueue(new Callback<JSONResponseAlbum>() {
            @Override
            public void onResponse(Call<JSONResponseAlbum> call, Response<JSONResponseAlbum> response) {

                List<AlbumList> albums = response.body().getAlbums();

                Log.d(TAG, "" + response);
                Log.d(TAG, "Albums found");
                Log.d(TAG, "Number of albums:" + albums.size());

                albumAdapter = new AlbumAdapter(getContext(), albums);
                albumList.setAdapter(albumAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponseAlbum> call, Throwable t) {

                Log.e(TAG, "Fail: " + t.getMessage());
            }
        });
    }

    public void searchAlbums() {

        String searchTerm = searchTxt.getText().toString().trim();
        String searchCat = "release";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(api_url).addConverterFactory
                (GsonConverterFactory.create());
        final Retrofit retrofit = builder.client(httpClient.build()).build();
        AlbumListClient client = retrofit.create(AlbumListClient.class);
        Call<JSONResponseAlbum> call = client.getAlbumList(searchTerm, searchCat);

        call.enqueue(new Callback<JSONResponseAlbum>() {
            @Override
            public void onResponse(Call<JSONResponseAlbum> call, Response<JSONResponseAlbum> response) {

                List<AlbumList> albums = response.body().getAlbums();

                Log.d(TAG, "" + response);
                Log.d(TAG, "Albums found");
                Log.d(TAG, "Number of albums:" + albums.size());

                albumAdapter = new AlbumAdapter(getContext(), albums);
                albumList.setAdapter(albumAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponseAlbum> call, Throwable t) {

                Log.e(TAG, "Fail: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == searchBtn) {

            searchAlbums();
        }
    }
}
