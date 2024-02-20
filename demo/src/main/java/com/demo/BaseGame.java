package com.demo;

import java.util.ArrayList;

public class BaseGame {

    protected String name;
    protected String gameImg;
    protected String gameDesc;
    protected double gamePrice;
    protected String gameType; //This is an enum that represents the game type
    protected String gameReleaseDate;
    protected ArrayList<String> screenShots = new ArrayList<String>();

    private int gameID = 0;


    //Constructor
    BaseGame(int gameID,String name,String gameImg,String gameDesc,double gamePrice,String gameType,ArrayList<String> screenShots,
             String gameReleaseDate)
    {
        this.gameID = gameID;
        this.name = name;
        this.gameImg = gameImg;
        this.gameDesc = gameDesc;
        this.gamePrice = gamePrice;
        this.gameType = gameType;
        this.screenShots = screenShots;
        this.gameReleaseDate = gameReleaseDate;

        gameID++;
    }

    ///
    /// Setters
    ///
    public void SetImgSrc(String src)
    {
        gameImg = src;
        System.out.println(gameImg);
    }

    ///
    /// Getters
    ///
    public final int GetGameID() {return gameID;}
    public final String GetGameName() {return name;}
    public final String GetGameImg() {return gameImg;}
    public final String GetGameDesc() {return gameDesc;}
    public final double GetGamePrice() {return gamePrice;}
    public final String GetGameType() {return gameType;}
    public final ArrayList<String> GetScreenShots() {return screenShots;}
    public final String GetGameReleaseDate() {return gameReleaseDate;}

    public void SetGameDiscout(double percentage)
    {
        //Discount value cannot be higher or lower than 0
        //So clamp the value btw 0 and 1
        percentage = Clamp(percentage,0.f,1.f);
        double startingPrice = gamePrice;
        //Percentage formula: value = value * percentage
        //And decrement the neccesary variable with value
        startingPrice *= percentage;
        gamePrice -= startingPrice;
        gamePrice = (float)Math.round(gamePrice);

        System.out.println(gamePrice);
    }

    public static double Clamp(double value, double min, double max)
    {
        return Math.max(min, Math.min(max, value));
    }

    public void AddScreenShot(String screenShotSrc)
    {
        //Check if the screenShot already exists
        //If it does, don't add
        if(!screenShotSrc.isEmpty())
        {
            for (String screenShot : screenShots) {
                if (screenShot.equals(screenShotSrc)) {
                    System.out.println("The screen shot " + screenShotSrc + " already exists");
                    return;
                }
            }
        }
        screenShots.add(screenShotSrc);

    }
}
