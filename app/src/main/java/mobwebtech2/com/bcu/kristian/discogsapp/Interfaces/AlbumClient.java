package mobwebtech2.com.bcu.kristian.discogsapp.Interfaces;

import mobwebtech2.com.bcu.kristian.discogsapp.Models.Album;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlbumClient {

    @GET("/releases/{id}")
    Call<Album> getAlbum(@Path("id") int albumID);
}
