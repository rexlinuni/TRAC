package com.tracv.settings;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracv.util.Constants;

import java.io.File;
import java.io.IOException;

public class SettingsIO {

    private ObjectMapper mapper;
    private Settings settings;

    public SettingsIO(){
        mapper = new ObjectMapper();

        setupSaveFile();
    }

    public Settings getSettings(){
        return settings;
    }

    public void readFile(){
        File file = new File(Constants.SETTINGS_FILE);
        try{
            settings = mapper.readValue(file, Settings.class);
        }catch(IOException e){
            settings = new Settings(); //Rewrite with default settings file...
            save();
            e.printStackTrace();
        }
    }

    private File saveFile;


    private void setupSaveFile(){
        System.out.println("Checking save file");
        saveFile = new File(Constants.SETTINGS_FILE);
        if(!saveFile.exists()){
            System.out.println("File not found");
            saveFile.getParentFile().mkdirs();
            try{
                saveFile.createNewFile();
                writeDefaultFile();
                System.out.println("Creating new Settings File since none exist");
            }catch(IOException e){
                e.printStackTrace();
            }
            saveFile.setWritable(true);
        }else{
            System.out.println("File found");
        }
    }

    public void writeDefaultFile(){
        settings = new Settings();
        save();
    }


    public void save(){ //Saves current settings.
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(saveFile, settings);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
