package mobwebtech2.com.bcu.kristian.discogsapp.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topalbums {

    @SerializedName("album")
    @Expose
    private List<LastFmAlbum> album = null;

    public List<LastFmAlbum> getAlbum() {
        return album;
    }

    public void setAlbum(List<LastFmAlbum> album) {
        this.album = album;
    }
}
