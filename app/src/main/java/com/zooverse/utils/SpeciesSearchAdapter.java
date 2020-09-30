package com.zooverse.utils;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.model.Model;

public class SpeciesSearchAdapter extends RecyclerView.Adapter<SpeciesSearchAdapter.SpeciesSearchViewHolder> {
	private Cursor speciesCursor = Model.getInstance().getAllSpecies("");
	
	// inner class for view holder
	public static class SpeciesSearchViewHolder extends RecyclerView.ViewHolder{
		TextView speciesNameTextView = itemView.findViewById(R.id.speciesNameTextView);
		ImageView speciesImageView = itemView.findViewById(R.id.speciesImageView);
		public SpeciesSearchViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
	
	@NonNull
	@Override
	public SpeciesSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// assign layout file which is used for each row in recycler view
		View speciesListLayout = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.layout_recyclerview_species, parent, false);
		return new SpeciesSearchViewHolder(speciesListLayout);
	}
	
	@Override
	public void onBindViewHolder(@NonNull SpeciesSearchViewHolder viewHolder, int position) {
		// check if cursor can move further on db entries
		if (!speciesCursor.moveToPosition(position)){
			return;
		}
		viewHolder.speciesNameTextView.setText(speciesCursor.getString(speciesCursor.getColumnIndex("name")));
		byte[] imgBlob = speciesCursor.getBlob(speciesCursor.getColumnIndex("image"));
		if (imgBlob != null) {
			viewHolder.speciesImageView.setImageBitmap(BitmapFactory.decodeByteArray(imgBlob, 0, imgBlob.length));
		}
	}
	
	@Override
	public int getItemCount() {
		return speciesCursor.getCount();
	}
	
	public void updateCursor(String searchCriterion){
		speciesCursor = Model.getInstance().getAllSpecies(searchCriterion);
	}
}
