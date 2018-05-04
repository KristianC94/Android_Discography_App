package mobwebtech2.com.bcu.kristian.discogsapp.Interfaces;

import mobwebtech2.com.bcu.kristian.discogsapp.Responses.JSONResponseAlbum;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface AlbumListClient {

    @Headers("Authorization: Discogs key=wMWTrOWaTkfHDUQVXFSG, secret=AEmHdfwlGwPqUYQpTBVrarEtzjKsykih")

    @GET("/database/search")
    Call<JSONResponseAlbum> getAlbumList (@Query("q") String searchTerm,
                                          @Query("type") String searchCat);
}
