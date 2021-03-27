package com.zooverse.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Theme;
import com.zooverse.activities.adapters.CustomAttributesAdapter;
import com.zooverse.activities.adapters.SubjectCatalogueDefaultAdapter;
import com.zooverse.model.Group;
import com.zooverse.model.Model;

public class GroupActivity extends AbstractSubjectCatalogueActivity {
	private Boolean attributesVisible = false;
	private RecyclerView subjectRecyclerView;
	private ImageView childImageView;
	private Group group;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_subject);
		subjectCatalogueAdapter = new SubjectCatalogueDefaultAdapter(this);
		subjectRecyclerView = findViewById(R.id.subjectRecyclerView);
		group = Model.getGroups().get(getIntent().getIntExtra(MainApplication.INTENT_EXTRA_GROUP_ID, 0));
		
		TextView childCountTextView = findViewById(R.id.childCountTextView);
		ImageView subjectImage = findViewById(R.id.subjectImage);
		subjectImage.setImageBitmap(group.getImage());
		
		if (group.getMembers().size() > 0) {
			childImageView = findViewById(R.id.childImageView);
			childImageView.setVisibility(View.VISIBLE);
			childImageView.setOnClickListener(view -> {
				this.setAdapter();
			});
			childCountTextView.setText(Integer.toString(group.getMembers().size()));
			childCountTextView.setTextColor(Theme.getColor(R.attr.themeColorBackground));
		} else {
			childCountTextView.setVisibility(View.INVISIBLE);
			childImageView.setColorFilter(Theme.getColor(R.attr.themeColorForegroundFaded));
		}
		
		this.setAdapter();
		setTitle(group.getName());
	}
	
	@Override
	protected void setAdapter() {
		if (attributesVisible) {
			subjectRecyclerView.setAdapter(getAdapter());
			subjectRecyclerView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
			getAdapter().updateCursor(group);
			childImageView.setColorFilter(Theme.getColor(R.attr.themeColorPrimaryDark));
			attributesVisible = false;
		} else {
			CustomAttributesAdapter customAttributesAdapter = new CustomAttributesAdapter(group.getAttributes());
			subjectRecyclerView.setAdapter(customAttributesAdapter);
			subjectRecyclerView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
			childImageView.setColorFilter(Theme.getColor(R.attr.themeColorPrimary));
			attributesVisible = true;
		}
	}
}