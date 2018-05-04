package mobwebtech2.com.bcu.kristian.discogsapp.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import mobwebtech2.com.bcu.kristian.discogsapp.Models.Topalbums;

public class JSONResponseLastFM {

    @SerializedName("topalbums")
    @Expose
    private Topalbums topalbums;

    public Topalbums getTopalbums() {
        return topalbums;
    }

    public void setTopalbums(Topalbums topalbums) {
        this.topalbums = topalbums;
    }

}
