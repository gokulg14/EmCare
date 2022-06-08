package com.myproj.wear.helperclasses;

public class HealthDataHelper {

    private String namew;

    private String bpReading;

    private String heartRateReading;

    private String motionSensorReading;

    @Override
    public String toString() {
        return "HealthDataHelper{" +
                "namew='" + namew + '\'' +
                ", bpReading='" + bpReading + '\'' +
                ", heartRateReading='" + heartRateReading + '\'' +
                ", motionSensorReading='" + motionSensorReading + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    private String date;

    public String getNamew() {
        return namew;
    }

    public void setNamew(String namew) {
        this.namew = namew;
    }

    public String getBpReading() {
        return bpReading;
    }

    public void setBpReading(String bpReading) {
        this.bpReading = bpReading;
    }

    public String getHeartRateReading() {
        return heartRateReading;
    }

    public void setHeartRateReading(String heartRateReading) {
        this.heartRateReading = heartRateReading;
    }

    public String getMotionSensorReading() {
        return motionSensorReading;
    }

    public void setMotionSensorReading(String motionSensorReading) {
        this.motionSensorReading = motionSensorReading;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
