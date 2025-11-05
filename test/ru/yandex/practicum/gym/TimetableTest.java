package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, monday.size());

        TimeOfDay expectedTime = new TimeOfDay(13, 00);
        assertTrue(monday.containsKey(expectedTime));

        List<TrainingSession> sessionsAtTime = monday.get(expectedTime);
        assertNotNull(sessionsAtTime);
        assertEquals(1, sessionsAtTime.size());
        assertTrue(sessionsAtTime.contains(singleTrainingSession));

        TreeMap<TimeOfDay, List<TrainingSession>> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, tuesday.size());
    }

    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, monday.size());

        TimeOfDay expectedTime = new TimeOfDay(13, 00);
        assertTrue(monday.containsKey(new TimeOfDay(13, 00)));

        List<TrainingSession> sessionsOnMonday = monday.get(expectedTime);
        assertNotNull(sessionsOnMonday);
        assertEquals(1, sessionsOnMonday.size());
        assertTrue(sessionsOnMonday.contains(mondayChildTrainingSession));

        TreeMap<TimeOfDay, List<TrainingSession>> thursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursday.size());
        List<TrainingSession> childSessionOnThursday = thursday.get(new TimeOfDay(13, 00));
        assertNotNull(childSessionOnThursday);
        assertEquals(1, childSessionOnThursday.size());
        assertTrue(childSessionOnThursday.contains(thursdayChildTrainingSession));
        List<TrainingSession> adultSessionOnThursday = thursday.get(new TimeOfDay(20, 00));
        assertNotNull(adultSessionOnThursday);
        assertEquals(1, adultSessionOnThursday.size());
        assertTrue(adultSessionOnThursday.contains(thursdayAdultTrainingSession));


        TreeMap<TimeOfDay, List<TrainingSession>> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, tuesday.size());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> monday13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 00));
        assertEquals(1, monday13.size());
        assertTrue(monday13.contains(singleTrainingSession));

        List<TrainingSession> monday14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 00));
        assertEquals(0, monday14.size());
    }

    @Test
    public void testMultipleSessionsDifferentGroupsWithSameCoachAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Ковалев", "Иван", "Михайлович");
        Group group1 = new Group("Группа 1", Age.ADULT, 60);
        Group group2 = new Group("Группа 2", Age.CHILD, 60);

        TrainingSession trainingSession1 = new TrainingSession(group1, coach, DayOfWeek.FRIDAY,
                new TimeOfDay(14, 00));
        TrainingSession trainingSession2 = new TrainingSession(group2, coach, DayOfWeek.FRIDAY,
                new TimeOfDay(14, 00));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.FRIDAY,
                new TimeOfDay(14, 00));
        assertEquals(1, sessions.size());
        assertTrue(sessions.contains(trainingSession1));
        assertFalse(sessions.contains(trainingSession2));
    }

    @Test
    public void testMultipleSessionsDifferentGroupsWithDifferentCoachesAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Ковалев", "Иван", "Михайлович");
        Coach coach2 = new Coach("Лопатов", "Кирилл", "Олегович");
        Group group1 = new Group("Группа 1", Age.ADULT, 60);
        Group group2 = new Group("Группа 2", Age.CHILD, 60);

        TrainingSession trainingSession1 = new TrainingSession(group1, coach1, DayOfWeek.SATURDAY,
                new TimeOfDay(14, 00));
        TrainingSession trainingSession2 = new TrainingSession(group2, coach2, DayOfWeek.SATURDAY,
                new TimeOfDay(14, 00));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.SATURDAY,
                new TimeOfDay(14, 00));
        assertEquals(2, sessions.size());
        assertTrue(sessions.contains(trainingSession1));
        assertTrue(sessions.contains(trainingSession2));
    }

    @Test
    public void testMultipleSessionsDuplicateGroupsWithDifferentCoachesAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Ковалев", "Иван", "Михайлович");
        Coach coach2 = new Coach("Лопатов", "Кирилл", "Олегович");
        Group group1 = new Group("Группа 1", Age.ADULT, 60);
        Group group2 = new Group("Группа 1", Age.ADULT, 60);

        TrainingSession trainingSession1 = new TrainingSession(group1, coach1, DayOfWeek.SUNDAY,
                new TimeOfDay(14, 00));
        TrainingSession trainingSession2 = new TrainingSession(group2, coach2, DayOfWeek.SUNDAY,
                new TimeOfDay(14, 00));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.SUNDAY,
                new TimeOfDay(14, 00));
        assertEquals(1, sessions.size());
        assertTrue(sessions.contains(trainingSession1));
    }

    @Test
    public void testAddDuplicateSessionDoesNotAffectGetMethods() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Ковалев", "Иван", "Михайлович");
        Group group = new Group("Группа 1", Age.ADULT, 60);
        TrainingSession session = new TrainingSession(group, coach, DayOfWeek.THURSDAY,
                new TimeOfDay(10, 00));

        timetable.addNewTrainingSession(session);
        timetable.addNewTrainingSession(session);

        TreeMap<TimeOfDay, List<TrainingSession>> daySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        List<TrainingSession> dayAndTimeSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY,
                new TimeOfDay(10, 00));

        TimeOfDay expectedTime = new TimeOfDay(10, 00);
        List<TrainingSession> sessionsAtTime = daySessions.get(expectedTime);

        assertEquals(1, daySessions.size());
        assertTrue(sessionsAtTime.contains(session));
        assertEquals(1, dayAndTimeSessions.size());
        assertTrue(dayAndTimeSessions.contains(session));
    }

    @Test
    public void testGetCountByCoachesSingleCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Сергей", "Алексеевич");
        Group group1 = new Group("Группа 1", Age.ADULT, 60);
        Group group2 = new Group("Группа 2", Age.ADULT, 45);

        timetable.addNewTrainingSession(new TrainingSession(group1, coach, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group2, coach, DayOfWeek.WEDNESDAY,
                new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group1, coach, DayOfWeek.FRIDAY,
                new TimeOfDay(19, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(1, result.size());
        assertEquals(coach, result.get(0).getCoach());
        assertEquals(3, result.get(0).getTrainingsPerWeek());
    }

    @Test
    public void testGetCountByCoachesMultipleCoachesSorted() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Степнов", "Андрей", "Викторович");
        Coach coach2 = new Coach("Калинина", "Мария", "Ивановна");
        Coach coach3 = new Coach("Ломов", "Дмитрий", "Сергеевич");

        Group group = new Group("Фитнес", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY,
                new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.TUESDAY,
                new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY,
                new TimeOfDay(9, 0)));


        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.THURSDAY,
                new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.FRIDAY,
                new TimeOfDay(18, 0)));


        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.SATURDAY,
                new TimeOfDay(11, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(3, result.size());

        assertEquals(3, result.get(0).getTrainingsPerWeek());
        assertEquals(2, result.get(1).getTrainingsPerWeek());
        assertEquals(1, result.get(2).getTrainingsPerWeek());

        assertEquals(coach1, result.get(0).getCoach());
        assertEquals(coach2, result.get(1).getCoach());
        assertEquals(coach3, result.get(2).getCoach());
    }

    @Test
    public void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testValidatorConflictingCoach() {
        Group group1 = new Group("Группа1", Age.ADULT, 60);
        Group group2 = new Group("Группа2", Age.ADULT, 60);
        Coach coach = new Coach("Иванов", "Александр", "Степанович");

        TrainingSession session1 = new TrainingSession(group1, coach, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach, DayOfWeek.MONDAY,
                new TimeOfDay(10, 30));

        TreeMap<TimeOfDay, List<TrainingSession>> schedule = new TreeMap<>();
        schedule.put(session1.getTimeOfDay(), new ArrayList<>(List.of(session1)));

        assertFalse(TrainingSessionValidator.canAddTrainingSession(session2, schedule));
    }
}
