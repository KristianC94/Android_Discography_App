package mobwebtech2.com.bcu.kristian.discogsapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video implements Parcelable {

    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("embed")
    @Expose
    private boolean embed;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("title")
    @Expose
    private String title;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEmbed() {
        return embed;
    }

    public void setEmbed(boolean embed) {
        this.embed = embed;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private Video(Parcel in) {

        String[] data = new String[2];

        in.readStringArray(data);
        this.uri = data[0];
        this.title = data[1];
    }

    public int describeContents() {

        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[] {this.uri, this.title
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
