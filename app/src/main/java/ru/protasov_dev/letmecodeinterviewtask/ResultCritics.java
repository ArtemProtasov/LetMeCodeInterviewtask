
package ru.protasov_dev.letmecodeinterviewtask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultCritics {

    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("sort_name")
    @Expose
    private String sortName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("seo_name")
    @Expose
    private String seoName;
    @SerializedName("multimedia")
    @Expose
    private MultimediaCritics multimedia;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSeoName() {
        return seoName;
    }

    public void setSeoName(String seoName) {
        this.seoName = seoName;
    }

    public MultimediaCritics getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(MultimediaCritics multimedia) {
        this.multimedia = multimedia;
    }

}
