package com.example.carbonmetric.model;

import com.google.gson.Gson;

public class State {
    Status status;
    int count;

    public State(Status status, int count) {
        this.status = status;
        this.count = count;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (count != state.count) return false;
        return status == state.status;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + count;
        return result;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void setCount(int count) {
        this.count = count;
    }
}
