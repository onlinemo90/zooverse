package com.zooverse.activities.adapters;

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
	private List<Species> filteredSpeciesList = Model.getSpeciesList();
	private final speciesOnClickListener speciesOnClickListener;
	
	public SpeciesSearchAdapter(speciesOnClickListener speciesOnClickListener) {
		this.speciesOnClickListener = speciesOnClickListener;
	}
	
	// inner class for view holder
	public static class SpeciesSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		TextView speciesNameTextView = itemView.findViewById(R.id.speciesNameTextView);
		ImageView speciesImageView = itemView.findViewById(R.id.speciesImageView);
		speciesOnClickListener speciesOnClickListener;
		
		public SpeciesSearchViewHolder(@NonNull View itemView, speciesOnClickListener speciesOnClickListener) {
			super(itemView);
			this.speciesOnClickListener = speciesOnClickListener;
			itemView.setOnClickListener(this);
		}
		
		@Override //on click method references interface for on click implementation
		public void onClick(View v) {
			speciesOnClickListener.onSpeciesClick(getAdapterPosition());
		}
	}
	
	@NonNull
	@Override
	public SpeciesSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// assign layout file which is used for each row in recycler view
		View speciesListLayout = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.layout_recyclerview_species, parent, false);
		return new SpeciesSearchViewHolder(speciesListLayout, speciesOnClickListener);
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
	
	public Species getSelectedSpecies(int position) {
		return this.filteredSpeciesList.get(position);
	}
	
	public void updateCursor(String searchCriterion) {
		this.filteredSpeciesList = new ArrayList<>();
		for (Species species : Model.getSpeciesList()) {
			if (species.getName().toLowerCase().startsWith(searchCriterion.toLowerCase())) {
				this.filteredSpeciesList.add(species);
			}
		}
	}
	
	public interface speciesOnClickListener {
		void onSpeciesClick(int position);
	}
	
	
}
