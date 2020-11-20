package com.zooverse.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
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
		individuals = species.getIndividualsList();
		
		ViewPager2 viewPager = findViewById(R.id.individualsViewPager);
		TabLayout tabLayout = findViewById(R.id.individualsTabLayout);
		
		IndividualsViewPagerAdapter viewPagerAdapter = new IndividualsViewPagerAdapter(individuals);
		viewPager.setAdapter(viewPagerAdapter);
		
		new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
			tab.setIcon(R.drawable.icon_individuals);
			if (position == 0) {
				tab.getIcon().setColorFilter(Theme.getColor(R.attr.themeColorPrimary), PorterDuff.Mode.SRC_ATOP);
				this.setActivityTitle(position);
			}
		}).attach();
		
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				tab.getIcon().setColorFilter(Theme.getColor(R.attr.themeColorPrimary), PorterDuff.Mode.SRC_ATOP);
				IndividualsActivity.this.setActivityTitle(tab.getPosition());
			}
			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
				tab.getIcon().setColorFilter(Theme.getColor(R.attr.themeColorForeground), PorterDuff.Mode.SRC_ATOP);
			}
			@Override
			public void onTabReselected(TabLayout.Tab tab) {}
		});
	}
	
	private void setActivityTitle(int position){
		Individual selectedIndividual = individuals.get(position);
		if (!"".equals(selectedIndividual.getName())){
			setTitle(selectedIndividual.getName());
		}
		else setTitle("Unnamed " + selectedIndividual.getSpecies().getName());
	}
}
