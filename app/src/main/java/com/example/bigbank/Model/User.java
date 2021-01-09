package com.example.bigbank.Model;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String email;
    private String password;
    private String fullName;
    private String identityCard;
    private String phoneNumber;
    private String gender;
    private String nation;
    private String occupation;
    private String address;
    private String postCode;
    private String state;
    private String city;
    private String identityCardPictureFront;
    private String identityCardPictureBack;
    private String facePicture;
    private String cvc;
    private String cardNumber;
    private int usingFirstTime;        // 1 mean true and 0 means false
    private int receivedCard;          // 1 mean true and 0 means false


    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public void setIdentityCardPictureBack(String identityCardPictureBack) {
        this.identityCardPictureBack = identityCardPictureBack;
    }

    public void setIdentityCardPictureFront(String identityCardPictureFront) {
        this.identityCardPictureFront = identityCardPictureFront;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setReceivedCard(int receivedCard) {
        this.receivedCard = receivedCard;
    }

    public void setUsingFirstTime(int usingFirstTime) {
        this.usingFirstTime = usingFirstTime;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCvc() {
        return cvc;
    }

    public int getReceivedCard() {
        return receivedCard;
    }

    public String getGender() {
        return gender;
    }

    public int getUsingFirstTime() {
        return usingFirstTime;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public String getIdentityCardPictureBack() {
        return identityCardPictureBack;
    }

    public String getIdentityCardPictureFront() {
        return identityCardPictureFront;
    }

    public String getNation() {
        return nation;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getState() {
        return state;
    }

    public String getFacePicture() {
        return facePicture;
    }

    public void setFacePicture(String facePicture) {
        this.facePicture = facePicture;
    }
}
