package com.zooverse.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.zooverse.MainApplication;
import static com.zooverse.MainApplication.getContext;
import com.zooverse.R;
import com.zooverse.model.Model;


public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketListViewHolder> {
	private final TicketOnClickListener ticketOnClickListener;
	
	public TicketListAdapter(TicketOnClickListener ticketOnClickListener) {
		this.ticketOnClickListener = ticketOnClickListener;
	}
	
	// inner class for view holder
	public static class TicketListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private final TextView ticketDateTextView = itemView.findViewById(R.id.ticketDateTextView);
		private final TextView ticketLabelTextView = itemView.findViewById(R.id.ticketLabelTextView);
		private final TicketOnClickListener ticketOnClickListener;
		private final CardView cardView = itemView.findViewById(R.id.ticketCardView);
		private final ImageView ticketIcon = itemView.findViewById(R.id.ticketIcon);
		
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
		View ticketListLayout = LayoutInflater.from(getContext()).inflate(R.layout.layout_recyclerview_tickets, parent, false);
		return new TicketListViewHolder(ticketListLayout, ticketOnClickListener);
	}
	
	@Override
	public void onBindViewHolder(@NonNull TicketListViewHolder viewHolder, int position) {

		if (Model.getStoredTickets().get(position).isForToday()) { // highlight if it is a ticket for today
			viewHolder.cardView.setCardBackgroundColor(MainApplication.getThemeColor(R.attr.themeColorPrimary));
			viewHolder.ticketLabelTextView.setTextColor(MainApplication.getThemeColor(R.attr.themeColorBackground));
			viewHolder.ticketLabelTextView.setText(getContext().getString(R.string.main_ticket_today));
			viewHolder.ticketDateTextView.setText("");
			viewHolder.ticketIcon.setImageDrawable(getContext().getDrawable(R.drawable.icon_walking));
			viewHolder.ticketIcon.setColorFilter(MainApplication.getThemeColor(R.attr.themeColorBackground));
		} else {
			viewHolder.ticketDateTextView.setText(Model.getStoredTickets().get(position).getFormattedDate());
			viewHolder.ticketIcon.setImageDrawable(getContext().getDrawable(R.drawable.icon_calendar));
			MainApplication.setCardViewTheme(viewHolder.cardView);
		}
	}
	
	@Override
	public int getItemCount() {
		return Model.getStoredTickets().size();
	}
	
	public interface TicketOnClickListener {
		void onTicketClick(int position);
	}
}
