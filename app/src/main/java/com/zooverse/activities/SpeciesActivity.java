package com.zooverse.activities;


import android.os.Bundle;

import androidx.viewpager2.widget.ViewPager2;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.activities.adapters.SpeciesViewPagerAdapter;
import com.zooverse.model.Model;
import com.zooverse.model.Species;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpeciesActivity extends AbstractBaseActivity {
	
	SpeciesViewPagerAdapter viewPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_species);
		this.enableOptionsMenu();
		
		Map<Integer, Species> speciesMap = Model.getSpecies();
		List<Species> filteredSortedSpeciesList = new ArrayList<>();
		
		for (Integer speciesId : getIntent().getIntegerArrayListExtra(MainApplication.INTENT_EXTRA_SPECIES_VIEWPAGER_FILTERED_LIST)) {
			filteredSortedSpeciesList.add(speciesMap.get(speciesId));
		}
		
		// identify species position in sorted list
		int speciesPosition = -1;
		for (Species species : filteredSortedSpeciesList) {
			speciesPosition++;
			if (species.getId() == getIntent().getIntExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, 1))
				break;
		}
		
		ViewPager2 viewPager = findViewById(R.id.speciesViewPager);
		
		viewPagerAdapter = new SpeciesViewPagerAdapter(filteredSortedSpeciesList, this);
		viewPager.setAdapter(viewPagerAdapter);
		viewPager.setCurrentItem(speciesPosition);
	}
	
	
	
	@Override
	protected void onDestroy() {
			viewPagerAdapter.activePlayers.forEach((simplePlayer, playerView) -> {
				playerView.setPlayer(null);
				simplePlayer.release();
				simplePlayer = null;
			});
		super.onDestroy();
	}
}