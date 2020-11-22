package com.zooverse.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.activities.adapters.SpeciesSearchAdapter;

public class SpeciesSearchActivity extends AbstractBaseActivity implements SpeciesSearchAdapter.SpeciesOnClickListener {
	private SpeciesSearchAdapter speciesSearchAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_species_search);
		this.enableOptionsMenu();
		
		speciesSearchAdapter = new SpeciesSearchAdapter(this);
		RecyclerView searchResults = findViewById(R.id.searchResults);
		searchResults.setAdapter(speciesSearchAdapter);
		searchResults.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
		
		EditText searchCriterionEditText = findViewById(R.id.searchCriterionEditText);
		searchCriterionEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				speciesSearchAdapter.updateCursor(charSequence.toString());
				speciesSearchAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
			}
		});
	}
	
	
	@Override
	public void onSpeciesClick(int position) {
		int selectedSpeciesId = speciesSearchAdapter.getSelectedSpecies(position).getId();
		Intent intent = new Intent(MainApplication.getContext(), SpeciesActivity.class);
		intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, selectedSpeciesId);
		startActivity(intent);
	}
}