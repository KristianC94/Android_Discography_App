package mobwebtech2.com.bcu.kristian.discogsapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtistList implements Parcelable {

    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("cover_image")
    @Expose
    private String coverImage;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private int id;

    public ArtistList() {}

    public ArtistList(String title, int id, String coverImage) {

        this.title = title;
        this.id = id;
        this.coverImage = coverImage;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArtistList(Parcel in) {

        int[] data = new int[1];
        String[] data2 = new String[2];

        in.readIntArray(data);
        this.id = data[0];

        in.readStringArray(data2);
        this.coverImage = data2[0];
        this.title = data2[1];
    }

    public int describeContents() {

        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeIntArray(new int[] {this.id
        });

        dest.writeStringArray(new String[] {this.coverImage, this.title
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ArtistList createFromParcel(Parcel in) {
            return new ArtistList(in);
        }

        public ArtistList[] newArray(int size) {
            return new ArtistList[size];
        }
    };
}
