package com.zooverse.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.R;
import com.zooverse.activities.adapters.SubjectRecyclerViewAdapter;
import com.zooverse.zoo.Zoo;
import com.zooverse.zoo.Subject;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;

public class SubjectSearchActivity extends AbstractBaseActivity {
	private final SubjectRecyclerViewAdapter subjectRecyclerViewAdapter = new SubjectRecyclerViewAdapter(Zoo.getSearchableSubjectsList());
	
	@Override
	@SuppressLint("MissingSuperCall")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_subject_search, true);
		
		// Subject RecyclerView
		RecyclerView subjectList = findViewById(R.id.speciesList);
		subjectList.setAdapter(subjectRecyclerViewAdapter);
		subjectList.setLayoutManager(new LinearLayoutManager(this));
		
		// Search input
		EditText searchCriterionEditText = findViewById(R.id.searchCriterionEditText);
		searchCriterionEditText.setVisibility(View.VISIBLE);
		searchCriterionEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				String searchCriterion = charSequence.toString().toLowerCase();
				List<Subject> filteredSubjectList = new ArrayList<>();
				for (Subject subject : Zoo.getSearchableSubjectsList()) {
					String subjectName = subject.getName().toLowerCase();
					// 1st: search for string snippet within subject name, 2nd: search for typos within full string
					if (subjectName.contains(searchCriterion) || LevenshteinDistance.getDefaultInstance().apply(subjectName, searchCriterion) == 1)
						filteredSubjectList.add(subject);
				}
				subjectRecyclerViewAdapter.setSubjectList(filteredSubjectList);
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