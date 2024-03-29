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
	}
	
	protected void setAdapter() {
		RecyclerView speciesList = findViewById(R.id.speciesList);
		speciesList.setAdapter(subjectCatalogueAdapter);
		speciesList.setLayoutManager(new LinearLayoutManager(this));
	}
	
	public AbstractSubjectCatalogueAdapter getAdapter() {
		return subjectCatalogueAdapter;
	}
	
	@Override
	public void onSubjectClick(int position) {
		Subject selectedSubject = subjectCatalogueAdapter.getSelectedSubject(position);
		
		if (selectedSubject instanceof Group) {
			Intent intent = new Intent(MainApplication.getContext(), GroupActivity.class);
			intent.putExtra(MainApplication.INTENT_EXTRA_GROUP_ID, selectedSubject.getId());
			startActivity(intent);
		} else if (selectedSubject instanceof Species) {
			Intent intent = new Intent(MainApplication.getContext(), SpeciesActivity.class);
			intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, selectedSubject.getId());
			intent.putIntegerArrayListExtra(MainApplication.INTENT_EXTRA_SPECIES_VIEWPAGER_FILTERED_LIST, subjectCatalogueAdapter.getSubjectListInContext());
			startActivity(intent);
		}
	}
}