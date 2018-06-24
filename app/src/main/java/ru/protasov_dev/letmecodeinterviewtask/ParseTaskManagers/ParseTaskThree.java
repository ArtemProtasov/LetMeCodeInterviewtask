
package ru.protasov_dev.letmecodeinterviewtask.ParseTaskManagers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParseTaskThree {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("num_results")
    @Expose
    private Integer numResults;
    @SerializedName("results")
    @Expose
    private List<ResultCritics> results = null;

    public List<ResultCritics> getResults() {
        return results;
    }
}
