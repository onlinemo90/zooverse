package com.zooverse.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.zooverse.R;
import com.zooverse.activities.adapters.SubjectCatalogueDefaultAdapter;

public class SubjectCatalogueSearchActivity extends AbstractSubjectCatalogueActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject_catalogue);
		
		subjectCatalogueAdapter = new SubjectCatalogueDefaultAdapter(this);
		super.setAdapter();

		EditText searchCriterionEditText = findViewById(R.id.searchCriterionEditText);
		searchCriterionEditText.setVisibility(View.VISIBLE);
		searchCriterionEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				subjectCatalogueAdapter.updateCursor(charSequence.toString());
				subjectCatalogueAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
			}
		});
		
		
	}
}