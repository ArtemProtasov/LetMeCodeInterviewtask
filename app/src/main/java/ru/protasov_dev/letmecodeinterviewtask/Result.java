
package ru.protasov_dev.letmecodeinterviewtask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("display_title")
    @Expose
    private String displayTitle;
    @SerializedName("mpaa_rating")
    @Expose
    private String mpaaRating;
    @SerializedName("critics_pick")
    @Expose
    private Integer criticsPick;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("summary_short")
    @Expose
    private String summaryShort;
    @SerializedName("publication_date")
    @Expose
    private String publicationDate;
    @SerializedName("opening_date")
    @Expose
    private Object openingDate;
    @SerializedName("date_updated")
    @Expose
    private String dateUpdated;
    @SerializedName("link")
    @Expose
    private Link link;
    @SerializedName("multimedia")
    @Expose
    private Multimedia multimedia;

    public String getDisplayTitle() {
        return displayTitle;
    }

    public String getByline() {
        return byline;
    }

    public String getSummaryShort() {
        return summaryShort;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

}
