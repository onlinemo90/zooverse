package com.zooverse.activities.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.activities.SubjectActivity;
import com.zooverse.zoo.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectCatalogAdapter extends RecyclerView.Adapter<SubjectCatalogAdapter.SubjectViewHolder> {
	private List<Subject> subjectList;
	
	protected class SubjectViewHolder extends RecyclerView.ViewHolder {
		protected final TextView speciesNameTextView = itemView.findViewById(R.id.speciesNameTextView);
		protected final ImageView speciesImageView = itemView.findViewById(R.id.speciesImageView);
		
		public SubjectViewHolder(@NonNull View itemView) {
			super(itemView);
			itemView.setOnClickListener(view -> {
				Intent intent = new Intent(MainApplication.getContext(), SubjectActivity.class);
				intent.putExtra(
						SubjectActivity.IntentExtras.SUBJECT_UUID_ARRAY.toString(),
						subjectList.stream().map(Subject::getUuid).toArray(String[]::new)
				);
				intent.putExtra(SubjectActivity.IntentExtras.STARTING_SUBJECT_INDEX.toString(), getAdapterPosition());
				MainApplication.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			});
		}
		
		public void bind(Subject subject) {
			this.speciesNameTextView.setText(subject.getName());
			this.speciesImageView.setImageBitmap(subject.getImage());
		}
	}
	
	public SubjectCatalogAdapter() {
		this.subjectList = new ArrayList<>();
	}
	
	public SubjectCatalogAdapter(List<Subject> subjectList) {
		this.setSubjectList(subjectList);
	}
	
	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
		this.notifyDataSetChanged();
	}
	
	@NonNull
	@Override
	public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new SubjectViewHolder(
				LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.layout_recyclerview_subjects, parent, false)
		);
	}
	
	@Override
	public void onBindViewHolder(@NonNull SubjectViewHolder viewHolder, int position) {
		viewHolder.bind(this.subjectList.get(position));
	}
	
	@Override
	public int getItemCount() {
		return this.subjectList.size();
	}
}
