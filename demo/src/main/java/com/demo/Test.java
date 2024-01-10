package com.demo;

public class Test {
    private static boolean bProfilePage;

    public static boolean GetIsInProfilePage() {return bProfilePage;}

    public static void SetIsInProfilePage(boolean ProfilePage) {
        bProfilePage = ProfilePage;
        if(bProfilePage)
        {
            System.out.println("User is in profile page");
        }
        else
        {
            System.out.println("User is outside of profile page");
        }
    }
}
