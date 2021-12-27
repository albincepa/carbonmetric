package com.example.carbonmetric.service.statemachine;

import com.example.carbonmetric.model.State;
import com.example.carbonmetric.model.Status;
import org.springframework.stereotype.Component;

@Component
public class StateMachine {
    public State getNewState(State currentState, long co2) {
        return switch (currentState.getStatus()) {
            case OK -> handleOK(currentState, co2);
            case WARN -> handleWarning(currentState, co2);
            case ALERT -> handleAlert(currentState, co2);
        };
    }

    private State handleAlert(State currentState, long co2) {
        if (co2 >= 2000) {
            return new State(Status.ALERT, 0);
        }

        int currentStateCount = currentState.getCount();
        if (currentStateCount < 2) {
            currentStateCount++;
            currentState.setCount(currentStateCount);
            return currentState;
        }

        return new State(Status.OK, 0);
    }

    private State handleWarning(State currentState, long co2) {
        if (co2 <= 2000) {
            return new State(Status.OK, 0);
        }

        if (currentState.getCount() == 1) {
            currentState.setCount(2);
            return currentState;
        } else {
            return new State(Status.ALERT, 0);
        }
    }

    private State handleOK(State currentState, long co2) {
        if (co2 <= 2000) {
            return currentState;
        }
        return new State(Status.WARN, 1);


    }
}
