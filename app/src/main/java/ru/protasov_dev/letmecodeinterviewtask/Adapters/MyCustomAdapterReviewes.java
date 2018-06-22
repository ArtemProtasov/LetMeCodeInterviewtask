package ru.protasov_dev.letmecodeinterviewtask.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.protasov_dev.letmecodeinterviewtask.R;
import ru.protasov_dev.letmecodeinterviewtask.ReviewesElement;

public class MyCustomAdapterReviewes extends RecyclerView.Adapter<MyCustomAdapterReviewes.ViewHolder> {
    private List<ReviewesElement> list;

    public MyCustomAdapterReviewes(List<ReviewesElement> list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_reviewes, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReviewesElement reviewesElement = list.get(position);
        Glide.with(reviewesElement.getContext())
                .load(reviewesElement.getUrlImg())
                .into(holder.imgReviewes);
        //holder.imgReviewes.setImageBitmap(reviewesElement.getImg());
        holder.txtTitleReviewes.setText(reviewesElement.getTitle());
        holder.txtSummaryShortReviewes.setText(reviewesElement.getSummaryShort());
        holder.txtDateReviewes.setText(reviewesElement.getDate());
        holder.txtByline.setText(reviewesElement.getByline());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgReviewes;
        TextView txtTitleReviewes;
        TextView txtSummaryShortReviewes;
        TextView txtDateReviewes;
        TextView txtByline;

        public ViewHolder(View itemView) {
            super(itemView);

            imgReviewes = itemView.findViewById(R.id.img_reviewes);
            txtTitleReviewes = itemView.findViewById(R.id.txt_title_reviewes);
            txtSummaryShortReviewes = itemView.findViewById(R.id.txt_summary_short_reviewes);
            txtDateReviewes = itemView.findViewById(R.id.txt_date_reviewes);
            txtByline = itemView.findViewById(R.id.txt_byline);
        }
    }
}
