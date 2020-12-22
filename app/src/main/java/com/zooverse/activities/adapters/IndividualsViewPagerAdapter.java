package com.zooverse.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Theme;
import com.zooverse.model.Individual;

import java.util.List;

public class IndividualsViewPagerAdapter extends RecyclerView.Adapter<IndividualsViewPagerAdapter.IndividualViewHolder> {
	List<Individual> individuals;
	
	public IndividualsViewPagerAdapter(List<Individual> individuals) {
		this.individuals = individuals;
	}
	
	// inner class for view holder
	public static class IndividualViewHolder extends RecyclerView.ViewHolder {
		private final ImageView individualImageView = itemView.findViewById(R.id.individualImage);
		private final ImageView genderImageView = itemView.findViewById(R.id.individualIconGender);
		private final RecyclerView customAttributesRecyclerView = itemView.findViewById(R.id.customAttributesRecyclerView);
		private final RecyclerView demographicsRecyclerView = itemView.findViewById(R.id.individualDemographicsRecyclerView);
		
		public IndividualViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
	
	@NonNull
	@Override
	public IndividualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View individualViewPagerLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_viewpager_individuals, parent, false);
		return new IndividualViewHolder(individualViewPagerLayout);
	}
	
	@Override
	public void onBindViewHolder(@NonNull IndividualViewHolder viewHolder, int position) {
		Individual individual = this.individuals.get(position);
		
		if (individual.getImage() != null)
			viewHolder.individualImageView.setImageBitmap(this.individuals.get(position).getImage());
		
		if (individual.getGender() != null && individual.getGender().toUpperCase().equals("M"))
			viewHolder.genderImageView.setImageResource(R.drawable.icon_gender_male);
		else if (individual.getGender() != null && individual.getGender().toUpperCase().equals("F"))
			viewHolder.genderImageView.setImageResource(R.drawable.icon_gender_female);
		
		// Loading RecyclerView with custom attributes
		CustomAttributesAdapter customAttributesAdapter = new CustomAttributesAdapter(individual.getAttributes());
		viewHolder.customAttributesRecyclerView.setAdapter(customAttributesAdapter);
		viewHolder.customAttributesRecyclerView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
		
		// Loading RecyclerView with demographics
		IndividualDemographicsAdapter demographicsAdapter = new IndividualDemographicsAdapter(individual);
		viewHolder.demographicsRecyclerView.setAdapter(demographicsAdapter);
		viewHolder.demographicsRecyclerView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
	}
	
	@Override
	public int getItemCount() {
		return individuals.size();
	}
}
