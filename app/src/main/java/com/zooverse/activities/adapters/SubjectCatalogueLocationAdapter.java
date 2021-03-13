package com.zooverse.activities.adapters;

import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Theme;
import com.zooverse.activities.SpeciesLocationActivity;
import com.zooverse.model.Group;
import com.zooverse.model.Species;
import com.zooverse.model.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SubjectCatalogueLocationAdapter extends AbstractSubjectCatalogueAdapter {
	private final int MAX_MEASURED_DISTANCE = 999999;
	private List<Entry<Species, Integer>> speciesWithDistanceSorted;
	
	
	public SubjectCatalogueLocationAdapter(SubjectOnClickListener subjectOnClickListener) {
		super(subjectOnClickListener);
		buildLocationBasedList(null);
	}
	
	// inner class for view holder
	public static class SubjectCatalogueLocationViewHolder extends AbstractSubjectCatalogueViewHolder {
		
		public SubjectCatalogueLocationViewHolder(@NonNull View itemView, SubjectOnClickListener subjectOnClickListener) {
			super(itemView, subjectOnClickListener);
			this.speciesLocationImageView.setVisibility(View.VISIBLE);
			this.speciesDistanceTextView.setVisibility(View.VISIBLE);
			this.speciesLocationImageView.setImageDrawable(MainApplication.getContext().getDrawable(R.drawable.icon_location));
		}
	}
	
	@NonNull
	@Override
	public SubjectCatalogueLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// assign layout file which is used for each row in recycler view
		View speciesListLayout = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.layout_recyclerview_subjects, parent, false);
		return new SubjectCatalogueLocationViewHolder(speciesListLayout, subjectOnClickListener);
	}
	
	@Override
	public void onBindViewHolder(@NonNull AbstractSubjectCatalogueViewHolder viewHolder, int position) {
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
				viewHolder.speciesDistanceTextView.setText(distance / 1000 + MainApplication.getContext().getString(R.string.kilometer_abbreviation));
			else
				viewHolder.speciesDistanceTextView.setText(distance + MainApplication.getContext().getString(R.string.meter_abbreviation));
			
			viewHolder.speciesLocationImageView.setColorFilter(Theme.getColor(R.attr.themeColorForeground));
		} else {
			viewHolder.speciesDistanceTextView.setText(MainApplication.getContext().getString(R.string.no_location));
			viewHolder.speciesLocationImageView.setColorFilter(Theme.getColor(R.attr.themeColorForegroundFaded));
		}
		viewHolder.speciesImageView.setImageBitmap(this.speciesWithDistanceSorted.get(position).getKey().getImage());
	}
	
	
	public Species getSelectedSubject(int position) { return this.speciesWithDistanceSorted.get(position).getKey(); }
	
	@Override
	public void updateCursor(String searchCriterion) { return; }
	
	@Override
	public void updateCursor(Location userLocation) {
		buildLocationBasedList(userLocation);
	}
	
	@Override
	public void updateCursor(Group group) { return; }
	
	@Override
	public ArrayList<Integer> getSubjectListInContext() {
		ArrayList<Integer> filterSpeciesListIntentExtra = new ArrayList<>();
		for (Entry<Species, Integer> species : speciesWithDistanceSorted) {
			filterSpeciesListIntentExtra.add(species.getKey().getId());
		}
		return filterSpeciesListIntentExtra;
	}
	
	private void buildLocationBasedList(Location location) {
		Map<Species, Integer> speciesWithDistance = new HashMap<>();
		for (Subject subject : fullSubjectList) {
			Location speciesLocation = new Location("");
			int distance = MAX_MEASURED_DISTANCE;
			if (subject instanceof Species) {
				Species species = (Species) subject;
				if (species.getLocation() != null && location != null) {
					speciesLocation.setLatitude(species.getLocation().first);
					speciesLocation.setLongitude(species.getLocation().second);
					distance = (int) location.distanceTo(speciesLocation);
				}
				speciesWithDistance.put(species, distance);
			}
		}
		this.speciesWithDistanceSorted = new ArrayList<>(speciesWithDistance.entrySet());
		this.speciesWithDistanceSorted.sort(Entry.comparingByValue());
	}
	
	@Override
	public int getItemCount() {
		return this.speciesWithDistanceSorted.size();
	}
}
