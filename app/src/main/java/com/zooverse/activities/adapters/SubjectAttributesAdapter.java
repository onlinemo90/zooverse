package com.zooverse.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.R;
import com.zooverse.zoo.Subject;

public class SubjectAttributesAdapter extends RecyclerView.Adapter<SubjectAttributesAdapter.CustomAttributeViewHolder> {
	
	private final Subject subject;
	
	public SubjectAttributesAdapter(Subject subject) {
		this.subject = subject;
	}
	
	// inner class for view holder
	public class CustomAttributeViewHolder extends RecyclerView.ViewHolder {
		private final TextView categoryTextView = itemView.findViewById(R.id.categoryTextView);
		private final TextView attributeTextView = itemView.findViewById(R.id.attributeTextView);
		
		public CustomAttributeViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
	
	@NonNull
	@Override
	public CustomAttributeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View customAttributesLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_attributes, parent, false);
		return new CustomAttributeViewHolder(customAttributesLayout);
	}
	
	@Override
	public void onBindViewHolder(@NonNull CustomAttributeViewHolder viewHolder, int position) {
		viewHolder.categoryTextView.setText(this.subject.getAttributes().get(position).getCategory());
		viewHolder.attributeTextView.setText(this.subject.getAttributes().get(position).getText());
	}
	
	@Override
	public int getItemCount() {
		return this.subject.getAttributes().size();
	}
}
