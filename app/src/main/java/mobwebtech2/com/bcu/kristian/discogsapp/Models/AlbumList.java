package mobwebtech2.com.bcu.kristian.discogsapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlbumList implements Parcelable {

    @SerializedName("style")
    @Expose
    private List<String> style = null;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("format")
    @Expose
    private List<String> format = null;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("barcode")
    @Expose
    private List<String> barcode = null;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("label")
    @Expose
    private List<String> label = null;
    @SerializedName("cover_image")
    @Expose
    private String coverImage;
    @SerializedName("catno")
    @Expose
    private String catno;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("genre")
    @Expose
    private List<String> genre = null;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private int id;

    public AlbumList() {}

    public AlbumList(String coverImage, String title, int id) {

        this.coverImage = coverImage;
        this.title = title;
        this.id = id;
    }

    public List<String> getStyle() {
        return style;
    }

    public void setStyle(List<String> style) {
        this.style = style;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public List<String> getFormat() {
        return format;
    }

    public void setFormat(List<String> format) {
        this.format = format;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getBarcode() {
        return barcode;
    }

    public void setBarcode(List<String> barcode) {
        this.barcode = barcode;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCatno() {
        return catno;
    }

    public void setCatno(String catno) {
        this.catno = catno;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    private AlbumList(Parcel in) {

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
        public AlbumList createFromParcel(Parcel in) {
            return new AlbumList(in);
        }

        public AlbumList[] newArray(int size) {
            return new AlbumList[size];
        }
    };
}
