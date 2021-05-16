package com.zooverse.activities.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;

import static com.zooverse.MainApplication.getContext;

import com.zooverse.R;
import com.zooverse.Theme;
import com.zooverse.activities.ZooMenuActivity;
import com.zooverse.zoo.Zoo;


public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketListViewHolder> {
	// inner class for view holder
	public static class TicketListViewHolder extends RecyclerView.ViewHolder {
		private final TextView ticketDateTextView = itemView.findViewById(R.id.ticketDateTextView);
		private final TextView ticketLabelTextView = itemView.findViewById(R.id.ticketLabelTextView);
		private final CardView cardView = itemView.findViewById(R.id.ticketCardView);
		private final ImageView ticketIcon = itemView.findViewById(R.id.ticketIcon);
		
		public TicketListViewHolder(@NonNull View itemView) {
			super(itemView);
			itemView.setOnClickListener(view -> {
				if (Zoo.getStoredTickets().get(getAdapterPosition()).isForToday()) {
					MainApplication.getContext().startActivity(
							new Intent(MainApplication.getContext(), ZooMenuActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
					);
				}
			});
		}
		
		public void bind(int position){
			if (Zoo.getStoredTickets().get(position).isForToday()) { // highlight if it is a ticket for today
				Theme.apply(Theme.Mode.ACTIVE, this.cardView);
				Theme.apply(Theme.Mode.BACKGROUND, this.ticketLabelTextView);
				ticketLabelTextView.setText(getContext().getString(R.string.ticket_list_ticket_today));
				this.ticketDateTextView.setText("");
				this.ticketIcon.setImageDrawable(ContextCompat.getDrawable(MainApplication.getContext(), R.drawable.icon_walking));
				Theme.apply(Theme.Mode.BACKGROUND, this.ticketIcon);
			} else {
				Theme.apply(Theme.Mode.DEFAULT, this.cardView);
				this.ticketDateTextView.setText(Zoo.getStoredTickets().get(position).getFormattedDate());
				this.ticketIcon.setImageDrawable(ContextCompat.getDrawable(MainApplication.getContext(), R.drawable.icon_calendar));
			}
		}
	}
	
	@NonNull
	@Override
	public TicketListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View ticketListLayout = LayoutInflater.from(getContext()).inflate(R.layout.layout_recyclerview_tickets, parent, false);
		return new TicketListViewHolder(ticketListLayout);
	}
	
	@Override
	public void onBindViewHolder(@NonNull TicketListViewHolder viewHolder, int position) {
		viewHolder.bind(position);
	}
	
	@Override
	public int getItemCount() {
		return Zoo.getStoredTickets().size();
	}
	
}
