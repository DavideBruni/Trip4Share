package org.example.scraper;

import org.example.model.Itinerary;
import org.example.model.Trip;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.google.gson.*;

public class ScraperSiVola
{

    public Set getLinks(String webPage) throws IOException {

        Set set = new HashSet();
        String html = Jsoup.connect(webPage).get().html();
        Document doc = Jsoup.parse(html);

        Element availableTrips = doc.getElementById("disponibili");
        Element soldOutTrips = doc.getElementById("soldout");
        Elements availableElements = availableTrips.getElementsByTag("a");
        Elements soldOutElements = soldOutTrips.getElementsByTag("a");
        for (Element e : availableElements)
            set.add("https://www.sivola.it"+e.attr("href"));
        for (Element e : soldOutElements)
            set.add("https://www.sivola.it"+e.attr("href"));
        return set;
    }

    private static List<String> removeLongest(List<String> p){
        if(p.size()<3)
            return p;
        int maxLen = p.get(0).length();
        int index = 0;
        int i = 0;
        for(String x : p){
            if(x.length()>maxLen){
                maxLen = x.length();
                index = i;
            }
            i++;
        }
        p.remove(index);
        return p;
    }

    private List<String> getTags(String html) throws IOException {
        Document doc = Jsoup.parse(html);
        Element element = doc.getElementsByClass("label mb-4").first();
        Elements divs = element.getElementsByTag("div");
        List<String> ret = new ArrayList<>();
        Set<String> s = new HashSet<>();
        for(Element e : divs)
            s.add(e.text());
        Iterator<String> iterator = s.iterator();
        while(iterator.hasNext()) {
            ret.add(iterator.next());
        }
        ret = removeLongest(ret);
        return ret;
    }

    private String getTitle(String html){
        Document doc = Jsoup.parse(html);
        Element element = doc.getElementsByClass("unresponsive font-60 font-weight-bold text-white font-sivola").first();
        return element.text();

    }

    private String getDescription(String html){
        Document doc = Jsoup.parse(html);
        Element element = doc.getElementsByClass("col-md-12 col-lg-7").get(1);
        Elements paragraphs = element.getElementsByTag("p");
        StringBuilder s = new StringBuilder();
        for(Element e : paragraphs) {
            try{
                e.getElementsByTag("a").first().text();     //adv nuovi viaggi, skip p
                continue;
            }catch (Exception ex){

            }
            String string = e.text();
            String newString = string.replace("PARTENZE DISPONIBILI PER:Clicca su \\\"scopri date\\\" per saperne di pi","");
            string = newString.replace("GUARDA ANCHE GLI ALTRI ITINERARI","");
            s.append(string);
        }
        return String.valueOf(s);
    }

    private String getOrganizer(String html){
        Document doc = Jsoup.parse(html);
        Element element = doc.getElementsByClass("col-lg-4 mt-4 mt-lg-5 pt-4 pt-lg-5 accompagnatori").first();
        String organizer ="";
        try {
            Element orgLi = element.getElementsByTag("li").first();
            organizer = orgLi.getElementsByTag("img").first().attr("alt");
        }catch(Exception exception){
            return null;
        }
        return organizer;
    }

    private List<Itinerary> getItinerary(String html){
        Document doc = Jsoup.parse(html);
        Element element = doc.getElementsByClass("col-12 mt-4").get(0);     //the second one
        Elements divs = element.getElementsByClass("card-home card-tappa rounded p-4");
        List<Itinerary> iter = new ArrayList<>();
        int i = 1;
        for(Element div : divs){
            Itinerary it = new Itinerary();
            String title = div.getElementsByTag("h2").first().text();
            it.setTitle(title);
            String subTitle = div.getElementsByTag("p").first().text();
            it.setSubtitle(subTitle);
            StringBuilder description = new StringBuilder();
            try {
                description.append(div.getElementsByTag("p").get(1).text());
                it.setDescription(String.valueOf(description));
            }catch (IndexOutOfBoundsException e){

            }
            it.setDay(i);
            iter.add(it);
            i++;
        }
        return iter;
    }

    private static String parseMonth(String data){
        String firstLetter = data.substring(0,1).toUpperCase();
        String restLetters = data.substring(1).toLowerCase();
        return firstLetter + restLetters;
    }
    private List<Trip> getSingleTripInfo(String html) throws ParseException {
        Document doc = Jsoup.parse(html);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ITALIAN);
        List<Trip> trips = new ArrayList<>();
        Elements elements = doc.getElementsByClass("card-home rounded p-4 d-flex justify-content-between flex-column flex-sm-row");
        for(Element e : elements) {
            int day = Integer.parseInt(e.getElementsByTag("strong").first().text());
            String month = e.getElementsByTag("span").first().text();
            int year = 2023;
            if (month.equals("DIC"))
                year = 2022;
            Date depDate = formatter.parse(day+"-"+parseMonth(month)+"-"+year);

            String durationS = e.getElementsByTag("span").get(1).text();
            int duration = Integer.parseInt(durationS.replaceAll("[\\D]", ""));
            Calendar c = Calendar.getInstance();
            c.setTime(depDate);
            c.add(Calendar.DATE, duration);
            Date returnDate = c.getTime();

            Element div = e.getElementsByClass("mr-lg-4 d-flex text-small align-items-center flex-wrap justify-content-center").first();
            String priceS = div.getElementsByClass("d-block font-weight-bolder h5 mb-0").first().text();
            int price = Integer.parseInt(priceS.replaceAll("[\\D]", ""));

            Trip t = new Trip();
            t.setDepartureDate(depDate);
            t.setReturnDate(returnDate);
            t.setPrice(price);
            trips.add(t);
        }
        return trips;
    }


    public List<String> getTravelPerDestination(String html, String dest) throws ParseException, IOException {
        List<Trip> trips = getSingleTripInfo(html);
        List<String> jsons = new ArrayList<>();
        for(Trip t : trips){
            t.setInfo(getGeneralInfo(html));
            t.setCompreso(getCompreso(html));
            t.setNonCompreso(getNonCompreso(html));
            t.setTitle(getTitle(html));
            t.setDestination(dest);
            t.setDescription(getDescription(html));
            t.setTags(getTags(html));
            t.setItinerary(getItinerary(html));
            String json = new Gson().toJson(t);
            jsons.add(json);
        }
        return jsons;
    }

    private String getGeneralInfo(String html){
        Document doc = Jsoup.parse(html);
        Element element = doc.getElementsByClass("col-12 mt-4 mb-5").first();
        try {
            Element div = element.getElementsByClass("card-home card-tappa rounded px-4 py-2").get(4);
            return div.text();
        }catch (Exception e ){}
        return "";
    }

    private List<String> getCompreso(String html){
        Document doc = Jsoup.parse(html);
        try{
            Element element = doc.getElementsByClass("col-12 mt-4 mb-5").first();
            Element div = element.getElementsByClass("card-home card-tappa rounded px-4 py-2").get(2);
            Elements lis = div.getElementsByTag("li");
            List <String> ret = new ArrayList<>();
            for(Element x : lis){
                ret.add(x.text());
            }

            return ret;
        }catch (Exception e){
        }
        return null;
    }

    private List<String> getNonCompreso(String html){
        Document doc = Jsoup.parse(html);
        try {
            Element element = doc.getElementsByClass("col-12 mt-4 mb-5").first();
            Element div = element.getElementsByClass("card-home card-tappa rounded px-4 py-2").get(3);
            Elements lis = div.getElementsByTag("li");
            List<String> ret = new ArrayList<>();
            for (Element x : lis) {
                ret.add(x.text());
            }
            return ret;
        }catch (Exception e){}

        return null;
    }

}
