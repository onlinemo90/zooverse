package com.zooverse.activities.adapters;

import android.graphics.drawable.Drawable;
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
		private final TextView individualAgeTextView = itemView.findViewById(R.id.individualAgeTextView);
		private final TextView individualPlaceOfBirthTextView = itemView.findViewById(R.id.individualOriginTextView);
		private final TextView individualWeightTextView = itemView.findViewById(R.id.individualWeightTextView);
		private final TextView individualSizeTextView = itemView.findViewById(R.id.individualSizeTextView);
		
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
			viewHolder.genderImageView.setImageDrawable(MainApplication.getContext().getDrawable(R.drawable.icon_gender_male));
		else if (individual.getGender() != null && individual.getGender().toUpperCase().equals("F"))
			viewHolder.genderImageView.setImageDrawable(MainApplication.getContext().getDrawable(R.drawable.icon_gender_female));
		
		if (individual.getAge() != null)
			viewHolder.individualAgeTextView.setText(individual.getAge());
		else
			viewHolder.individualAgeTextView.setVisibility(View.GONE);
		
		if (individual.getSize() != null)
			viewHolder.individualSizeTextView.setText(individual.getSize());
		else
			viewHolder.individualSizeTextView.setVisibility(View.GONE);
		
		if (individual.getWeight() != null)
			viewHolder.individualWeightTextView.setText(individual.getWeight());
		else
			viewHolder.individualWeightTextView.setVisibility(View.GONE);
		
		if (individual.getPlaceOfBirth() != null) {
			viewHolder.individualPlaceOfBirthTextView.setText(individual.getPlaceOfBirth());
		}
		else {
			viewHolder.individualPlaceOfBirthTextView.setVisibility(View.GONE);
		}
		
		// Loading RecyclerView with custom attributes
		CustomAttributesAdapter customAttributesAdapter = new CustomAttributesAdapter(individual.getAttributes());
		viewHolder.customAttributesRecyclerView.setAdapter(customAttributesAdapter);
		viewHolder.customAttributesRecyclerView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
	}
	
	@Override
	public int getItemCount() {
		return individuals.size();
	}
}
