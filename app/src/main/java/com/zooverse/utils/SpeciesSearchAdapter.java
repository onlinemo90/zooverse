package com.zooverse.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.model.Model;
import com.zooverse.model.Species;

import java.util.ArrayList;
import java.util.List;

public class SpeciesSearchAdapter extends RecyclerView.Adapter<SpeciesSearchAdapter.SpeciesSearchViewHolder> {
	private List<Species> filteredSpeciesList = Model.getInstance().getSpeciesList();
	
	// inner class for view holder
	public static class SpeciesSearchViewHolder extends RecyclerView.ViewHolder {
		TextView speciesNameTextView = itemView.findViewById(R.id.speciesNameTextView);
		ImageView speciesImageView = itemView.findViewById(R.id.speciesImageView);
		
		public SpeciesSearchViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
	
	@NonNull
	@Override
	public SpeciesSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// assign layout file which is used for each row in recycler view
		View speciesListLayout = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.layout_recyclerview_species, parent, false);
		return new SpeciesSearchViewHolder(speciesListLayout);
	}
	
	@Override
	public void onBindViewHolder(@NonNull SpeciesSearchViewHolder viewHolder, int position) {
		viewHolder.speciesNameTextView.setText(this.filteredSpeciesList.get(position).getName());
		if (this.filteredSpeciesList.get(position).getImage() != null) {
			viewHolder.speciesImageView.setImageBitmap(this.filteredSpeciesList.get(position).getImage());
		}
	}
	
	@Override
	public int getItemCount() {
		return this.filteredSpeciesList.size();
	}
	
	public void updateCursor(String searchCriterion) {
		this.filteredSpeciesList = new ArrayList<>();
		for (Species species : Model.getInstance().getSpeciesList()) {
			if (species.getName().toLowerCase().startsWith(searchCriterion.toLowerCase())) {
				this.filteredSpeciesList.add(species);
			}
		}
	}
}
