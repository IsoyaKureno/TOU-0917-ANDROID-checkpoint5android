package fr.wcs.wcstravel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ResultListAdapter extends ArrayAdapter<TravelModel>{
    ResultListAdapter(Context context, List<TravelModel> travels) {
        super(context, 0, travels);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_travel_row,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.aerialCompagny = convertView.findViewById(R.id.textViewCompany);
            viewHolder.price = convertView.findViewById(R.id.textViewPrice);
            viewHolder.departureDate = convertView.findViewById(R.id.textViewDepartureDate);
            viewHolder.returnDate = convertView.findViewById(R.id.textViewReturnDate);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        TravelModel travel = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.aerialCompagny.setText(travel.getAirline());
        viewHolder.price.setText(travel.getPrice());
        viewHolder.departureDate.setText(travel.getDeparture_date());
        viewHolder.returnDate.setText(travel.getReturn_date());

        return convertView;
    }

    private class TweetViewHolder{
        TextView aerialCompagny;
        TextView price;
        TextView departureDate;
        TextView returnDate;
    }
}
