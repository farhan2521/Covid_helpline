package com.example.covidhelpline.ui;

public class WorkingPhoneNo {
    String phoneNo;
    boolean isWorking;
    String category;

    public WorkingPhoneNo(String phoneNo, boolean isWorking, String category) {
        this.phoneNo = phoneNo;
        this.isWorking = isWorking;
        this.category = category;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return phoneNo;
    }
}
