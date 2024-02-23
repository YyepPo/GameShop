package com.demo.Friend;

public class Friend {
    private int id;
    private String name;
    private String imagePath;

    public Friend(int id,String name,String imagePath)
    {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
    }

    ///
    /// Getters
    ///
    public final String GetFriendName() {return name;}
    public final String GetFriendImagePath() {return imagePath;}
    public final int GetFriendID() {return id;}
}
