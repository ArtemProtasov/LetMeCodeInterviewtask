package ru.protasov_dev.letmecodeinterviewtask.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.protasov_dev.letmecodeinterviewtask.CriticsElement;
import ru.protasov_dev.letmecodeinterviewtask.R;

public class MyCustomAdapterCritics extends RecyclerView.Adapter<MyCustomAdapterCritics.ViewHolder> {
    private List<CriticsElement> list;

    public MyCustomAdapterCritics(List<CriticsElement> list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_critics, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CriticsElement criticsElement = list.get(position);
        holder.imgCritics.setImageBitmap(criticsElement.getImg());
        holder.txtNameCritics.setText(criticsElement.getName());
        holder.txtStatusCritics.setText(criticsElement.getStatus());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCritics;
        TextView txtNameCritics;
        TextView txtStatusCritics;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            imgCritics = itemView.findViewById(R.id.critic_photo);
            txtNameCritics = itemView.findViewById(R.id.critic_name);
            txtStatusCritics = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.card_view_critics);
        }
    }
}
