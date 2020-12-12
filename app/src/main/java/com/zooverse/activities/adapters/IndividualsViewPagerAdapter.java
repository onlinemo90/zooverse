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
	
	public IndividualsViewPagerAdapter(List<Individual> individuals){
		this.individuals = individuals;
	}
	
	// inner class for view holder
	public static class IndividualViewHolder extends RecyclerView.ViewHolder{
		private final ImageView individualImageView = itemView.findViewById(R.id.individualImage);
		private final ImageView genderImageView = itemView.findViewById(R.id.individualIconGender);
		private final ImageView ageImageView = itemView.findViewById(R.id.individualIconAge);
		private final ImageView sizeImageView = itemView.findViewById(R.id.individualIconSize);
		private final ImageView placeOfOriginImageView = itemView.findViewById(R.id.individualIconOrigin);
		private final ImageView weightImageView= itemView.findViewById(R.id.individualIconWeight);
		private final TextView individualNameTextView = itemView.findViewById(R.id.individualNameTextView);
		private final TextView individualAgeTextView = itemView.findViewById(R.id.individualAgeTextView);
		private final TextView individualPlaceOfBirthTextView = itemView.findViewById(R.id.individualPlaceOfBirthTextView);
		private final TextView individualWeightTextView = itemView.findViewById(R.id.individualWeightTextView);
		private final TextView individualSizeTextView = itemView.findViewById(R.id.individualSizeTextView);
		private final RecyclerView customAttributesRecyclerView = itemView.findViewById(R.id.customAttributesRecyclerView);
		
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
		
		if (individual.getName() != null)
			viewHolder.individualNameTextView.setText(individual.getName());
		else
			viewHolder.individualNameTextView.setText(MainApplication.getContext().getString(R.string.unknown_individual_name));
		
		if (individual.getAge() != null) {
			viewHolder.ageImageView.setVisibility(View.VISIBLE);
			viewHolder.individualAgeTextView.setText(individual.getAge());
		}
		
		if (individual.getSize() != null) {
			viewHolder.sizeImageView.setVisibility(View.VISIBLE);
			viewHolder.individualSizeTextView.setText(individual.getSize());
		}
		
		if (individual.getPlaceOfBirth() != null) {
			viewHolder.placeOfOriginImageView.setVisibility(View.VISIBLE);
			viewHolder.individualPlaceOfBirthTextView.setText(individual.getPlaceOfBirth());
		}
		
		if (individual.getWeight() != null) {
			viewHolder.weightImageView.setVisibility(View.VISIBLE);
			viewHolder.individualWeightTextView.setText(individual.getWeight());
		}

		Theme.apply(
				viewHolder.ageImageView,
				viewHolder.sizeImageView,
				viewHolder.placeOfOriginImageView,
				viewHolder.weightImageView
		);
		
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
