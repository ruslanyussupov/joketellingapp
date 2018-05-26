package com.example.ruslaniusupov.jokes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataProvider {

    private static final String DATA_PATH = "jokes.json";

    public static String getJokesJson() {

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(DATA_PATH);
        BufferedReader bufferedReader = null;
        String result;

        try {

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }

            result = stringBuilder.toString();

        } catch (FileNotFoundException e) {
            result = e.getMessage();
            System.out.println("File is not found: " + DATA_PATH);
        } catch (IOException e) {
            result  = e.getMessage();
            System.out.println("Can't read the line: " + e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("Can't close the InputStream: " + e.getMessage());
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("Can't close the BufferedReader: " + e.getMessage());
                }
            }
        }

        return result;

    }

}
