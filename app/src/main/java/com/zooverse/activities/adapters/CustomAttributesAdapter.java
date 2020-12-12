package com.zooverse.activities.adapters;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.R;

import java.util.List;

public class CustomAttributesAdapter extends RecyclerView.Adapter<CustomAttributesAdapter.CustomAttributeViewHolder>{
	
	private final List<Pair<String, String>> attributes;
	
	public CustomAttributesAdapter(List<Pair<String, String>> attributes) {
		this.attributes = attributes;
	}
	
	// inner class for view holder
	public class CustomAttributeViewHolder extends RecyclerView.ViewHolder {
		private final TextView categoryTextView = itemView.findViewById(R.id.categoryTextView);
		private final TextView attributeTextView = itemView.findViewById(R.id.attributeTextView);
		
		public CustomAttributeViewHolder(@NonNull View itemView) { super(itemView); }
	}
	
	@NonNull
	@Override
	public CustomAttributeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View customAttributesLayout = LayoutInflater.from (parent.getContext()).inflate(R.layout.layout_recyclerview_custom_attributes,parent,false);
		return new CustomAttributeViewHolder(customAttributesLayout);
	}
	
	@Override
	public void onBindViewHolder(@NonNull CustomAttributeViewHolder viewHolder, int position) {
		viewHolder.categoryTextView.setText(attributes.get(position).first);
		viewHolder.attributeTextView.setText(attributes.get(position).second);
	}
	
	@Override
	public int getItemCount() {
		return attributes.size();
	}
}
