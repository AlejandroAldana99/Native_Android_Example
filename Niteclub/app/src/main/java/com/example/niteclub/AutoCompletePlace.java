package com.example.niteclub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AutoCompletePlace extends ArrayAdapter<PlaceItem> {
    private List<PlaceItem> placeListFull;

    public AutoCompletePlace(@NonNull Context context, @NonNull List<PlaceItem> placeList) {
        super(context, 0, placeList);
        placeListFull = new ArrayList<>(placeList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return placeFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.place_autocomplete_row, parent, false
            );
        }
        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        PlaceItem placeItem = getItem(position);

        if (placeItem != null){
            textViewName.setText(placeItem.getP_Name());
        }

        return convertView;
    }

    private Filter placeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<PlaceItem> suggestion = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                suggestion.addAll(placeListFull);
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (PlaceItem item : placeListFull) {
                    if (item.getP_Name().toLowerCase().contains(filterPattern)){
                        suggestion.add(item);
                    }
                }
            }
            results.values = suggestion;
            results.count = suggestion.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((PlaceItem) resultValue).getP_Name();
        }
    };
}
