package com.zooverse.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.model.Model;
import com.zooverse.model.Ticket;

import java.util.List;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketListViewHolder> {
	private List<Ticket> ticketList = Model.getStoredTickets();
	private View ticketListLayout;
	private TicketOnClickListener ticketOnClickListener;
	
	public TicketListAdapter(TicketOnClickListener ticketOnClickListener){
		this.ticketOnClickListener = ticketOnClickListener;
	}
	
	// inner class for view holder
	public static class TicketListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		TextView ticketDateTextView = itemView.findViewById(R.id.ticketDateTextView);
		TextView ticketLabelTextView = itemView.findViewById(R.id.ticketLabelTextView);
		TicketOnClickListener ticketOnClickListener;
		
		public TicketListViewHolder(@NonNull View itemView, TicketOnClickListener ticketOnClickListener) {
			super(itemView);
			this.ticketOnClickListener = ticketOnClickListener;
			itemView.setOnClickListener(this);
		}
		
		@Override //on click method references interface for on click implementation
		public void onClick(View v) {
			ticketOnClickListener.onTicketClick(getAdapterPosition());
		}
	}
	
	@NonNull
	@Override
	public TicketListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// assign layout file which is used for each row in recycler view
		ticketListLayout = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.layout_recyclerview_tickets, parent, false);
		return new TicketListViewHolder(ticketListLayout, ticketOnClickListener);
	}
	
	@Override
	public void onBindViewHolder(@NonNull TicketListViewHolder viewHolder, int position) {
		CardView cardView = ticketListLayout.findViewById(R.id.ticketCardView);
		if (this.ticketList.get(position).isForToday()){ // highlight if it is a ticket for today
			cardView.setCardBackgroundColor(MainApplication.getThemeColor(R.attr.themeColorPrimary));
			viewHolder.ticketLabelTextView.setTextColor(MainApplication.getThemeColor(R.attr.themeColorBackground));
			viewHolder.ticketLabelTextView.setText(MainApplication.getContext().getString(R.string.main_ticket_today));
			viewHolder.ticketDateTextView.setText("");
		}
		else {
			cardView.setCardBackgroundColor(MainApplication.getThemeColor(R.attr.themeColorCardBackground));
			viewHolder.ticketDateTextView.setText(this.ticketList.get(position).getReadableDate());
		}
	}
	
	@Override
	public int getItemCount() {
		return this.ticketList.size();
	}
	
	public interface TicketOnClickListener {
		void onTicketClick(int position);
	}
}
