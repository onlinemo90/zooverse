package com.zooverse.activities.adapters;

import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.model.Group;
import com.zooverse.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectCatalogueDefaultAdapter extends AbstractSubjectCatalogueAdapter {
	protected List<Subject> filteredSubjectList = fullSubjectList;
	
	public SubjectCatalogueDefaultAdapter(SubjectOnClickListener subjectOnClickListener) {
		super(subjectOnClickListener);
	}
	
	// inner class for view holder
	public static class SubjectCatalogueDefaultViewHolder extends AbstractSubjectCatalogueViewHolder {
		
		public SubjectCatalogueDefaultViewHolder(@NonNull View itemView, SubjectOnClickListener subjectOnClickListener) {
			super(itemView, subjectOnClickListener);
		}
	}
	
	@NonNull
	@Override
	public SubjectCatalogueDefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// assign layout file which is used for each row in recycler view
		View subjectListLayout = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.layout_recyclerview_subjects, parent, false);
		return new SubjectCatalogueDefaultViewHolder(subjectListLayout, subjectOnClickListener);
	}
	
	@Override
	public void onBindViewHolder(@NonNull AbstractSubjectCatalogueViewHolder viewHolder, int position) {
		viewHolder.speciesNameTextView.setText(this.filteredSubjectList.get(position).getName());
		viewHolder.speciesImageView.setImageBitmap(this.filteredSubjectList.get(position).getImage());
	}
	
	@Override
	public Subject getSelectedSubject(int position) {
		return this.filteredSubjectList.get(position);
	}
	
	@Override
	public void updateCursor(String searchCriterion) {
		this.filteredSubjectList = new ArrayList<>();
		for (Subject subject : this.fullSubjectList) {
			if (subject.getName().toLowerCase().startsWith(searchCriterion.toLowerCase())) {
				this.filteredSubjectList.add(subject);
			}
		}
	}
	
	@Override
	public  void updateCursor(Location userLocation) { return; }
	
	@Override
	public int getItemCount() {
		return this.filteredSubjectList.size();
	}
	
	@Override
	public void updateCursor(Group group) {
		this.filteredSubjectList = group.getMembers();
	}
}
