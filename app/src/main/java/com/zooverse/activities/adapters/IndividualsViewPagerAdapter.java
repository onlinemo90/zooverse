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
import com.zooverse.model.Individual;

import java.util.List;

public class IndividualsViewPagerAdapter extends RecyclerView.Adapter<IndividualsViewPagerAdapter.IndividualViewHolder> {
	List<Individual> individuals;
	
	public IndividualsViewPagerAdapter(List<Individual> individuals) {
		this.individuals = individuals;
	}
	
	// inner class for view holder
	public static class IndividualViewHolder extends RecyclerView.ViewHolder {
		private final ImageView subjectImage = itemView.findViewById(R.id.subjectImage);
		private final ImageView subjectIconGender = itemView.findViewById(R.id.subjectIconGender);
		private final RecyclerView subjectRecyclerView = itemView.findViewById(R.id.subjectRecyclerView);
		private final TextView individualAgeTextView = itemView.findViewById(R.id.mainAttribute1TextView);
		private final TextView individualPlaceOfBirthTextView = itemView.findViewById(R.id.mainAttribute2TextView);
		
		public IndividualViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
	
	@NonNull
	@Override
	public IndividualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View individualViewPagerLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_subject, parent, false);
		return new IndividualViewHolder(individualViewPagerLayout);
	}
	
	@Override
	public void onBindViewHolder(@NonNull IndividualViewHolder viewHolder, int position) {
		Individual individual = this.individuals.get(position);
		
		if (individual.getImage() != null)
			viewHolder.subjectImage.setImageBitmap(this.individuals.get(position).getImage());
		
		if (individual.getGender() != null && individual.getGender().toUpperCase().equals("M"))
			viewHolder.subjectIconGender.setImageDrawable(MainApplication.getContext().getDrawable(R.drawable.icon_gender_male));
		else if (individual.getGender() != null && individual.getGender().toUpperCase().equals("F"))
			viewHolder.subjectIconGender.setImageDrawable(MainApplication.getContext().getDrawable(R.drawable.icon_gender_female));
		
		if (individual.getAge() != null) {
			viewHolder.individualAgeTextView.setText(individual.getAge());
			viewHolder.individualAgeTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_age, 0, 0, 0);
		} else
			viewHolder.individualAgeTextView.setVisibility(View.GONE);
		
		if (individual.getPlaceOfBirth() != null) {
			viewHolder.individualPlaceOfBirthTextView.setText(individual.getPlaceOfBirth());
			viewHolder.individualPlaceOfBirthTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_place_of_origin, 0, 0, 0);
		}
		else
			viewHolder.individualPlaceOfBirthTextView.setVisibility(View.GONE);
		
		// Loading RecyclerView with custom attributes
		CustomAttributesAdapter customAttributesAdapter = new CustomAttributesAdapter(individual.getAttributes());
		viewHolder.subjectRecyclerView.setAdapter(customAttributesAdapter);
		viewHolder.subjectRecyclerView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
	}
	
	@Override
	public int getItemCount() {
		return individuals.size();
	}
}
