package com.wordpress.frankhaolunliblog.laprunner;

/**
 * Created by Frank2 on 4/29/2017.
 */

public class SaveWorkout {
    String id;
    String date;
    String time_start;
    Integer laps_per_km;
    Integer total_laps;

    // Time
    Integer total_time;
    String each_lap_time;
    String fluc_lap_time;
    Integer avg_lap_time;

    // Speed
    String each_lap_speed;
    String fluc_lap_speed;
    String avg_speed;

    // Calories
    String each_lap_calories;
    String fluc_lap_calories;
    Integer total_calories;
    Integer avg_calories;

    // Steps
    String each_lap_steps;
    String fluc_lap_steps;
    Integer total_steps;
    Integer avg_steps;
    
    public SaveWorkout(){}

    public SaveWorkout(String id, String date, String time_start, Integer laps_per_km, 
                       Integer total_laps, Integer total_time, String each_lap_time,
                       String fluc_lap_time, Integer avg_lap_time, String each_lap_speed,
                       String fluc_lap_speed, String avg_speed, String each_lap_calories, 
                       String fluc_lap_calories, Integer total_calories, Integer avg_calories,
                       String each_lap_steps, String fluc_lap_steps, Integer total_steps,
                       Integer avg_steps){
            this.id = id;
            this.date = date;
            this.time_start = time_start;
            this.laps_per_km = laps_per_km;
            this.total_laps = total_laps;

            // Time
            this.total_time = total_time;
            this.each_lap_time = each_lap_time;
            this.fluc_lap_time = fluc_lap_time;
            this.avg_lap_time = avg_lap_time;

            // Speed
            this.each_lap_speed = each_lap_speed;
            this.fluc_lap_speed = fluc_lap_speed;
            this.avg_speed = avg_speed;

            // Calories
            this.each_lap_calories = each_lap_calories;
            this.fluc_lap_calories = fluc_lap_calories;
            this.total_calories = total_calories;
            this.avg_calories = avg_calories;

            // Steps
            this.each_lap_steps = each_lap_steps;
            this.fluc_lap_steps = fluc_lap_steps;
            this.total_steps = total_steps;
            this.avg_steps = avg_steps;
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

    public Integer getAvg_lap_time() {
        return avg_lap_time;
    }

    public void setAvg_lap_time(Integer avg_lap_time) {
        this.avg_lap_time = avg_lap_time;
    }

    public String getEach_lap_speed() {
        return each_lap_speed;
    }

    public void setEach_lap_speed(String each_lap_speed) {
        this.each_lap_speed = each_lap_speed;
    }

    public String getFluc_lap_speed() {
        return fluc_lap_speed;
    }

    public void setFluc_lap_speed(String fluc_lap_speed) {
        this.fluc_lap_speed = fluc_lap_speed;
    }

    public String getAvg_speed() {
        return avg_speed;
    }

    public void setAvg_speed(String avg_speed) {
        this.avg_speed = avg_speed;
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

    public Integer getTotal_calories() {
        return total_calories;
    }

    public void setTotal_calories(Integer total_calories) {
        this.total_calories = total_calories;
    }

    public Integer getAvg_calories() {
        return avg_calories;
    }

    public void setAvg_calories(Integer avg_calories) {
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

    public Integer getTotal_steps() {
        return total_steps;
    }

    public void setTotal_steps(Integer total_steps) {
        this.total_steps = total_steps;
    }

    public Integer getAvg_steps() {
        return avg_steps;
    }

    public void setAvg_steps(Integer avg_steps) {
        this.avg_steps = avg_steps;
    }
}
