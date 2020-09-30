package com.zooverse.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.utils.SpeciesSearchAdapter;

public class SpeciesSearchActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_species_search);
		
		SpeciesSearchAdapter speciesSearchAdapter = new SpeciesSearchAdapter();
		RecyclerView searchResults = findViewById(R.id.searchResults);
		searchResults.setAdapter(speciesSearchAdapter);
		searchResults.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
		
		EditText searchCriterionEditText = findViewById(R.id.searchCriterionEditText);
		searchCriterionEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
					speciesSearchAdapter.updateCursor(charSequence.toString());
					speciesSearchAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
			}
		});
	}
}