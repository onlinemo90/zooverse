package com.zooverse.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.activities.adapters.SubjectCatalogueDefaultAdapter;
import com.zooverse.model.Group;
import com.zooverse.model.Model;

public class GroupActivity extends AbstractSubjectCatalogueActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		
		subjectCatalogueAdapter = new SubjectCatalogueDefaultAdapter(this);
		super.setAdapter();
		
		Group group = Model.getGroups().get(getIntent().getIntExtra(MainApplication.INTENT_EXTRA_GROUP_ID, 0));
		
		setTitle(group.getName());
		
		ImageView groupImage = findViewById(R.id.groupImage);
		groupImage.setImageBitmap(group.getImage());
		
		subjectCatalogueAdapter.updateCursor(group);
	}
}