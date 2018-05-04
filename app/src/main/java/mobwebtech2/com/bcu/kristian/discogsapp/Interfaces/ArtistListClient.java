package mobwebtech2.com.bcu.kristian.discogsapp.Interfaces;

import mobwebtech2.com.bcu.kristian.discogsapp.Responses.JSONResponseArtist;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ArtistListClient {

    @Headers("Authorization: Discogs key=wMWTrOWaTkfHDUQVXFSG, secret=AEmHdfwlGwPqUYQpTBVrarEtzjKsykih")

    @GET("/database/search")
    Call<JSONResponseArtist> getArtistList (@Query("q") String searchTerm,
                                            @Query("type") String searchCat);
}
