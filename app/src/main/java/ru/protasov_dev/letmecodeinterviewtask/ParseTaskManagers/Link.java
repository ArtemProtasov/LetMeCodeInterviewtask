
package ru.protasov_dev.letmecodeinterviewtask.ParseTaskManagers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Link {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("suggested_link_text")
    @Expose
    private String suggestedLinkText;

    public String getUrl() {
        return url;
    }

    public String getSuggestedLinkText() {
        return suggestedLinkText;
    }
}
