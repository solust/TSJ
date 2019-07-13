package com.example.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

public class parser {
    static class User {
        String login=null;
        String firstName=null;
        String lastName=null;
        String middleName=null;
        String email=null;
        String phone=null;
        String apartament=null;

        public User(String login, String firstName, String lastName, String middleName, String email, String phone, String apartament) {
            this.login = login;
            this.firstName = firstName;
            this.lastName = lastName;
            this.middleName = middleName;
            this.email = email;
            this.phone = phone;
            this.apartament = apartament;
        }
        public User(){
            this.login = null;
            this.firstName = null;
            this.lastName = null;
            this.middleName = null;
            this.email = null;
            this.phone = null;
            this.apartament = null;
        }
    }

    public User getUser(String response) {
        try {
            JSONObject userJson = new JSONObject(response);
            String login = userJson.getString("login");
            String firstName = userJson.getString("firstName");
            String lastName = userJson.getString("lastName");
            String middleName = userJson.getString("middleName");
            String email = userJson.getString("email");
            String phone = userJson.getString("phone");
            String apartament = userJson.getString("apartament");
            return new User(login,firstName,lastName,middleName,email,phone,apartament);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new User();
    }
}
