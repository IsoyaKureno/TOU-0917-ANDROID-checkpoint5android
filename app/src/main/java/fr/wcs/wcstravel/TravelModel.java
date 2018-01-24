package fr.wcs.wcstravel;

public class TravelModel {
    private String airline, departure_date, price, return_date, travel;

    TravelModel(String airline,String departure_date,String price,
                String return_date,String travel){
        this.airline = airline;
        this.departure_date = departure_date;
        this.price = price;
        this.return_date = return_date;
        this.travel = travel;
    }

    public String getAirline() {return airline;}

    public String getDeparture_date() {return departure_date;}

    public String getPrice() {return price;}

    public String getReturn_date() {return return_date;}

    public String getTravel() {return travel;}
}
