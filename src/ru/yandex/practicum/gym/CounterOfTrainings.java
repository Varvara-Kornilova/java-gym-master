package ru.yandex.practicum.gym;

public class CounterOfTrainings {
    private final Coach coach;
    private int trainingsPerWeek;

    public CounterOfTrainings(Coach coach, int trainingsPerWeek) {
        this.coach = coach;
        this.trainingsPerWeek = trainingsPerWeek;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getTrainingsPerWeek() {
        return trainingsPerWeek;
    }

    @Override
    public String toString() {
        return coach + " — " + getTrainingsPerWeek() + " тренировок";
    }
}
