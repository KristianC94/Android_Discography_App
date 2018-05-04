package mobwebtech2.com.bcu.kristian.discogsapp.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import mobwebtech2.com.bcu.kristian.discogsapp.Models.AlbumList;

public class JSONResponseAlbum {

    @SerializedName("results")
    @Expose
    private List<AlbumList> albums = null;

    public List<AlbumList> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumList> albums) {
        this.albums = albums;
    }
}
