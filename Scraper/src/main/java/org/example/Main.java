package org.example;

import org.example.scraper.ScraperSiVola;
import org.jsoup.Jsoup;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        ScraperSiVola s = new ScraperSiVola();
        Set set = s.getLinks("https://www.sivola.it/viaggi");
        Iterator<String> iterator = set.iterator();
        List<String> travels = new ArrayList<>();
        Set<String> unique = new HashSet();
        while(iterator.hasNext()) {
            String link = iterator.next();
            String html = Jsoup.connect(link).get().html();
            List<String> temp = s.getTravelPerDestination(html,link.substring(29));

            //to save organizer uncomment and use another file
            // String organizer = s.getOrganizer(html);
            /*if(organizer!=null) {
                String name = organizer.substring(0, organizer.indexOf(" "));
                String surname = organizer.substring(organizer.indexOf(" ") + 1);
                Organizer o = new Organizer(name, surname);
                unique.add(new Gson().toJson(o));
                Iterator<String> it = unique.iterator();
                while(it.hasNext()){
                    travels.add(it.next());
                }
            }*/
            travels.addAll(temp);
        }
        FileWriter fileWriter = new FileWriter("dataset.json");
        fileWriter.write(travels.toString());
        fileWriter.close();

    }

}
