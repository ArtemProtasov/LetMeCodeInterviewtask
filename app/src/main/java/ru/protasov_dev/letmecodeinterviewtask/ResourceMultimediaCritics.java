package ru.protasov_dev.letmecodeinterviewtask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResourceMultimediaCritics {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("credit")
    @Expose
    private String credit;

    public String getSrc() {
        return src;
    }
}
