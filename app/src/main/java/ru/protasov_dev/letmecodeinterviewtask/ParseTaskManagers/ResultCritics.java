
package ru.protasov_dev.letmecodeinterviewtask.ParseTaskManagers;

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

    public String getStatus() {
        return status;
    }

    public String getBio() {
        return bio;
    }

    public MultimediaCritics getMultimedia() {
        return multimedia;
    }
}
