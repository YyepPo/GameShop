package com.demo;

public class Test {
    private static boolean bProfilePage;

    public static boolean GetIsInProfilePage() {return bProfilePage;}

    public static void SetIsInProfilePage(boolean ProfilePage) {
        bProfilePage = ProfilePage;
        if(bProfilePage)
        {
            System.out.println("true");
        }
        else
        {
            System.out.println("false");
        }
    }
}
