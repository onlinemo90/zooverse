package com.zooverse.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.activities.adapters.SpeciesCatalogueAdapter;
import com.zooverse.model.Group;
import com.zooverse.model.Model;

public class GroupActivity extends AbstractBaseActivity implements SpeciesCatalogueAdapter.SpeciesOnClickListener {
	
	private SpeciesCatalogueAdapter speciesCatalogueAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		
		Group group = Model.getGroups().get(getIntent().getIntExtra(MainApplication.INTENT_EXTRA_GROUP_ID, 0));
		
		setTitle(group.getName());
		
		ImageView groupImage = findViewById(R.id.groupImage);
		groupImage.setImageBitmap(group.getImage());
		
		speciesCatalogueAdapter = new SpeciesCatalogueAdapter(this, SpeciesCatalogueActivity.CATALOGUE_MODE_WITHOUT_LOCATION);
		RecyclerView speciesList = findViewById(R.id.speciesList);
		speciesList.setAdapter(speciesCatalogueAdapter);
		speciesList.setLayoutManager(new LinearLayoutManager(this));
		speciesCatalogueAdapter.updateCursor(group);
	}
	
	@Override
	public void onSpeciesClick(int position) {
		int selectedSpeciesId = speciesCatalogueAdapter.getSelectedSpecies(position).getId();
		Intent intent = new Intent(MainApplication.getContext(), SpeciesActivity.class);
		intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, selectedSpeciesId);
		startActivity(intent);
		
	}
}