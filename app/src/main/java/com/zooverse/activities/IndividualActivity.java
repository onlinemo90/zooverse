package com.zooverse.activities;

import android.os.Bundle;
import android.widget.ImageView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.model.Individual;
import com.zooverse.model.Model;
import com.zooverse.model.Species;

public class IndividualActivity extends AbstractBaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual);
		
		Species species = Model.getSpeciesList().get(getIntent().getIntExtra(MainApplication.INTENT_EXTRA_SPECIES, 0));
		Individual individual = species.getIndividualsList().get(0); // TODO: Change this when we can support multiple individuals per species
		
		// Name
		if (!"".equals(individual.getName())){
			setTitle(individual.getName());
		}
		else setTitle("Unnamed " + individual.getSpecies().getName());
		
		// Image
		ImageView individualImage = findViewById(R.id.individualImage);
		if (individual.getImage() != null){
			individualImage.setImageBitmap(individual.getImage());
		}
	}
}
