package com.zooverse.activities.adapters;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.zooverse.activities.IndividualsActivity;
import com.zooverse.activities.SpeciesActivity;
import com.zooverse.activities.SpeciesLocationActivity;
import com.zooverse.model.Model;
import com.zooverse.model.Species;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpeciesViewPagerAdapter extends RecyclerView.Adapter<SpeciesViewPagerAdapter.SpeciesViewHolder> {
	public Map<SimpleExoPlayer, PlayerControlView> activePlayers = new HashMap<>();
	List<Species> speciesList;
	SpeciesActivity currentActivity;
	
	public SpeciesViewPagerAdapter(List<Species> species, SpeciesActivity speciesActivity) {
		this.speciesList = species;
		this.currentActivity = speciesActivity;
	}
	
	// inner class for view holder
	public static class SpeciesViewHolder extends RecyclerView.ViewHolder {
		Species species;
		
		ImageView subjectImage = itemView.findViewById(R.id.subjectImage);
		ImageView childImageView = itemView.findViewById(R.id.childImageView);
		ImageView locationImageView = itemView.findViewById(R.id.locationImageView);
		ImageView audioImageView = itemView.findViewById(R.id.audioImageView);
		TextView childCountTextView = itemView.findViewById(R.id.childCountTextView);
		TextView speciesWeightTextView = itemView.findViewById(R.id.mainAttribute2TextView);
		TextView speciesSizeTextView = itemView.findViewById(R.id.mainAttribute1TextView);
		RecyclerView subjectRecyclerView = itemView.findViewById(R.id.subjectRecyclerView);
		
		PlayerControlView playerView = itemView.findViewById(R.id.exoAudioPlayer);
		SimpleExoPlayer simplePlayer;
		PlayerNotificationManager playerNotificationManager;

		
		public SpeciesViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
	
	@NonNull
	@Override
	public SpeciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View individualViewPagerLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_subject, parent, false);
		return new SpeciesViewHolder(individualViewPagerLayout);
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void onBindViewHolder(@NonNull SpeciesViewHolder viewHolder, int position) {
		
		viewHolder.species = speciesList.get(position);
		initExoPlayer(viewHolder, viewHolder.species);
		
		viewHolder.subjectImage.setImageBitmap(viewHolder.species.getImage());
		viewHolder.childCountTextView.setVisibility(View.VISIBLE);
		viewHolder.childImageView.setVisibility(View.VISIBLE);
		viewHolder.audioImageView.setVisibility(View.VISIBLE);
		viewHolder.locationImageView.setVisibility(View.VISIBLE);
		// show numbers of individuals, if 0 fade icon
		
		if (viewHolder.species.getIndividuals().size() > 0){
			viewHolder.childCountTextView.setVisibility(View.VISIBLE);
			viewHolder.childCountTextView.setText(Integer.toString(viewHolder.species.getIndividuals().size()));
			viewHolder.childCountTextView.setTextColor(Theme.getColor(R.attr.themeColorBackground));
			viewHolder.childImageView.setColorFilter(Theme.getColor(R.attr.themeColorPrimary));
			viewHolder.childImageView.setOnClickListener(view -> {
				Intent intent = new Intent(view.getContext(), IndividualsActivity.class);
				intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, viewHolder.species.getId());
				view.getContext().startActivity(intent);
			});
		} else {
			viewHolder.childCountTextView.setVisibility(View.INVISIBLE);
			viewHolder.childImageView.setColorFilter(Theme.getColor(R.attr.themeColorForegroundFaded));
		}
		
		// fade location button if no location available
		
		if (viewHolder.species.getLocation() == null)
			viewHolder.locationImageView.setColorFilter(Theme.getColor(R.attr.themeColorForegroundFaded));
		else
			viewHolder.locationImageView.setColorFilter(Theme.getColor(R.attr.themeColorPrimary));
		
		viewHolder.locationImageView.setOnClickListener(view -> {
			if (viewHolder.species.getLocation() != null) {
				Intent intent = new Intent(view.getContext(), SpeciesLocationActivity.class);
				intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, viewHolder.species.getId());
				view.getContext().startActivity(intent);
			}
		});
		
		//fade audio button if no audio available
		
		byte[] speciesAudio = viewHolder.species.getAudio();
		if (speciesAudio == null)
			viewHolder.audioImageView.setColorFilter(Theme.getColor(R.attr.themeColorForegroundFaded));
		else
			viewHolder.audioImageView.setColorFilter(Theme.getColor(R.attr.themeColorPrimary));
		
		viewHolder.audioImageView.setOnClickListener(view -> {

			if (speciesAudio != null) {
				if (!viewHolder.playerView.isVisible()) {
					viewHolder.simplePlayer.play();
					viewHolder.playerView.show();
				} else
					viewHolder.playerView.hide();
			}
		});
		
		if (viewHolder.species.getSize() != null) {
			viewHolder.speciesSizeTextView.setText(viewHolder.species.getSize());
			viewHolder.speciesSizeTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_size, 0, 0, 0);
		} else
			viewHolder.speciesSizeTextView.setVisibility(View.GONE);
		
		if (viewHolder.species.getWeight() != null) {
			viewHolder.speciesWeightTextView.setText(viewHolder.species.getWeight());
			viewHolder.speciesWeightTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_weight, 0, 0, 0);
		} else
			viewHolder.speciesWeightTextView.setVisibility(View.GONE);
		
		// Loading RecyclerView with custom attributes
		CustomAttributesAdapter customAttributesAdapter = new CustomAttributesAdapter(viewHolder.species.getAttributes());
		viewHolder.subjectRecyclerView.setAdapter(customAttributesAdapter);
		viewHolder.subjectRecyclerView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
		viewHolder.subjectRecyclerView.setOnTouchListener((v, event) -> {
			//handle only simple touch
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				viewHolder.playerView.hide();
				return true;
			}
			//leave other touch events like scrolling to recyclerView
			return false;
		});
		
		viewHolder.simplePlayer.stop();
		viewHolder.playerView.hide();
		if (speciesAudio != null) {
			viewHolder.simplePlayer.setMediaSource(new ProgressiveMediaSource.Factory(() -> new ByteArrayDataSource(speciesAudio)).createMediaSource(MediaItem.fromUri(Uri.EMPTY)));
			viewHolder.simplePlayer.prepare();
		}
	}
	
	
	@Override
	public int getItemCount() {
		return speciesList.size();
	}
	
	private void initExoPlayer(SpeciesViewHolder viewHolder, Species species) {
		viewHolder.simplePlayer = new SimpleExoPlayer.Builder(currentActivity).build();
		viewHolder.playerView.setPlayer(viewHolder.simplePlayer);
		
		activePlayers.put(viewHolder.simplePlayer, viewHolder.playerView);
		
		ImageView playButton = viewHolder.playerView.findViewById(R.id.exo_play);
		ImageView pauseButton = viewHolder.playerView.findViewById(R.id.exo_pause);
		ImageView backButton = viewHolder.playerView.findViewById(R.id.exo_prev);
		playButton.setColorFilter(Theme.getColor(R.attr.themeColorExoPlayerButtons));
		pauseButton.setColorFilter(Theme.getColor(R.attr.themeColorExoPlayerButtons));
		backButton.setColorFilter(Theme.getColor(R.attr.themeColorExoPlayerButtons));

		viewHolder.playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
				currentActivity,
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
						ArrayList<Integer> speciesList = new ArrayList<>();
						for (Species species : Model.getSortedSpeciesList())
							speciesList.add(species.getId());
						Intent intent = new Intent(MainApplication.getContext(), SpeciesActivity.class);
						intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, species.getId());
						intent.putIntegerArrayListExtra(MainApplication.INTENT_EXTRA_SPECIES_VIEWPAGER_FILTERED_LIST, speciesList);
						return PendingIntent.getActivity(MainApplication.getContext(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
					}
					
					@Nullable
					@Override
					public CharSequence getCurrentContentText(Player player) {
						return "";
					}
					
					@Nullable
					@Override
					public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
						return species.getImage();
					}
				});
		viewHolder.playerNotificationManager.setPlayer(viewHolder.simplePlayer);
	}
	
	@Override
	public void onViewAttachedToWindow(@NonNull SpeciesViewHolder viewHolder) {
		currentActivity.setTitle(viewHolder.species.getName());
		super.onViewAttachedToWindow(viewHolder);
	}
}


