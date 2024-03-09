package com.heshus18.game;

/**
 * Counter to track the number of time you perform various activities within the game.
 * This includes the number of time you eat, sleep, study, and doing activities.
 */
public class Score {
    private int timeAte;
    private int timeSlept;
    private int timeStudy;
    private int timeActivity;

    private static Score instance = new Score();

    private Score() {}

    public static Score getInstance() {
        return instance;
    }

    /**
     * Increments the counter for the number of time you eat by one.
     */
    public void incrementTimeAte() { this.timeAte++; }

    /**
     * Increments the counter for the number of time you sleep by one.
     */
    public void incrementTimeSlept() { this.timeSlept++; }

    /**
     * Increments the counter for the number of time you study by one.
     */
    public void incrementTimeStudy() { this.timeStudy++; }

    /**
     * Increments the counter for the number of time you perform an activity by one.
     */
    public void incrementTimeActivity() { this.timeActivity++; }

    /**
     * Gets the total number of time you eat.
     *
     * @return The total number of time you eat.
     */
    public int getTimeAte() { return timeAte; }

    /**
     * Gets the total number of time you sleep.
     *
     * @return The total number of time you sleep.
     */
    public int getTimeSlept() { return timeSlept; }

    /**
     * Gets the total number of time you study.
     *
     * @return The total number of time you study.
     */
    public int getTimeStudy() { return timeStudy; }

    /**
     * Gets the total number of time you do other activities.
     *
     * @return The total number of time you perform other activities.
     */
    public int getTimeActivity() { return timeActivity; }

    /**
     * Resets all the counters (eat, sleep, study, activity) to zero.
     */
    public void resetCounters() {
        timeAte = 0;
        timeSlept = 0;
        timeStudy = 0;
        timeActivity = 0;
    }
}