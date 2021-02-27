package com.zooverse.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.activities.adapters.AbstractSubjectCatalogueAdapter;
import com.zooverse.model.Subject;
import com.zooverse.model.Group;
import com.zooverse.model.Species;

public abstract class AbstractSubjectCatalogueActivity extends AbstractBaseActivity implements AbstractSubjectCatalogueAdapter.SubjectOnClickListener {
	protected AbstractSubjectCatalogueAdapter subjectCatalogueAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.enableOptionsMenu();
		
		RecyclerView speciesList = findViewById(R.id.speciesList);
		speciesList.setAdapter(subjectCatalogueAdapter);
		speciesList.setLayoutManager(new LinearLayoutManager(this));
	}
	
	@Override
	public void onSubjectClick(int position) {
		Subject selectedSubject = subjectCatalogueAdapter.getSelectedSubject(position);
		
		if (selectedSubject instanceof Group) {
			Intent intent = new Intent(MainApplication.getContext(), SubjectCatalogueGroupActivity.class);
			intent.putExtra(MainApplication.INTENT_EXTRA_GROUP_ID, selectedSubject.getId());
			startActivity(intent);
		} else if (selectedSubject instanceof Species) {
			Intent intent = new Intent(MainApplication.getContext(), SpeciesActivity.class);
			intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, selectedSubject.getId());
			startActivity(intent);
		}
	}
}