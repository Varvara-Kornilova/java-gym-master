package ru.yandex.practicum.gym;

import java.util.List;
import java.util.TreeMap;

public class TrainingSessionValidator {

    public static boolean canAddTrainingSession(
            TrainingSession newSession,
            TreeMap<TimeOfDay, List<TrainingSession>> daySchedule) {

        if (daySchedule == null || daySchedule.isEmpty()) {
            return true;
        }

        TimeOfDay newStart = newSession.getTimeOfDay();
        int newDuration = newSession.getGroup().getDuration();
        Group newGroup = newSession.getGroup();
        Coach newCoach = newSession.getCoach();

        for (List<TrainingSession> sessionAtTime : daySchedule.values()) {
            for (TrainingSession existingSession : sessionAtTime) {
                TimeOfDay existingTime = existingSession.getTimeOfDay();
                int existingDuration = existingSession.getGroup().getDuration();

                if (internalOverlap(newStart, newDuration, existingTime, existingDuration)) {
                    if (existingSession.getGroup().equals(newGroup)) {
                        return false;
                    }

                    if (existingSession.getCoach().equals(newCoach)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static boolean internalOverlap(
            TimeOfDay start1, int duration1,
            TimeOfDay start2, int duration2) {

        int start1InMin = start1.toTotalMinutes();
        int start2InMin = start2.toTotalMinutes();
        int end1 = start1InMin + duration1;
        int end2 = start2InMin + duration2;

        return start1InMin < end2 && start2InMin < end1;
    }
}
