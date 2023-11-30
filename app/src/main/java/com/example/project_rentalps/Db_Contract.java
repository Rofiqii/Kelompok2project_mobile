package com.example.project_rentalps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Db_Contract {

    private static final String API_URL = "https://rentalpowergames.my.id/API/";
    private static final String LOGIN_ENDPOINT = "login.php";

    // Database connection parameters
//    private static final String DB_URL = "jdbc:mysql://https://rentalpowergames.my.id/API/login.php";
//    private static final String USER = "username_cus";
//    private static final String PASS = "password_cus";

    // Establish database connection
//    public static Connection connect() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            return DriverManager.getConnection(DB_URL, USER, PASS);
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            System.err.println("Failed to connect to the database. Error: " + e.getMessage());
//            return null;
//        }
//    }


    // Get data from API
    public static String fetchDataFromAPI(String endpoint) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(API_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    // Fetch data from login.php
    public static String fetchLoginData() {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(API_URL + LOGIN_ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void main(String[] args) {
//        Connection connection = connect();
//        if (connection != null) {
//            System.out.println("Database connected!");
//
//            // Example: Fetch data from API (adjust the endpoint)
//            String apiData = fetchDataFromAPI("histori.php");
//            System.out.println("Data from API: " + apiData);
//
//            // Example: Fetch data from login.php
//            String loginData = fetchLoginData();
//            System.out.println("Login data from API: " + loginData);
//        } else {
//            System.out.println("Failed to connect to the database.");
//        }
    }
}
