package ru.protasov_dev.letmecodeinterviewtask.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.protasov_dev.letmecodeinterviewtask.CriticPage;
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

            imgCritics.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String name = txtNameCritics.getText().toString();
            Intent startCriticPage = new Intent(view.getContext(), CriticPage.class);
            startCriticPage.putExtra("URL", createURL(name));
            startCriticPage.putExtra("NAME", name);
            view.getContext().startActivity(startCriticPage);
        }

        private String createURL(String name){
            String url = "https://api.nytimes.com/svc/movies/v2/critics/";
            url += name.replace(" ", "%20") + ".json?api-key=020eb74eff674e3da8aaa1e8e311edda";
            return url;
        }
    }
}
