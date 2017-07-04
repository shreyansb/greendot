package org.shreyans.greendot.events;

/**
 * Created by shreyans on 7/4/17.
 */

public class GoalCompletedEvent {
    private int[] heartPosition;

    public GoalCompletedEvent(int[] heartPosition) {
        this.heartPosition = heartPosition;
    }

    public int[] getHeartPosition() {
        return this.heartPosition;
    }
}
