package com.zooverse.activities.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Theme;
import com.zooverse.activities.SubjectLocationActivity;
import com.zooverse.activities.SubjectActivity;
import com.zooverse.zoo.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;


public class SubjectLocationCatalogAdapter extends RecyclerView.Adapter<SubjectLocationCatalogAdapter.SubjectLocationViewHolder> {
	List<Entry<Integer, Subject>> subjectListByDistance = new ArrayList<>();
	
	protected class SubjectLocationViewHolder extends RecyclerView.ViewHolder {
		private final TextView speciesNameTextView = itemView.findViewById(R.id.speciesNameTextView);
		private final ImageView speciesImageView = itemView.findViewById(R.id.speciesImageView);
		private final ImageView speciesLocationImageView = itemView.findViewById(R.id.speciesLocationIcon);
		private final TextView speciesDistanceTextView = itemView.findViewById(R.id.speciesDistanceTextView);
		
		public SubjectLocationViewHolder(@NonNull View itemView) {
			super(itemView);
			itemView.setOnClickListener(view -> {
				Subject subject = subjectListByDistance.get(getAdapterPosition()).getValue();
				Intent intent = new Intent(MainApplication.getContext(), SubjectActivity.class);
				intent.putExtra(SubjectActivity.IntentExtras.SUBJECT_UUID_ARRAY.toString(), new String[]{subject.getUuid()});
				MainApplication.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			});
			
			this.speciesLocationImageView.setVisibility(View.VISIBLE);
			this.speciesDistanceTextView.setVisibility(View.VISIBLE);
			this.speciesLocationImageView.setImageDrawable(ContextCompat.getDrawable(MainApplication.getContext(), R.drawable.icon_location));
		}
		
		@SuppressLint("SetTextI18n")
		public void bind(Subject subject, int distance) {
			speciesNameTextView.setText(subject.getName());
			speciesImageView.setImageBitmap(subject.getImage());
			Theme.apply(Theme.THEME_COLOR_DEFAULT, speciesLocationImageView);
			
			// Format distance string
			if (distance > 999) {
				speciesDistanceTextView.setText(distance / 1000 + MainApplication.getContext().getString(R.string.kilometer_abbreviation));
			} else {
				speciesDistanceTextView.setText(distance + MainApplication.getContext().getString(R.string.meter_abbreviation));
			}
			
			speciesLocationImageView.setOnClickListener(view -> {
				Intent intent = new Intent(MainApplication.getContext(), SubjectLocationActivity.class);
				intent.putExtra(SubjectLocationActivity.IntentExtras.SUBJECT_UUID.toString(), subject.getUuid());
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				MainApplication.getContext().startActivity(intent);
			});
		}
	}
	
	public SubjectLocationCatalogAdapter() {
	}
	
	public SubjectLocationCatalogAdapter(TreeMap<Integer, Subject> subjectsByDistance) {
		this.setSubjectListByDistance(subjectsByDistance);
	}
	
	public void setSubjectListByDistance(TreeMap<Integer, Subject> subjectsByDistance) {
		this.subjectListByDistance = new ArrayList<>();
		this.subjectListByDistance.addAll(subjectsByDistance.entrySet());
		this.notifyDataSetChanged();
	}
	
	@NonNull
	public SubjectLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new SubjectLocationViewHolder(
				LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.layout_recyclerview_subjects, parent, false)
		);
	}
	
	public void onBindViewHolder(@NonNull SubjectLocationViewHolder viewHolder, int position) {
		Subject subject = this.subjectListByDistance.get(position).getValue();
		Integer distance = this.subjectListByDistance.get(position).getKey();
		viewHolder.bind(subject, distance);
	}
	
	@Override
	public int getItemCount() {
		return this.subjectListByDistance.size();
	}
}
