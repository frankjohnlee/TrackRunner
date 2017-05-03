package com.wordpress.frankhaolunliblog.laprunner;

/**
 * Created by Frank2 on 4/29/2017.
 */

public class Workout {
    String id;
    String date;
    String time_start;
    Integer laps_per_km;
    Integer total_laps;

    // Time
    Integer total_time;
    String each_lap_time;
    String fluc_lap_time;
    Double avg_lap_time;

    // TimePerKM
    String each_lap_TimePerKM;
    String fluc_lap_TimePerKM;
    Double avg_TimePerKM;

    // MetersPerSecond
    String each_lap_MetersPerSecond;
    String fluc_lap_MetersPerSecond;
    Double avg_MetersPerSecond;

    // Calories
    String each_lap_calories;
    String fluc_lap_calories;
    Double total_calories;
    Double avg_calories;

    // Steps
    String each_lap_steps;
    String fluc_lap_steps;
    Double total_steps;
    Double avg_steps;

    public Workout(){}

    public Workout(String id){
            this.id = id;
            this.date = "";
            this.time_start = "";
            this.laps_per_km = -1;
            this.total_laps = -1;

            // Time
            this.total_time = -1;
            this.each_lap_time = "";
            this.fluc_lap_time = "";
            this.avg_lap_time = -1.0;

            // TimePerKM
            this.each_lap_TimePerKM = "";
            this.fluc_lap_TimePerKM = "";
            this.avg_TimePerKM = -1.0;

            // MetersPerSecond
            this.each_lap_MetersPerSecond = "";
            this.fluc_lap_MetersPerSecond = "";
            this.avg_MetersPerSecond = -1.0;

            // Calories
            this.each_lap_calories = "";
            this.fluc_lap_calories = "";
            this.total_calories = -1.0;
            this.avg_calories = -1.0;

            // Steps
            this.each_lap_steps = "";
            this.fluc_lap_steps = "";
            this.total_steps = -1.0;
            this.avg_steps = -1.0;
             }
    public String getEach_lap_MetersPerSecond() {
        return each_lap_MetersPerSecond;
    }
    public void setEach_lap_MetersPerSecond(String each_lap_MetersPerSecond) {
        this.each_lap_MetersPerSecond = each_lap_MetersPerSecond;
    }
    public String getFluc_lap_MetersPerSecond() {
        return fluc_lap_MetersPerSecond;
    }
    public void setFluc_lap_MetersPerSecond(String fluc_lap_MetersPerSecond) {
        this.fluc_lap_MetersPerSecond = fluc_lap_MetersPerSecond;
    }
    public Double getAvg_MetersPerSecond() {
        return avg_MetersPerSecond;
    }
    public void setAvg_MetersPerSecond(Double avg_MetersPerSecond) {
        this.avg_MetersPerSecond = avg_MetersPerSecond;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime_start() {
        return time_start;
    }
    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }
    public Integer getLaps_per_km() {
        return laps_per_km;
    }
    public void setLaps_per_km(Integer laps_per_km) {
        this.laps_per_km = laps_per_km;
    }
    public Integer getTotal_laps() {
        return total_laps;
    }
    public void setTotal_laps(Integer total_laps) {
        this.total_laps = total_laps;
    }
    public Integer getTotal_time() {
        return total_time;
    }
    public void setTotal_time(Integer total_time) {
        this.total_time = total_time;
    }
    public String getEach_lap_time() {
        return each_lap_time;
    }
    public void setEach_lap_time(String each_lap_time) {
        this.each_lap_time = each_lap_time;
    }
    public String getFluc_lap_time() {
        return fluc_lap_time;
    }
    public void setFluc_lap_time(String fluc_lap_time) {
        this.fluc_lap_time = fluc_lap_time;
    }
    public Double getAvg_lap_time() {
        return avg_lap_time;
    }
    public void setAvg_lap_time(Double avg_lap_time) {
        this.avg_lap_time = avg_lap_time;
    }
    public String getEach_lap_TimePerKM() {
        return each_lap_TimePerKM;
    }
    public void setEach_lap_TimePerKM(String each_lap_TimePerKM) {
        this.each_lap_TimePerKM = each_lap_TimePerKM;
    }
    public String getFluc_lap_TimePerKM() {
        return fluc_lap_TimePerKM;
    }
    public void setFluc_lap_TimePerKM(String fluc_lap_TimePerKM) {
        this.fluc_lap_TimePerKM = fluc_lap_TimePerKM;
    }
    public Double getAvg_TimePerKM() {
        return avg_TimePerKM;
    }
    public void setAvg_TimePerKM(Double avg_TimePerKM) {
        this.avg_TimePerKM = avg_TimePerKM;
    }
    public String getEach_lap_calories() {
        return each_lap_calories;
    }
    public void setEach_lap_calories(String each_lap_calories) {
        this.each_lap_calories = each_lap_calories;
    }
    public String getFluc_lap_calories() {
        return fluc_lap_calories;
    }
    public void setFluc_lap_calories(String fluc_lap_calories) {
        this.fluc_lap_calories = fluc_lap_calories;
    }
    public Double getTotal_calories() {
        return total_calories;
    }
    public void setTotal_calories(Double total_calories) {
        this.total_calories = total_calories;
    }
    public Double getAvg_calories() {
        return avg_calories;
    }
    public void setAvg_calories(Double avg_calories) {
        this.avg_calories = avg_calories;
    }
    public String getEach_lap_steps() {
        return each_lap_steps;
    }
    public void setEach_lap_steps(String each_lap_steps) {
        this.each_lap_steps = each_lap_steps;
    }
    public String getFluc_lap_steps() {
        return fluc_lap_steps;
    }
    public void setFluc_lap_steps(String fluc_lap_steps) {
        this.fluc_lap_steps = fluc_lap_steps;
    }
    public Double getTotal_steps() {
        return total_steps;
    }
    public void setTotal_steps(Double total_steps) {
        this.total_steps = total_steps;
    }
    public Double getAvg_steps() {
        return avg_steps;
    }
    public void setAvg_steps(Double avg_steps) {
        this.avg_steps = avg_steps;
    }
}
