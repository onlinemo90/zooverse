package com.zooverse.activities;

import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.ByteArrayDataSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.zooverse.R;
import com.zooverse.model.Model;
import com.zooverse.model.Species;

public class SpeciesDetailsActivity extends AbstractBaseActivity {
	
	private PlayerControlView playerview;
	private SimpleExoPlayer simpleplayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_species_details);
		
		playerview = findViewById(R.id.exoAudioPlayer);
		simpleplayer = new SimpleExoPlayer.Builder(this).build();
		playerview.setPlayer(simpleplayer);
		byte[] speciesAudio = null;
		
		TextView speciesDescriptionTextView = findViewById(R.id.speciesDescriptionTextView);
		speciesDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
		ImageView speciesImage = findViewById(R.id.speciesImage);
		
		String speciesTitle = getIntent().getStringExtra("SPECIES_NAME");
		setTitle(speciesTitle);
		
		for (Species species : Model.getSpeciesList()) {
			if (species.getName().toLowerCase().startsWith(speciesTitle.toLowerCase())) {
				speciesImage.setImageBitmap(species.getImage());
				speciesDescriptionTextView.setText(species.getDescription());
				speciesAudio = species.getAudioDescription();
				break;
			}
		}
		
		if (speciesAudio != null) {
			final ByteArrayDataSource dataSource = new ByteArrayDataSource(speciesAudio);
			DataSource.Factory factory = () -> dataSource;
			simpleplayer.setMediaSource(new ProgressiveMediaSource.Factory(factory).createMediaSource(MediaItem.fromUri(Uri.EMPTY)));
			simpleplayer.prepare();
		}
	}
	
	@Override
	protected void onDestroy() {
		playerview.setPlayer(null);
		simpleplayer.release();
		simpleplayer = null;
		super.onDestroy();
	}
}