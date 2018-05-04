package mobwebtech2.com.bcu.kristian.discogsapp.Responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import mobwebtech2.com.bcu.kristian.discogsapp.Models.ArtistList;

public class JSONResponseArtist {

    @SerializedName("results")
    @Expose
    private List<ArtistList> artists = null;

    public List<ArtistList> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistList> artists) {
        this.artists = artists;
    }
}
