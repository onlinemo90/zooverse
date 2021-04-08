package com.zooverse.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import androidx.viewpager2.widget.ViewPager2;

import com.zooverse.R;
import com.zooverse.activities.adapters.SubjectPageAdapter;
import com.zooverse.zoo.Subject;
import com.zooverse.zoo.Zoo;

import java.util.Arrays;
import java.util.List;

public class SubjectActivity extends AbstractBaseActivity {
	public enum IntentExtras {
		SUBJECT_UUID_ARRAY,
		STARTING_SUBJECT_INDEX
	}
	
	@Override
	@SuppressLint("MissingSuperCall")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_subject, true);
		
		Intent intent = this.getIntent();
		List<Subject> subjectList = Zoo.getSubjectList(Arrays.asList(intent.getStringArrayExtra(IntentExtras.SUBJECT_UUID_ARRAY.toString())));
		int startingSubjectIndex = 0;
		if (subjectList.size() > 1){
			startingSubjectIndex = intent.getIntExtra(IntentExtras.STARTING_SUBJECT_INDEX.toString(), -1);
		}
		
		ViewPager2 viewPager = findViewById(R.id.speciesViewPager);
		viewPager.setAdapter(new SubjectPageAdapter(this, subjectList));
		viewPager.setCurrentItem(startingSubjectIndex, false);
	}
}