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

import mobwebtech2.com.bcu.kristian.discogsapp.Adapters.ArtistAdapter;
import mobwebtech2.com.bcu.kristian.discogsapp.Interfaces.ArtistListClient;
import mobwebtech2.com.bcu.kristian.discogsapp.R;
import mobwebtech2.com.bcu.kristian.discogsapp.Models.ArtistList;
import mobwebtech2.com.bcu.kristian.discogsapp.Responses.JSONResponseArtist;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistSearch extends Fragment implements View.OnClickListener {

    private EditText searchTxt;
    private Button searchBtn;
    private ListView artistList;
    private ArtistAdapter artistAdapter;
    private static final String api_url = "https://api.discogs.com";
    private static final String TAG = "debug";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_search, container, false);

        searchTxt = rootView.findViewById(R.id.artistSearch);
        searchBtn = rootView.findViewById(R.id.artistSearchBtn);
        artistList = rootView.findViewById(R.id.artistList);

        searchBtn.setOnClickListener(this);

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

        String searchTerm = "Michael Jackson";
        String searchCat = "artist";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(api_url).addConverterFactory
                (GsonConverterFactory.create());
        final Retrofit retrofit = builder.client(httpClient.build()).build();
        ArtistListClient client = retrofit.create(ArtistListClient.class);
        Call<JSONResponseArtist> call = client.getArtistList(searchTerm, searchCat);

        call.enqueue(new Callback<JSONResponseArtist>() {
            @Override
            public void onResponse(Call<JSONResponseArtist> call, Response<JSONResponseArtist> response) {

                List<ArtistList> artists = response.body().getArtists();

                Log.d(TAG, "" + response);
                Log.d(TAG, "Artists found");
                Log.d(TAG, "Number of artists:" + artists.size());

                artistAdapter = new ArtistAdapter(getContext(), artists);
                artistList.setAdapter(artistAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponseArtist> call, Throwable t) {

                Log.e(TAG, "Fail: " + t.getMessage());
            }
        });
    }

    public void searchArtists() {

        String searchTerm = searchTxt.getText().toString().trim();
        String searchCat = "artist";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(api_url).addConverterFactory
                (GsonConverterFactory.create());
        final Retrofit retrofit = builder.client(httpClient.build()).build();
        ArtistListClient client = retrofit.create(ArtistListClient.class);
        Call<JSONResponseArtist> call = client.getArtistList(searchTerm, searchCat);

        call.enqueue(new Callback<JSONResponseArtist>() {
            @Override
            public void onResponse(Call<JSONResponseArtist> call, Response<JSONResponseArtist> response) {

                List<ArtistList> artists = response.body().getArtists();

                Log.d(TAG, "" + response);
                Log.d(TAG, "Artists found");
                Log.d(TAG, "Number of artists:" + artists.size());

                artistAdapter = new ArtistAdapter(getContext(), artists);
                artistList.setAdapter(artistAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponseArtist> call, Throwable t) {

                Log.e(TAG, "Fail: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == searchBtn) {

            searchArtists();
        }
    }
}
