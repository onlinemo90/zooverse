package com.zooverse.activities;


import android.os.Bundle;
import android.widget.ImageView;


import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Theme;
import com.zooverse.activities.adapters.GroupsViewPagerAdapter;
import com.zooverse.activities.adapters.SubjectCatalogueDefaultAdapter;
import com.zooverse.model.Group;
import com.zooverse.model.Model;

public class GroupActivity extends AbstractSubjectCatalogueActivity {
	public String [] tabLabels;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		tabLabels = new String[]{getString(R.string.group_tab1), getString(R.string.group_tab2)};
		
		subjectCatalogueAdapter = new SubjectCatalogueDefaultAdapter(this);
		
		Group group = Model.getGroups().get(getIntent().getIntExtra(MainApplication.INTENT_EXTRA_GROUP_ID, 0));
		
		setTitle(group.getName());
		
		ImageView groupImage = findViewById(R.id.groupImage);
		groupImage.setImageBitmap(group.getImage());
		
		ViewPager2 viewPager = findViewById(R.id.groupsViewPager);
		GroupsViewPagerAdapter viewPagerAdapter = new GroupsViewPagerAdapter(this, group);
		viewPager.setAdapter(viewPagerAdapter);
		
		TabLayout tabLayout = findViewById(R.id.groupMenuTabLayout);
		tabLayout.setTabTextColors(Theme.getColor(R.attr.themeColorForeground),Theme.getColor(R.attr.themeColorPrimary));
		
		new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
			tab.setText(tabLabels [position]);
		}).attach();
	}
}