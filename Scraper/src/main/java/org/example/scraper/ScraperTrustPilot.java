package org.example.scraper;

import com.google.gson.Gson;
import org.example.model.Organizer;
import org.example.model.Review;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScraperTrustPilot {
    private List<Review> getReview(String html, List<Organizer> users) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag("article");
        List<Review> reviews = new ArrayList<>();
        for(Element x : elements){
            // get reviewer
            String fullname = x.getElementsByTag("a").first().getElementsByTag("span").first().text();
            Organizer u;
            if(fullname.indexOf(" ") != -1) {
                String name = fullname.substring(0, fullname.indexOf(" "));
                String surname = fullname.substring(fullname.indexOf(" ")+1);
                u = new Organizer(name,surname);
            }else{
                u= new Organizer(fullname);
            }
            users.add(u);

            //get review
            Element div = x.getElementsByTag("section").first().getElementsByTag("div").first();
            int rating = Integer.parseInt(div.attr("data-service-review-rating"));
            String dateS =div.getElementsByTag("time").first().attr("datetime");
            Date date = formatter.parse(dateS);
            Element textReviewElem = x.getElementsByTag("section").first().getElementsByClass("styles_reviewContent__0Q2Tg").first();
            Element titleElem = textReviewElem.getElementsByTag("h2").first();
            String title=null;
            String text = null;
            try {
                title = titleElem.text();
                Element textElem = textReviewElem.getElementsByTag("p").first();
                text = textElem.text();
            }catch (Exception e){}

            Review r = new Review(text,title,date,rating);
            reviews.add(r);
        }
        return reviews;
    }

    // to test!!

    public static void main(String[] args) throws IOException, ParseException {
        List<String> reviewString = new ArrayList<>();
        List<String> usersString = new ArrayList<>();
        for(int i = 1; i<=57; i++) {
            String html = Jsoup.connect("https://it.trustpilot.com/review/www.weroad.it?page=" + i).get().html();
            ScraperTrustPilot s = new ScraperTrustPilot();
            List<Organizer> users = new ArrayList<>();
            List<Review> rev = s.getReview(html, users);
            for (Organizer org : users) {
                String json = new Gson().toJson(org);
                usersString.add(json);
            }
            for (Review r : rev) {
                String json = new Gson().toJson(r);
                reviewString.add(json);
            }


            System.out.println(reviewString);
            System.out.println(usersString);
        }
        FileWriter fileWriter = new FileWriter("reviews.json");
        fileWriter.write(reviewString.toString());
        fileWriter.close();

        fileWriter = new FileWriter("users.json");
        fileWriter.write(usersString.toString());
        fileWriter.close();

    }
}
