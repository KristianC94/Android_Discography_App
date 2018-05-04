package mobwebtech2.com.bcu.kristian.discogsapp.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album {

    @SerializedName("styles")
    @Expose
    private List<String> styles = null;
    @SerializedName("videos")
    @Expose
    private List<Video> videos = null;
    @SerializedName("series")
    @Expose
    private List<Object> series = null;
    @SerializedName("year")
    @Expose
    private int year;
    @SerializedName("format_quantity")
    @Expose
    private int formatQuantity;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("artists_sort")
    @Expose
    private String artistsSort;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("released")
    @Expose
    private String released;
    @SerializedName("tracklist")
    @Expose
    private List<Tracklist> tracklist = null;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("data_quality")
    @Expose
    private String dataQuality;
    private String albumCover;

    public Album() {}

    public Album(int id, String title, String albumCover) {
        this.id = id;
        this.title = title;
        this.albumCover = albumCover;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    public List<String> getStyles() {
        return styles;
    }

    public void setStyles(List<String> styles) {
        this.styles = styles;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Object> getSeries() {
        return series;
    }

    public void setSeries(List<Object> series) {
        this.series = series;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getFormatQuantity() {
        return formatQuantity;
    }

    public void setFormatQuantity(int formatQuantity) {
        this.formatQuantity = formatQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtistsSort() {
        return artistsSort;
    }

    public void setArtistsSort(String artistsSort) {
        this.artistsSort = artistsSort;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
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

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public List<Tracklist> getTracklist() {
        return tracklist;
    }

    public void setTracklist(List<Tracklist> tracklist) {
        this.tracklist = tracklist;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getDataQuality() {
        return dataQuality;
    }

    public void setDataQuality(String dataQuality) {
        this.dataQuality = dataQuality;
    }
}

