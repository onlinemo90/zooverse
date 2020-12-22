package com.zooverse.activities.adapters;

import android.graphics.drawable.Drawable;
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
import com.zooverse.model.Individual;

import java.util.ArrayList;
import java.util.List;

public class IndividualDemographicsAdapter extends RecyclerView.Adapter<IndividualDemographicsAdapter.IndividualDemographicsViewHolder> {
	private final List<DemographicViewData> demographicViewDataList;
	
	IndividualDemographicsAdapter(Individual individual) {
		demographicViewDataList = new ArrayList<>();
		
		// Age
		if (individual.getDOB() != null){
			demographicViewDataList.add(new DemographicViewData(R.string.individual_age_icon, individual.getAge(), R.drawable.icon_age));
		}
		// Size
		if (individual.getSize() != null){
			demographicViewDataList.add(new DemographicViewData(R.string.individual_size_icon, individual.getSize(), R.drawable.icon_size));
		}
		// Weight
		if (individual.getWeight() != null){
			demographicViewDataList.add(new DemographicViewData(R.string.individual_weight_icon, individual.getWeight(), R.drawable.icon_weight));
		}
		// Place of Birth
		if (individual.getPlaceOfBirth() != null){
			demographicViewDataList.add(new DemographicViewData(R.string.individual_origin_icon, individual.getPlaceOfBirth(), R.drawable.icon_place_of_origin));
		}
		
	}
	
	private class DemographicViewData {
		public final String category;
		public final String value;
		public final int iconResourceId;
		
		public DemographicViewData(int categoryResourceId, String value, int iconResourceId) {
			this.category = MainApplication.getContext().getResources().getString(categoryResourceId);
			this.value = value;
			this.iconResourceId = iconResourceId;
		}
	}
	
	// inner class for view holder
	public class IndividualDemographicsViewHolder extends RecyclerView.ViewHolder {
		private final ImageView demographicImageView = itemView.findViewById(R.id.demographicImageView);
		private final TextView demographicTextView = itemView.findViewById(R.id.demographicTextView);
		
		public IndividualDemographicsViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
	
	@NonNull
	@Override
	public IndividualDemographicsAdapter.IndividualDemographicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View customAttributesLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_individual_demographics, parent, false);
		return new IndividualDemographicsAdapter.IndividualDemographicsViewHolder(customAttributesLayout);
	}
	
	@Override
	public void onBindViewHolder(@NonNull IndividualDemographicsAdapter.IndividualDemographicsViewHolder viewHolder, int position) {
		viewHolder.demographicTextView.setText(demographicViewDataList.get(position).value);
		viewHolder.demographicImageView.setImageResource(demographicViewDataList.get(position).iconResourceId);
		viewHolder.demographicImageView.setContentDescription(demographicViewDataList.get(position).category);
		Theme.apply(viewHolder.demographicImageView);
	}
	
	@Override
	public int getItemCount() {
		return demographicViewDataList.size();
	}
}
