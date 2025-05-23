package com.example.esn_steria_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Entites.Intervention;

public class InterventionAdapter extends RecyclerView.Adapter<InterventionAdapter.ViewHolder> implements Filterable {

    private List<Intervention> interventionsList;
    private List<Intervention> interventionsListFull; // liste complète
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Intervention intervention);
    }

    public InterventionAdapter(List<Intervention> interventionsList, OnItemClickListener listener) {
        this.interventionsList = interventionsList;
        this.interventionsListFull = new ArrayList<>(interventionsList); // Copie pour le filtre
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Intervention intervention = interventionsList.get(position);
        holder.text1.setText(intervention.getIntitule());
        holder.text2.setText(intervention.getEtat());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(intervention);
        });
    }

    @Override
    public int getItemCount() {
        return interventionsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;

        ViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }

    @Override
    public Filter getFilter() {
        return interventionFilter;
    }

    private final Filter interventionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Intervention> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(interventionsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Intervention intervention : interventionsListFull) {
                    if (intervention.getIntitule().toLowerCase().contains(filterPattern) ||
                            intervention.getEtat().toLowerCase().contains(filterPattern)) {
                        filteredList.add(intervention);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            interventionsList.clear();
            interventionsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
