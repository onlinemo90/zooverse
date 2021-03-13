package com.zooverse.activities.adapters;

import android.location.Location;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.R;
import com.zooverse.model.Subject;
import com.zooverse.model.Group;
import com.zooverse.model.Model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSubjectCatalogueAdapter extends RecyclerView.Adapter<AbstractSubjectCatalogueAdapter.AbstractSubjectCatalogueViewHolder> {
	protected final List<Subject> fullSubjectList = Model.getSortedSubjectList();
	protected final SubjectOnClickListener subjectOnClickListener;
	
	public AbstractSubjectCatalogueAdapter(SubjectOnClickListener subjectOnClickListener) {
		this.subjectOnClickListener = subjectOnClickListener;
	}
	
	// inner class for view holder
	public abstract static class AbstractSubjectCatalogueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		protected final TextView speciesNameTextView = itemView.findViewById(R.id.speciesNameTextView);
		protected final ImageView speciesImageView = itemView.findViewById(R.id.speciesImageView);
		protected final ImageView speciesLocationImageView = itemView.findViewById(R.id.speciesLocationIcon);
		protected final TextView speciesDistanceTextView = itemView.findViewById(R.id.speciesDistanceTextView);
		protected final SubjectOnClickListener subjectOnClickListener;
		
		public AbstractSubjectCatalogueViewHolder(@NonNull View itemView, SubjectOnClickListener subjectOnClickListener) {
			super(itemView);
			this.subjectOnClickListener = subjectOnClickListener;
			itemView.setOnClickListener(this);
		}
		
		@Override //on click method references interface for on click implementation
		public void onClick(View v) {
			subjectOnClickListener.onSubjectClick(getAdapterPosition());
		}
	}
	
	public abstract AbstractSubjectCatalogueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);
	
	//public abstract void onBindViewHolder(@NonNull AbstractSubjectCatalogueViewHolder viewHolder, int position);
	
	public abstract Subject getSelectedSubject(int position);
	
	public abstract void updateCursor(String searchCriterion);
	
	public abstract void updateCursor(Location userLocation);
	
	public abstract void updateCursor(Group group);
	
	public abstract ArrayList<Integer> getSubjectListInContext();
	
	public interface SubjectOnClickListener {
		void onSubjectClick(int position);
	}
	
}
