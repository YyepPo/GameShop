package com.demo;

import java.util.ArrayList;

public class VideoGame extends  BaseGame{
    public class SystemRequirement
    {
        public String operationSystem;
        public String processor;
        public int memory;
        public String graphicsCard;
        public int storage;

        public final String GetOperatingSystem() {return operationSystem;}
        public final String GetProcessor() {return processor;}
        public final int GetMemory() {return memory;}
        public final String GetGraphicsCard() {return graphicsCard;}
        public final int GetStorage() {return storage;}
    }

    private int ageRestriction;
    SystemRequirement systemRequirement = new SystemRequirement();

    public VideoGame(int gameID,String name, String gameImg, String gameDesc, double gamePrice, String gameType, ArrayList<String> screenShots,
                     int ageRestriction, String operationSystem,  int memory, String graphicsCard, int storage,String gameReleaseDate
                     ) {
        super(gameID,name, gameImg, gameDesc, gamePrice, gameType, screenShots, gameReleaseDate);
        this.ageRestriction = ageRestriction;

        systemRequirement.operationSystem = operationSystem;
        systemRequirement.memory = memory;
        systemRequirement.graphicsCard = graphicsCard;
        systemRequirement.storage = storage;
    }

    ///
    ///Getters
    ///
    public final int GetAgeRestriction() {return ageRestriction;}
    public final SystemRequirement GetSystemRequirement() {return systemRequirement;}

    public static void main(String[] args)
    {

    }
}
