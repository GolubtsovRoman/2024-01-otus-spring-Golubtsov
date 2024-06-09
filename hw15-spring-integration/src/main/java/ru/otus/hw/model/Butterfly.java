package ru.otus.hw.model;

public record Butterfly(int wingspanMm, boolean isDead) {

    public boolean notDead() {
        return !isDead;
    }

}
