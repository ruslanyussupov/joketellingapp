package com.example.ruslaniusupov.jokes;


import org.junit.Assert;

public class DataProviderTest {

    @org.junit.Test
    public void getJokesJson() {
        String data = DataProvider.getJokesJson();
        Assert.assertNotNull(data);
        System.out.println(data);
    }
}