package mobwebtech2.com.bcu.kristian.discogsapp.Interfaces;

import mobwebtech2.com.bcu.kristian.discogsapp.Models.Artist;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArtistClient {

    @GET("/artists/{id}")
    Call<Artist> getArtist(@Path("id") int artistID);
}
