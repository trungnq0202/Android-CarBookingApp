package com.trungngo.carshareapp.model;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;
import java.util.List;

public class User {

    @DocumentId
    private String docId;
    private String username;
    private String phone;
    private Date birthDate;
    private String gender;
    private String email;
    private List<String> ownSitesId;
    private List<String> participatingSitesId;
    private String role;

    public User() {
    }

    public User(String docId, String username, String phone, Date birthDate, String gender, String email, List<String> ownSitesId, List<String> participatingSitesId, String role) {
        this.docId = docId;
        this.username = username;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
        this.ownSitesId = ownSitesId;
        this.participatingSitesId = participatingSitesId;
        this.role = role;
    }

    public boolean isCustomer(){
        return this.role.equals("Customer");
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getOwnSitesId() {
        return ownSitesId;
    }

    public void setOwnSitesId(List<String> ownSitesId) {
        this.ownSitesId = ownSitesId;
    }

    public List<String> getParticipatingSitesId() {
        return participatingSitesId;
    }

    public void setParticipatingSitesId(List<String> participatingSitesId) {
        this.participatingSitesId = participatingSitesId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
