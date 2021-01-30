package com.zooverse.activities.adapters;

import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Theme;
import com.zooverse.activities.SpeciesCatalogueActivity;
import com.zooverse.activities.SpeciesLocationActivity;
import com.zooverse.model.Group;
import com.zooverse.model.Model;
import com.zooverse.model.Species;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SpeciesCatalogueAdapter extends RecyclerView.Adapter<SpeciesCatalogueAdapter.SpeciesCatalogueViewHolder> {
	private final int MAX_MEASURED_DISTANCE = 999999;
	private final List<Species> fullSpeciesList = Model.getSortedSpeciesList();
	private final int catalogueMode;
	private List<Species> filteredSpeciesList = fullSpeciesList;
	private List<Entry<Species, Integer>> speciesWithDistanceSorted;
	
	private final SpeciesOnClickListener speciesOnClickListener;
	
	public SpeciesCatalogueAdapter(SpeciesOnClickListener speciesOnClickListener, int catalogueMode) {
		this.speciesOnClickListener = speciesOnClickListener;
		this.catalogueMode = catalogueMode;
		if (this.catalogueMode == SpeciesCatalogueActivity.CATALOGUE_MODE_WITH_LOCATION)
			buildLocationBasedList(null);
	}
	
	// inner class for view holder
	public static class SpeciesCatalogueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private final TextView speciesNameTextView = itemView.findViewById(R.id.speciesNameTextView);
		private final TextView speciesDistanceTextView = itemView.findViewById(R.id.speciesDistanceTextView);
		private final ImageView speciesImageView = itemView.findViewById(R.id.speciesImageView);
		private final ImageView speciesLocationImageView = itemView.findViewById(R.id.speciesLocationIcon);
		private final SpeciesOnClickListener speciesOnClickListener;
		
		public SpeciesCatalogueViewHolder(@NonNull View itemView, SpeciesOnClickListener speciesOnClickListener, int catalogueMode) {
			super(itemView);
			this.speciesLocationImageView.setImageDrawable(MainApplication.getContext().getDrawable(R.drawable.icon_location));
			this.speciesOnClickListener = speciesOnClickListener;
			itemView.setOnClickListener(this);
			
			if (catalogueMode == SpeciesCatalogueActivity.CATALOGUE_MODE_WITHOUT_LOCATION) {
				speciesDistanceTextView.setVisibility(View.GONE);
				speciesLocationImageView.setVisibility(View.GONE);
			}
		}
		
		@Override //on click method references interface for on click implementation
		public void onClick(View v) {
			speciesOnClickListener.onSpeciesClick(getAdapterPosition());
		}
	}
	
	@NonNull
	@Override
	public SpeciesCatalogueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// assign layout file which is used for each row in recycler view
		View speciesListLayout = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.layout_recyclerview_species, parent, false);
		return new SpeciesCatalogueViewHolder(speciesListLayout, speciesOnClickListener, catalogueMode);
	}
	
	@Override
	public void onBindViewHolder(@NonNull SpeciesCatalogueViewHolder viewHolder, int position) {
		if (this.catalogueMode == SpeciesCatalogueActivity.CATALOGUE_MODE_WITHOUT_LOCATION) {
			viewHolder.speciesNameTextView.setText(this.filteredSpeciesList.get(position).getName());
			viewHolder.speciesImageView.setImageBitmap(this.filteredSpeciesList.get(position).getImage());
		} else {
			viewHolder.speciesNameTextView.setText(this.speciesWithDistanceSorted.get(position).getKey().getName());
			int distance = this.speciesWithDistanceSorted.get(position).getValue();
			if (distance < MAX_MEASURED_DISTANCE) {
				viewHolder.speciesLocationImageView.setOnClickListener(view -> {
					Intent intent = new Intent(view.getContext(), SpeciesLocationActivity.class);
					intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, this.speciesWithDistanceSorted.get(position).getKey().getId());
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					view.getContext().startActivity(intent);
				});
				// check if kilometer or meter
				if (distance > 999)
					viewHolder.speciesDistanceTextView.setText(distance/1000 + MainApplication.getContext().getString(R.string.kilometer_abbreviation));
				else
					viewHolder.speciesDistanceTextView.setText(distance + MainApplication.getContext().getString(R.string.meter_abbreviation));
				
				viewHolder.speciesLocationImageView.setColorFilter(Theme.getColor(R.attr.themeColorForeground));
			} else {
				viewHolder.speciesDistanceTextView.setText(MainApplication.getContext().getString(R.string.no_location));
				viewHolder.speciesLocationImageView.setColorFilter(Theme.getColor(R.attr.themeColorForegroundFaded));
			}
			viewHolder.speciesImageView.setImageBitmap(this.speciesWithDistanceSorted.get(position).getKey().getImage());
		}
	}
	
	@Override
	public int getItemCount() {
		return this.filteredSpeciesList.size();
	}
	
	public Species getSelectedSpecies(int position) {
		if (catalogueMode == SpeciesCatalogueActivity.CATALOGUE_MODE_WITHOUT_LOCATION)
			return this.filteredSpeciesList.get(position);
		else
			return this.speciesWithDistanceSorted.get(position).getKey();
	}
	
	public void updateCursor(String searchCriterion) {
		this.filteredSpeciesList = new ArrayList<>();
		for (Species species : this.fullSpeciesList) {
			if (species.getName().toLowerCase().startsWith(searchCriterion.toLowerCase())) {
				this.filteredSpeciesList.add(species);
			}
		}
	}
	
	public void updateCursor(Group group) {
		this.filteredSpeciesList = group.getSpecies();
	}
	
	public void updateCursor(Location userLocation) {
		buildLocationBasedList(userLocation);
	}
	
	private void buildLocationBasedList(Location location) {
		Map<Species, Integer> speciesWithDistance = new HashMap<>();
		for (Species species : fullSpeciesList) {
			Location speciesLocation = new Location("");
			int distance = MAX_MEASURED_DISTANCE;
			if (species.getLocation() != null && location != null) {
				speciesLocation.setLatitude(species.getLocation().first);
				speciesLocation.setLongitude(species.getLocation().second);
				distance = (int) location.distanceTo(speciesLocation);
			}
			speciesWithDistance.put(species, distance);
		}
		this.speciesWithDistanceSorted = new ArrayList<>(speciesWithDistance.entrySet());
		this.speciesWithDistanceSorted.sort(Entry.comparingByValue());
	}
		
		public interface SpeciesOnClickListener {
		void onSpeciesClick(int position);
	}
	
}
