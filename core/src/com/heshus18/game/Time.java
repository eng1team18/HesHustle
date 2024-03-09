package com.heshus18.game;

/**
 * Represents a time within a week, using a 24-hour clock format.
 * The class allows you to change the time by adding minutes, moving to the next day, and retrieving the current time.
 */
public class Time {
    private int day;
    private int hour;
    private int minute;

    /**
     * Initializes a new Time instance to Monday at 08:00 AM.
     */
    public Time() {
        this.day = 1;  // 1 = Monday, 7 = Sunday
        this.hour = 8; // 08:00 AM in 24-hour clock format
        this.minute = 0;
    }

    /**
     * Adds a specified number of minutes to the current time.
     * The method will convert minutes to hours and days automatically
     * Days of the week cycle back to Monday after Sunday.
     *
     * @param minutesToAdd the number of minutes to add. Must be greater than 0.
     */
    public void addTime(int minutesToAdd) {
        minute += minutesToAdd;
        while (minute >= 60) {
            hour++;
            minute -= 60;
        }
        while (hour >= 24) {
            day++;
            hour -= 24;
        }
        if (day > 7) {
            day = 1;
        }
    }

    /**
     * Advances the current time to the next day, resetting the time to 08:00 AM of the next day.
     * Days of the week cycle back to Monday after Sunday.
     */
    public void nextDay() {
        day++;
        hour = 8;
        minute = 0;

        if (day > 7) {
            day = 1;
        }
    }

    /**
     * Returns a string of the current time, including the day name and time in HH:MM format.
     *
     * @return A string for the current day and time.
     */
    public String getTime() {
        String dayName = getDayName(day);
        return String.format("%s %02d:%02d", dayName, hour, minute);
    }

    /**
     * Get the name of the day based on the day number.
     *
     * @param day The day number (1 for Monday to 7 for Sunday).
     * @return The name of the day that matches the given number, or "Unknown" if the number is invalid.
     */
    public String getDayName(int day) {
        switch (day) {
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
            case 7: return "Sunday";
            default: return "Unknown";
        }
    }

    /**
     * Gets the current day of the week.
     *
     * @return The current day of the week, numbered from 1 (Monday) to 7 (Sunday).
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets the current hour of the day.
     *
     * @return The current hour, in 24-hour format.
     */
    public int getHour() {
        return hour;
    }

    /**
     * Gets the current minute of the hour.
     *
     * @return The current minute.
     */
    public int getMinute() {
        return minute;
    }
}
