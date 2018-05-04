package mobwebtech2.com.bcu.kristian.discogsapp.Interfaces;

import mobwebtech2.com.bcu.kristian.discogsapp.Responses.JSONResponseLastFM;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastFMClient {

    @GET("/2.0/")
    Call<JSONResponseLastFM> getTopalbums (@Query("method") String artistGetTopAlbums,
                                           @Query("artist") String artistName,
                                           @Query("api_key") String apiKey,
                                           @Query("format") String jsonFormat);
}
