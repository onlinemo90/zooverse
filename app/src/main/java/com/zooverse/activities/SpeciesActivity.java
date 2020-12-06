package com.zooverse.activities;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.upstream.ByteArrayDataSource;
import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Theme;
import com.zooverse.model.Model;
import com.zooverse.model.Species;

public class SpeciesActivity extends AbstractBaseActivity {
	private final int PREVIOUS_SPECIES_POSITION = -1;
	private final int CURRENT_SPECIES_POSITION = 0;
	private final int NEXT_SPECIES_POSITION = 1;
	
	private Species species;
	
	private PlayerControlView playerView;
	private SimpleExoPlayer simplePlayer;
	private PlayerNotificationManager playerNotificationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_species);
		this.enableOptionsMenu(R.menu.menu_bar_species);
		
		this.initExoPlayer();
		this.populateSpeciesPage(getIntent().getIntExtra(MainApplication.INTENT_EXTRA_SPECIES_ID,1), CURRENT_SPECIES_POSITION);
	}
	
	@SuppressLint("NonConstantResourceId")
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.previousSpeciesMenuItem:
				populateSpeciesPage(this.species.getId(), PREVIOUS_SPECIES_POSITION);
				break;
			case R.id.nextSpeciesMenuItem:
				populateSpeciesPage(this.species.getId(), NEXT_SPECIES_POSITION);
				break;
			default:
				super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	private void populateSpeciesPage(int speciesId, int requestedSpecies) {
		// identify species position in sorted list
		int speciesPosition = -1;
		for (Species species : Model.getSortedSpeciesList()) {
			speciesPosition++;
			if (species.getId() == speciesId)
				break;
		}
		// Did user press previous or next button or opened species page the 1st time?
		speciesPosition += requestedSpecies;
		
		// Does requested position exist?
		if (speciesPosition > -1 && speciesPosition < Model.getSortedSpeciesList().size()) {
			ImageView speciesImage = findViewById(R.id.speciesImage);
			TextView speciesDescriptionTextView = findViewById(R.id.speciesDescriptionTextView);
			TextView individualsCountTextView = findViewById(R.id.individualsCountTextView);
			
			this.species = Model.getSortedSpeciesList().get(speciesPosition);
			setTitle(this.species.getName());
			
			if (this.species.getImage() != null)
				speciesImage.setImageBitmap(this.species.getImage());
			
			individualsCountTextView.setText(Integer.toString(this.species.getIndividualsList().size()));
			individualsCountTextView.setTextColor(Theme.getColor(R.attr.themeColorBackground));
			
			speciesDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
			speciesDescriptionTextView.setOnClickListener(v -> playerView.hide());
			speciesDescriptionTextView.setText(this.species.getDescription());
			
			simplePlayer.stop();
			playerView.hide();
			byte[] speciesAudio = species.getAudioDescription();
			if (speciesAudio != null) {
				simplePlayer.setMediaSource(new ProgressiveMediaSource.Factory(() -> new ByteArrayDataSource(speciesAudio)).createMediaSource(MediaItem.fromUri(Uri.EMPTY)));
				simplePlayer.prepare();
			}
		}
	}
	
	private void initExoPlayer() {
		simplePlayer = new SimpleExoPlayer.Builder(this).build();
		playerView = findViewById(R.id.exoAudioPlayer);
		playerView.setPlayer(simplePlayer);
	
		ImageView playButton = playerView.findViewById(R.id.exo_play);
		ImageView pauseButton = playerView.findViewById(R.id.exo_pause);
		ImageView backButton = playerView.findViewById(R.id.exo_prev);
		playButton.setColorFilter(Theme.getColor(R.attr.themeColorExoPlayerButtons));
		pauseButton.setColorFilter(Theme.getColor(R.attr.themeColorExoPlayerButtons));
		backButton.setColorFilter(Theme.getColor(R.attr.themeColorExoPlayerButtons));
		
		playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
				this,
				MainApplication.getContext().getString(R.string.app_name),
				R.string.app_name,
				R.string.app_name,
				R.string.app_name,
				new PlayerNotificationManager.MediaDescriptionAdapter() {
					@Override
					public CharSequence getCurrentContentTitle(Player player) {
						return species.getName();
					}
					
					@Nullable
					@Override
					public PendingIntent createCurrentContentIntent(Player player) {
						Intent intent = new Intent(MainApplication.getContext(), SpeciesActivity.class);
						intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, species.getId());
						return PendingIntent.getActivity(MainApplication.getContext(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
					}
					
					@Nullable
					@Override
					public CharSequence getCurrentContentText(Player player) {
						return species.getDescription();
					}
					
					@Nullable
					@Override
					public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
						return species.getImage();
					}
				});
		playerNotificationManager.setPlayer(simplePlayer);
	}
	
	@Override
	protected void onDestroy() {
		playerView.setPlayer(null);
		simplePlayer.release();
		simplePlayer = null;
		super.onDestroy();
	}
	
	public void playAudio(View view) {
		if (this.species.getAudioDescription() != null) {
			if (!playerView.isVisible()) {
				simplePlayer.play();
				playerView.show();
			} else
				playerView.hide();
		}
	}
	
	public void openSpeciesLocation(View view) {
		//TODO: implement locator screen/logic
	}
	
	public void openSpeciesIndividuals(View view) {
		if (this.species.getIndividualsList().size() > 0) {
			Intent intent = new Intent(MainApplication.getContext(), IndividualsActivity.class);
			intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, this.species.getId());
			startActivity(intent);
		}
	}
}