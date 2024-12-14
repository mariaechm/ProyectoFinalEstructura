package com.example.controller.dao.implement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonFileManager {

    public static String jsonContainerDir = "./media/";
    public final static String currentID = "CurrentID"; 
    
    public static void saveFile(Object data, String className) {
        final String json = new GsonBuilder().setPrettyPrinting().create().toJson(data);

        final String fileName = jsonContainerDir + className + ".json";

        try (FileWriter tilinWriter = new FileWriter(fileName)) {
            tilinWriter.write(json);
            tilinWriter.flush();
        } catch (Exception e) {
            System.out.println("JsonFileManager.saveFile() dice: " + e.getMessage());
        }
    }

    public static String readFile(String className) {
        final String fileName = jsonContainerDir + className + ".json";
        try (BufferedReader bfr = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = bfr.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    // CURRENT ID MANAGMENT ===============================================================

    public static void saveCurrentIdOf(Integer currentId,String className) {
        HashMap<String,Integer> cIdMap = readCurrentIDMap();
        if(cIdMap == null) cIdMap = new HashMap<>();
        final String key = "current" + className + "Id";
        cIdMap.put(key, currentId);
        saveFile(cIdMap,currentID);
    }

    public static HashMap<String,Integer> readCurrentIDMap() {
        Type mapType = new TypeToken<HashMap<String,Integer>>(){}.getType();
        return new Gson().fromJson(readFile(currentID), mapType);
    }

    public static Integer readAndUpdateCurrentIdOf(String className) {
        HashMap<String, Integer> currentIDs = readCurrentIDMap();
        final String key = "current" + className + "Id";

        if(currentIDs == null) {
            saveCurrentIdOf(0,className);
            currentIDs = readCurrentIDMap();
        } 

        if(currentIDs.get(key) == null) {
            saveCurrentIdOf(0, className);
            currentIDs = readCurrentIDMap();
        } 

        Integer currentId = currentIDs.get(key);
        saveCurrentIdOf(++currentId, className);
        return currentId;
    }
}
