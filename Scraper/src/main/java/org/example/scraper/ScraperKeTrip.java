package org.example.scraper;


import org.example.model.Itinerary;
import org.example.model.Trip;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScraperKeTrip {

    private Document doc;

    private String getTitle(){
        Element content = doc.select("h1.hero-title").first();
        return content.text();
    }

    private String getDestination(){
        Element content = doc.select("h2.nazione-dettaglio").first();
        return content.text();
    }

    private String getDescription(){

        Element content = doc.select("ul.descrizione-viaggio").first();
        return content.text();
    }

    private Date getDateFrom(){

        try{
            Element content = doc.select("div.date-from").get(1);
            String date = content.text().split(" ")[1];
            date = date.replace("\\", "");
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
            //return LocalDate.parse();
        }catch(IndexOutOfBoundsException e) {
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Date getDateTo(){

        try{
            Element content = doc.select("div.date-to").get(1);
            String date = content.text().split(" ")[1];
            date = date.replace("\\", "");
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        }catch(IndexOutOfBoundsException e) {
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private int getPrice(){

        try{
            Element content = doc.select("div.price").get(2);
            String price = content.text().split(" ")[1];
            int p = Integer.parseInt(price.substring(0, price.length()-1));
            return p;
        }catch(IndexOutOfBoundsException e) {
            // sometimes this information is not available
            return -1;
        }
    }

    private ArrayList<Itinerary> getItinerary() {
        ArrayList<Itinerary> itinerary = new ArrayList<Itinerary>();
        Elements contents = doc.select("div.panel.itinerary-item");

        int i = 0;
        for (Element el : contents){

            Itinerary it = new Itinerary();
            it.setTitle(el.select("a").text());
            it.setDay(++i);
            it.setDescription(el.select("p").text());
            itinerary.add(it);
        }
        return  itinerary;
    }


    private List<String> getWhatsIncluded() {
        ArrayList<String> whatsincluded = new ArrayList<String>();
        Elements contents = doc.select("ul.list-with-icon.with-heading");
        for(Element el : contents.select("p")){
            if (! el.text().equals(""))
                whatsincluded.add(el.text());
        }
        return whatsincluded.subList(0, whatsincluded.size()/2);
    }


    private List<String> getWhatsNotIncluded() {
        ArrayList<String> whatsincluded = new ArrayList<String>();
        Elements contents = doc.select("ul.list-with-icon.with-heading");
        for(Element el : contents.select("p")){
            if (! el.text().equals(""))
                whatsincluded.add(el.text());
        }
        return whatsincluded.subList(whatsincluded.size()/2, whatsincluded.size()-1);
    }

    private ArrayList<String> getLinks(){
        ArrayList<String> trips = new ArrayList<>();

        trips.add("https://ketrip.it/viaggi/marocco-viaggio-gruppo-fotografico-capodanno");
        trips.add("https://ketrip.it/viaggi/fuoristrada-marocco-viaggio-gruppo");
        trips.add("https://ketrip.it/viaggi/namibia-avventura-fuoristrada");
        trips.add("https://ketrip.it/viaggi/viaggio-gruppo-seychelles#");
        trips.add("https://ketrip.it/viaggi/amazzonia-avventura");
        trips.add("https://ketrip.it/viaggi/brasile-nord-est");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-in-canada-on-the-road");
        trips.add("https://ketrip.it/viaggi/viaggio-gruppo-in-colombia");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-costa-rica");
        trips.add("https://ketrip.it/viaggi/avventura-guatemala");
        trips.add("https://ketrip.it/viaggi/baja-california-messico");
        trips.add("https://ketrip.it/viaggi/messico-dia-de-los-muertos");
        trips.add("https://ketrip.it/viaggi/yucatan-messico");
        trips.add("https://ketrip.it/viaggi/viaggio-gruppo-peru-machu-picchu");
        trips.add("https://ketrip.it/viaggi/stati-uniti-viaggio-di-gruppo-las-vegas#");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-hawaii-trekking");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-alaska-magic-bus");
        trips.add("https://ketrip.it/viaggi/new-york");
        trips.add("https://ketrip.it/viaggi/stati-uniti-grandi-parchi");
        trips.add("https://ketrip.it/viaggi/stati-uniti-trekking-experience");
        trips.add("https://ketrip.it/viaggi/stati-uniti-viaggio-usa-camper");
        trips.add("https://ketrip.it/viaggi/giordania-viaggio-di-gruppo");
        trips.add("https://ketrip.it/viaggi/viaggi-gruppo-capodanno-giordania");
        trips.add("https://ketrip.it/viaggi/viaggi-gruppo-avventura-trekking-giordania-epifania");
        trips.add("https://ketrip.it/viaggi/viaggio-gruppo-bali-lombok");
        trips.add("https://ketrip.it/viaggi/viaggio-gruppo-indonesia-sumatra");
        trips.add("https://ketrip.it/viaggi/viaggio-gruppo-in-rajasthan");
        trips.add("https://ketrip.it/viaggi/india-del-nord");
        trips.add("https://ketrip.it/viaggi/giappone-fioritura-ciliegi");
        trips.add("https://ketrip.it/viaggi/giappone-viaggio-fotografico");
        trips.add("https://ketrip.it/viaggi/giappone-capodanno");
        trips.add("https://ketrip.it/viaggi/giappone-tokyo-kyoto");
        trips.add("https://ketrip.it/viaggi/giappone-tradizione-avanguardia");
        trips.add("https://ketrip.it/viaggi/kirghizistan-paese-nomadi");
        trips.add("https://ketrip.it/viaggi/myamnar-viaggio-di-gruppo");
        trips.add("https://ketrip.it/viaggi/trekking-santuario-annapurna");
        trips.add("https://ketrip.it/viaggi/trekking-campo-base-everest");
        trips.add("https://ketrip.it/viaggi/viaggio-gruppo-nepal");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-kamchatka");
        trips.add("https://ketrip.it/viaggi/thailandia-viaggio-gruppo");
        trips.add("https://ketrip.it/viaggi/thailandia-natura-tradizione");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-in-vietnam");
        trips.add("https://ketrip.it/viaggi/vietnam-avventura");
        trips.add("https://ketrip.it/viaggi/Viaggio-gruppo-vietnam-cambogia-lungo-mekong");
        trips.add("https://ketrip.it/viaggi/viaggio-canarie-fuoristrada");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-trekking-canarie");
        trips.add("https://ketrip.it/viaggi/viaggio-in-groenlandia");
        trips.add("https://ketrip.it/viaggi/grecia-meteore");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-santorini");
        trips.add("https://ketrip.it/viaggi/islanda-invernale-aurore");
        trips.add("https://ketrip.it/viaggi/islanda-sud-5-giorni");
        trips.add("https://ketrip.it/viaggi/islanda-trekking-experience");
        trips.add("https://ketrip.it/viaggi/workshop-fotografico-islanda-aurore");
        trips.add("https://ketrip.it/viaggi/islanda-ring-road");
        trips.add("https://ketrip.it/viaggi/islanda-selvaggi-westfjords");
        trips.add("https://ketrip.it/viaggi/islanda-ring-road-viaggio-fotografico");
        trips.add("https://ketrip.it/viaggi/via-degli-dei-cammino");
        trips.add("https://ketrip.it/viaggi/norvegia-lofoten-aurora-inverno");
        trips.add("https://ketrip.it/viaggi/norvegia-isole-lofoten-capo-nord");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-norvegia-caponord");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-portogallo-algarve");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-trekking-azzorre");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-portogallo-trekking");
        trips.add("https://ketrip.it/viaggi/viaggio-di-gruppo-trekking-hiking-madeira");
        trips.add("https://ketrip.it/viaggi/lapponia-aurore-boreali");
        trips.add("https://ketrip.it/viaggi/capodanno-lapponia-aurora-boreale");
        trips.add("https://ketrip.it/viaggi/lapponia-aurora-hotel-ghiaccio");
        trips.add("https://ketrip.it/viaggi/cappadocia-istanbul");

        return trips;
    }



    private void executeScraping(){

        ArrayList<String> links = getLinks();

        ArrayList<Trip> trips = new ArrayList<Trip>();

        for(String trip_webpage : links){
            System.out.println(trip_webpage);

            Trip trip = new Trip();
            try {
                doc = Jsoup.connect(trip_webpage).get();
            } catch (IOException e) {
                System.out.println("Error");
                throw new RuntimeException(e);
            }

            trip.setTitle(this.getTitle());
            trip.setDescription(this.getDescription());
            trip.setDestination(this.getDestination());
            trip.setDepartureDate(this.getDateFrom());
            trip.setReturnDate(this.getDateTo());
            trip.setPrice(this.getPrice());
            trip.setItinerary(this.getItinerary());
            trip.setCompreso(this.getWhatsIncluded());
            trip.setNonCompreso(this.getWhatsNotIncluded());

            trips.add(trip);
            System.out.println(trip.toString());
        }


    }

    public static void main(String[] args) {
        ScraperKeTrip scraperKeTrip = new ScraperKeTrip();
        scraperKeTrip.executeScraping();
    }

}
