package com.zooverse.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.activities.GroupActivity;
import com.zooverse.model.Group;

public class GroupsViewPagerAdapter extends RecyclerView.Adapter<GroupsViewPagerAdapter.GroupViewHolder> {
	private final GroupActivity groupActivity;
	Group group;
	
	public GroupsViewPagerAdapter(GroupActivity groupActivity, Group group) {
		this.groupActivity = groupActivity;
		this.group = group;
	}
	
	// inner class for view holder
	public static class GroupViewHolder extends RecyclerView.ViewHolder {
		private final RecyclerView groupMenuRecyclerView = itemView.findViewById(R.id.groupRecyclerView);
		
		public GroupViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
	
	@NonNull
	@Override
	public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View groupViewPagerLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_viewpager_groups, parent, false);
		return new GroupViewHolder(groupViewPagerLayout);
	}
	
	@Override
	public void onBindViewHolder(@NonNull GroupViewHolder viewHolder, int position) {
		if (position == 0) {
			viewHolder.groupMenuRecyclerView.setAdapter(groupActivity.getAdapter());
			viewHolder.groupMenuRecyclerView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
			groupActivity.getAdapter().updateCursor(group);
		} else {
			CustomAttributesAdapter customAttributesAdapter = new CustomAttributesAdapter(group.getAttributes());
			viewHolder.groupMenuRecyclerView.setAdapter(customAttributesAdapter);
			viewHolder.groupMenuRecyclerView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
		}
	}
	
	@Override
	public int getItemCount() {
		return 2;
	}
}
