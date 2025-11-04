package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, HashSet<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (trainingSession == null) {
            return;
        }

        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        Coach coach = trainingSession.getCoach();

        TreeMap<TimeOfDay, HashSet<TrainingSession>> daySchedule = timetable.get(day);

        if (daySchedule == null) {
            daySchedule = new TreeMap<>();
            timetable.put(day, daySchedule);
        }

        HashSet<TrainingSession> sessionsAtTime = daySchedule.get(time);

        if (sessionsAtTime == null) {
            sessionsAtTime = new HashSet<>();
            daySchedule.put(time, sessionsAtTime);
        }

        if (sessionsAtTime.contains(trainingSession)) {
            return;
        }

        for (TrainingSession existingSession : sessionsAtTime) {
            if (existingSession.getGroup().equals(trainingSession.getGroup())) {
                return;
            }
        }

        for (TrainingSession existingSession : sessionsAtTime) {
            if (existingSession.getCoach().equals(coach)) {
                return;
            }
        }

        sessionsAtTime.add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, HashSet<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (daySchedule == null || daySchedule.isEmpty()) {
            return List.of();
        }

        List<TrainingSession> result = new ArrayList<>();

        for (HashSet<TrainingSession> sessions : daySchedule.values()) {
            result.addAll(sessions);
        }

        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, HashSet<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (daySchedule == null) {
            return List.of();
        }

        HashSet<TrainingSession> sessions = daySchedule.get(timeOfDay);

        if (sessions == null) {
            return List.of();
        }

        return new ArrayList<>(sessions);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachCountMap = new HashMap<>();

        for (TreeMap<TimeOfDay, HashSet<TrainingSession>> daySchedule : timetable.values()) {
            for (HashSet<TrainingSession> sessionsAtTime : daySchedule.values()) {
                for (TrainingSession session : sessionsAtTime) {
                    Coach coach = session.getCoach();
                    coachCountMap.put(coach, coachCountMap.getOrDefault(coach, 0) + 1);
                }

            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : coachCountMap.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        result.sort((a, b) -> Integer.compare(b.getTrainingsPerWeek(),
                a.getTrainingsPerWeek()));

        return result;
    }
}
