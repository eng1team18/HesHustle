package com.heshus18.game;

public class Time {
    private int day;
    private int hour;
    private int minute;

    public Time() {
        // 1 = Monday, 7 = Sunday
        // 8 = 08:00 (AM), the clock is using 24-hour style
        this.day = 1;
        this.hour = 8;
        this.minute = 0;
    }

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

    public void nextDay() {
        day++;
        hour = 8;
        minute = 0;

        if (day > 7) {
            day = 1;
        }
    }

    public String getTime() {
        String dayName = getDayName(day);
        return String.format("%s %02d:%02d", dayName, hour, minute);
    }

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

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}

