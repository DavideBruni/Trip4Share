package it.unipi.dii.lsmd.backend.config;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Loader {
    private static Config instance = null;

    public static Config getInstance() {
        if(instance == null){
            instance = loadConfig();
        }
        return instance;
    }

    private static Config loadConfig(){
        Config conf = null;
        try {
            conf = new Gson().fromJson(new FileReader("config.json"), Config.class);
        } catch (FileNotFoundException e) {
            System.err.println("Errore nel caricare la configurazione iniziale");
        }
        return conf;
    }
}
