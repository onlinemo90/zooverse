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
import com.zooverse.model.Individual;

import java.text.SimpleDateFormat;
import java.util.List;

public class IndividualsViewPagerAdapter extends RecyclerView.Adapter<IndividualsViewPagerAdapter.IndividualViewHolder> {
	List<Individual> individuals;
	
	public IndividualsViewPagerAdapter(List<Individual> individuals){
		this.individuals = individuals;
	}
	
	// inner class for view holder
	public static class IndividualViewHolder extends RecyclerView.ViewHolder{
		private final ImageView individualImageView = itemView.findViewById(R.id.individualImage);
		private final TextView individualDobTextView = itemView.findViewById(R.id.individualDobTextView);
		private final TextView individualPlaceOfBirthTextView = itemView.findViewById(R.id.individualPlaceOfBirthTextView);
		private final TextView individualGenderTextView = itemView.findViewById(R.id.individualGenderTextView);
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
		viewHolder.individualDobTextView.setText(new SimpleDateFormat(MainApplication.getContext().getString(R.string.local_date_format)).format(individual.getDOB()));
		viewHolder.individualPlaceOfBirthTextView.setText(individual.getPlaceOfBirth());
		viewHolder.individualGenderTextView.setText(individual.getGender());
		viewHolder.individualWeightTextView.setText(individual.getWeight());
		viewHolder.individualSizeTextView.setText(individual.getWeight());
	}
	
	@Override
	public int getItemCount() {
		return individuals.size();
	}
}
