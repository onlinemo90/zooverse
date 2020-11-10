package com.zooverse.activities;

import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.ByteArrayDataSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.model.Model;
import com.zooverse.model.Species;

public class SpeciesActivity extends AbstractBaseActivity {
	
	private PlayerControlView playerView;
	private SimpleExoPlayer simplePlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_species);
		
		playerView = findViewById(R.id.exoAudioPlayer);
		simplePlayer = new SimpleExoPlayer.Builder(this).build();
		playerView.setPlayer(simplePlayer);
		
		ImageView playButton = playerView.findViewById(R.id.exo_play);
		ImageView pauseButton = playerView.findViewById(R.id.exo_pause);
		ImageView backButton = playerView.findViewById(R.id.exo_prev);
		playButton.setColorFilter(MainApplication.getThemeColor(R.attr.themeColorPrimary));
		pauseButton.setColorFilter(MainApplication.getThemeColor(R.attr.themeColorForeground));
		backButton.setColorFilter(MainApplication.getThemeColor(R.attr.themeColorForeground));
		
		TextView speciesDescriptionTextView = findViewById(R.id.speciesDescriptionTextView);
		speciesDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
		speciesDescriptionTextView.setOnClickListener(v -> { playerView.hide(); });
		
		Species species = Model.getSpeciesList().get(getIntent().getIntExtra(MainApplication.INTENT_EXTRA_SPECIES, 0));
		setTitle(species.getName());
		ImageView speciesImage = findViewById(R.id.speciesImage);
		speciesImage.setImageBitmap(species.getImage());
		speciesDescriptionTextView.setText(species.getDescription());
		
		byte[] speciesAudio = species.getAudioDescription();
		if (speciesAudio != null) {
			final ByteArrayDataSource dataSource = new ByteArrayDataSource(speciesAudio);
			DataSource.Factory factory = () -> dataSource;
			simplePlayer.setMediaSource(new ProgressiveMediaSource.Factory(factory).createMediaSource(MediaItem.fromUri(Uri.EMPTY)));
			simplePlayer.prepare();
			playerView.hide();
		}
	}
	
	@Override
	protected void onDestroy() {
		playerView.setPlayer(null);
		simplePlayer.release();
		simplePlayer = null;
		super.onDestroy();
	}
	
	public void playAudio(View view) {
		if (!playerView.isVisible()) {
			simplePlayer.play();
			playerView.show();
		} else
			playerView.hide();
	}
	
	public void openSpeciesLocation(View view) {
		//TODO: implement locator screen/logic
	}
	
	public void openSpeciesIndividuals(View view) {
		//TODO: implement individuals screen/logic
	}
}