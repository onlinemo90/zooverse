package com.zooverse.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Theme;
import com.zooverse.activities.adapters.IndividualsViewPagerAdapter;
import com.zooverse.model.Individual;
import com.zooverse.model.Model;
import com.zooverse.model.Species;

import java.util.List;

public class IndividualsActivity extends AbstractBaseActivity {
	private List<Individual> individuals;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individuals);
		
		Species species = Model.getSpecies().get(getIntent().getIntExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, 0));
		individuals = species.getIndividuals();
		
		setTitle(getString(R.string.our_species_title_prefix) + " " + species.getNamePlural());
		
		ViewPager2 viewPager = findViewById(R.id.individualsViewPager);
		TabLayout tabLayout = findViewById(R.id.individualsTabLayout);
		tabLayout.setTabTextColors(Theme.getColor(R.attr.themeColorForeground),Theme.getColor(R.attr.themeColorPrimary));
		
		IndividualsViewPagerAdapter viewPagerAdapter = new IndividualsViewPagerAdapter(individuals);
		viewPager.setAdapter(viewPagerAdapter);
		
		new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
			tab.setText(individuals.get(position).getName());
		}).attach();
		
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {}
			@Override
			public void onTabUnselected(TabLayout.Tab tab) {}
			@Override
			public void onTabReselected(TabLayout.Tab tab) {}
		});
	}
	
}
