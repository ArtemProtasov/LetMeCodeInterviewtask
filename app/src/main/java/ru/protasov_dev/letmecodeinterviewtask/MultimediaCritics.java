package ru.protasov_dev.letmecodeinterviewtask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MultimediaCritics {

    @SerializedName("resource")
    @Expose
    private ResourceMultimediaCritics resource;

    public ResourceMultimediaCritics getResource() {
        return resource;
    }
}
