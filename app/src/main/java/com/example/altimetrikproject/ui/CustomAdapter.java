package com.example.altimetrikproject.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.altimetrikproject.R;

import com.example.altimetrikproject.interfaces.ListAdapterListener;
import com.example.altimetrikproject.pojo.Details;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends
        RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Filterable {

    private List<Details> details;
    private List<Details> listFiltered;
    private ListAdapterListener listener;

    public CustomAdapter(List<Details> details, ListAdapterListener listener) {

        this.details = details;
        this.listFiltered = details;
        this.listener = listener;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View contactView = inflater.inflate(R.layout.recycler_item, parent, false);


        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder viewHolder, int position) {
        final Details details = listFiltered.get(position);
        viewHolder.title.setText(details.getmTitle());
        viewHolder.blurpText.setText(details.getmBlurp());
        viewHolder.pleadgeText.setText(details.getmPleadge());
        viewHolder.backerText.setText(details.getmBy());

    }


    @Override
    public int getItemCount() {
        return listFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, blurpText, pleadgeText, backerText;

        public ViewHolder(View itemView) {

            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            blurpText = (TextView) itemView.findViewById(R.id.blurpText);
            pleadgeText = (TextView) itemView.findViewById(R.id.pleadgeText);
            backerText = (TextView) itemView.findViewById(R.id.backerText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onSelected(listFiltered.get(getAdapterPosition()));
                }
            });

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listFiltered = details;
                } else {
                    List<Details> filteredList = new ArrayList<>();
                    for (Details row : details) {

                        if (row.getmTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    listFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listFiltered = (ArrayList<Details>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
