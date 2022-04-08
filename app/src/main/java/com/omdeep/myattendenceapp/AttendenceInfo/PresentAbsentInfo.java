package com.omdeep.myattendenceapp.AttendenceInfo;

public class PresentAbsentInfo {
    private String present;
    private String absent;
    private String presentKey;

    public String getPresentKey() {
        return presentKey;
    }

    public void setPresentKey(String presentKey) {
        this.presentKey = presentKey;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }
}
