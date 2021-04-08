package com.zooverse.activities.adapters;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
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
import com.zooverse.activities.SubjectActivity;
import com.zooverse.activities.SubjectLocationActivity;
import com.zooverse.zoo.Individual;
import com.zooverse.zoo.Species;
import com.zooverse.zoo.Subject;

import java.util.List;

public class SubjectPageAdapter extends RecyclerView.Adapter<SubjectPageAdapter.SubjectPageViewHolder> {
	Activity hostActivity;
	List<Subject> subjectList;
	
	public SubjectPageAdapter(Activity hostActivity, List<Subject> subjectList) {
		this.hostActivity = hostActivity;
		this.subjectList = subjectList;
	}
	
	public class SubjectPageViewHolder extends RecyclerView.ViewHolder {
		boolean isDisplayingAttributes = true;
		private Subject subject;
		
		// Subject properties
		private final ImageView subjectImage = itemView.findViewById(R.id.subjectImage);
		private final TextView membersCountTextView = itemView.findViewById(R.id.membersCountTextView);
		private final ImageView subjectGenderIcon = itemView.findViewById(R.id.subjectGenderIcon);
		
		// Demographics
		private final ConstraintLayout demographicsLayout = itemView.findViewById(R.id.demographicsLayout);
		private final TextView demographic1TextView = itemView.findViewById(R.id.demographic1TextView);
		private final TextView demographic2TextView = itemView.findViewById(R.id.demographic2TextView);
		
		// Recycler View
		private final RecyclerView subjectInfoRecyclerView = itemView.findViewById(R.id.subjectInfoRecyclerView);
		
		// Control Buttons
		private final ImageView audioIconImageView = itemView.findViewById(R.id.audioIconImageView);
		private final ImageView locationIconImageView = itemView.findViewById(R.id.locationIconImageView);
		private final ImageView membersIconImageView = itemView.findViewById(R.id.membersIconImageView);
		
		// Audio Player
		private SimpleExoPlayer audioPlayer;
		private final PlayerControlView audioPlayerView = itemView.findViewById(R.id.audioPlayerControlView);
		private final ImageView playButton = audioPlayerView.findViewById(R.id.exo_play);
		private final ImageView pauseButton = audioPlayerView.findViewById(R.id.exo_pause);
		private final ImageView backButton = audioPlayerView.findViewById(R.id.exo_prev);
		
		public SubjectPageViewHolder(@NonNull View itemView) {
			super(itemView);
		}
		
		public void bind(Subject subject) {
			this.subject = subject;
			subjectImage.setImageBitmap(subject.getImage());
			
			// Subject Info RecyclerView
			subjectInfoRecyclerView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
			bindSubjectInfoAdapter(subject);
			
			// Members
			membersIconImageView.setVisibility(View.INVISIBLE);
			membersCountTextView.setVisibility(View.INVISIBLE);
			if (subject.getMembers() != null && subject.getMembers().size() > 0) {
				membersIconImageView.setVisibility(View.VISIBLE);
				membersIconImageView.setOnClickListener(view -> {
					isDisplayingAttributes = !isDisplayingAttributes;
					bindSubjectInfoAdapter(subject);
				});
				membersCountTextView.setVisibility(View.VISIBLE);
				membersCountTextView.setText(String.valueOf(subject.getMembers().size()));
			}
			
			// Location
			locationIconImageView.setVisibility(subject.getLocation() != null ? View.VISIBLE : View.INVISIBLE);
			locationIconImageView.setOnClickListener(view -> {
				Intent intent = new Intent(MainApplication.getContext(), SubjectLocationActivity.class);
				intent.putExtra(SubjectLocationActivity.IntentExtras.SUBJECT_UUID.toString(), subject.getUuid());
				MainApplication.getContext().startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			});
			
			// Audio
			bindAudioPlayer(subject);
			audioIconImageView.setVisibility(subject.getAudio() != null ? View.VISIBLE : View.INVISIBLE);
			if (subject.getAudio() != null) {
				this.audioPlayer.setMediaSource(new ProgressiveMediaSource.Factory(() -> new ByteArrayDataSource(subject.getAudio())).createMediaSource(MediaItem.fromUri(Uri.EMPTY)));
				this.audioPlayer.prepare();
				audioIconImageView.setOnClickListener(view -> {
					if (!this.audioPlayerView.isVisible()) {
						Theme.applyActive(this.audioIconImageView);
						this.audioPlayer.play();
						this.audioPlayerView.show();
					} else {
						Theme.applyDefault(this.audioIconImageView);
						this.audioPlayerView.hide();
					}
				});
			}
			
			// Demographics
			demographicsLayout.setVisibility(View.GONE);
			if (subject instanceof Species) {
				bindDemographics(((Species) subject).getWeight(), ((Species) subject).getSize());
			} else if (subject instanceof Individual) {
				Individual individual = (Individual) subject;
				bindDemographics(individual.getAge(), individual.getPlaceOfBirth());
				
				// Gender
				if (individual.getGender() == Individual.Gender.MALE) {
					subjectGenderIcon.setImageDrawable(ContextCompat.getDrawable(MainApplication.getContext(), R.drawable.icon_gender_male));
				} else if (individual.getGender() == Individual.Gender.FEMALE) {
					subjectGenderIcon.setImageDrawable(ContextCompat.getDrawable(MainApplication.getContext(), R.drawable.icon_gender_female));
				}
			}
		}
		
		private void bindSubjectInfoAdapter(Subject subject) {
			if (isDisplayingAttributes) {
				Theme.applyDefault(membersIconImageView);
				subjectInfoRecyclerView.setAdapter(new SubjectAttributesAdapter(subject));
			} else {
				Theme.applyActive(membersIconImageView);
				subjectInfoRecyclerView.setAdapter(new SubjectCatalogAdapter(subject.getMembers()));
			}
		}
		
		private void bindDemographics(String demographic1Text, String demographic2Text) {
			boolean hasDemographic1 = !("".equals(demographic1Text) || demographic1Text == null);
			boolean hasDemographic2 = !("".equals(demographic2Text) || demographic2Text == null);
			
			if (hasDemographic1 || hasDemographic2) {
				demographicsLayout.setVisibility(View.VISIBLE);
				demographic1TextView.setText(demographic1Text);
				demographic2TextView.setText(demographic2Text);
			}
		}
		
		private void bindAudioPlayer(Subject subject) {
			this.audioPlayer = new SimpleExoPlayer.Builder(hostActivity).build();
			this.audioPlayerView.setPlayer(this.audioPlayer);
			
			// Set Theme
			Theme.applyDefault(this.playButton, this.pauseButton, this.backButton);
			
			// Set notification
			PlayerNotificationManager playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
					hostActivity,
					MainApplication.getContext().getString(R.string.app_name),
					R.string.app_name,
					R.string.app_name,
					R.string.app_name,
					new PlayerNotificationManager.MediaDescriptionAdapter() {
						@NonNull
						@Override
						public CharSequence getCurrentContentTitle(@NonNull Player player) {
							return subject.getName();
						}
						
						@Nullable
						@Override
						public PendingIntent createCurrentContentIntent(@NonNull Player player) {
							Intent intent = new Intent(MainApplication.getContext(), SubjectActivity.class);
							intent.putExtra(SubjectActivity.IntentExtras.SUBJECT_UUID_ARRAY.toString(), new String[]{subject.getUuid()});
							return PendingIntent.getActivity(MainApplication.getContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
						}
						
						@Override
						public CharSequence getCurrentContentText(@NonNull Player player) {
							return "";
						}
						
						@Nullable
						@Override
						public Bitmap getCurrentLargeIcon(@NonNull Player player, @NonNull PlayerNotificationManager.BitmapCallback callback) {
							return subject.getImage();
						}
					}
			);
			playerNotificationManager.setPlayer(this.audioPlayer);
		}
		
		private void pauseAudio(){
			this.audioPlayer.pause();
		}
	}
	
	@NonNull
	@Override
	public SubjectPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new SubjectPageViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_subject, parent, false)
		);
	}
	
	@Override
	public void onBindViewHolder(@NonNull SubjectPageViewHolder viewHolder, int position) {
		viewHolder.bind(this.subjectList.get(position));
	}
	
	@Override
	public void onViewAttachedToWindow(@NonNull SubjectPageViewHolder viewHolder) {
		this.hostActivity.setTitle(viewHolder.subject.getName());
		super.onViewAttachedToWindow(viewHolder);
	}
	
	@Override
	public void onViewDetachedFromWindow(@NonNull SubjectPageViewHolder viewHolder){
		viewHolder.pauseAudio();
	}
	
	@Override
	public int getItemCount() {
		return this.subjectList.size();
	}
}
