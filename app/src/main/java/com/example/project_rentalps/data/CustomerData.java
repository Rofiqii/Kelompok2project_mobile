package com.example.project_rentalps.data;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerData {
    private static String status;
    private static String message;
    private static String username;
    private static String fullName;
    private static String password;
    private static String securityQuestion;
    private static String securityAnswer;

    public static void parseResponse(JSONObject response) {
        try {
            status = response.getString("status");
            message = response.getString("pesan");

            JSONObject customer = response.getJSONObject("customer");
            username = customer.getString("username_cus");
            fullName = customer.getString("fullname_cus");
            password = customer.getString("password_cus");
            securityQuestion = customer.getString("pertanyaan");
            securityAnswer = customer.getString("jawaban");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getStatus() {
        return status;
    }

    public static String getMessage() {
        return message;
    }

    public static String getUsername() {
        return username;
    }

    public static String getFullName() {
        return fullName;
    }

    public static String getPassword() {
        return password;
    }

    public static String getSecurityQuestion() {
        return securityQuestion;
    }

    public static String getSecurityAnswer() {
        return securityAnswer;
    }
}
