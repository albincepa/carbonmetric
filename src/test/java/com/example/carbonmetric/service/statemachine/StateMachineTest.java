package com.example.carbonmetric.service.statemachine;

import com.example.carbonmetric.model.State;
import com.example.carbonmetric.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StateMachineTest {

    @Autowired
    private StateMachine stateMachine;


    @Test
    void getNewState_OkWith1000() {
        State lastState = new State(Status.OK, 0);
        State expectState = new State(Status.OK, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 1000));
    }

    @Test
    void getNewState_OkWith2000() {
        State lastState = new State(Status.OK, 0);
        State expectState = new State(Status.OK, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 2000));
    }

    @Test
    void getNewState_OkWith3000() {
        State lastState = new State(Status.OK, 0);
        State expectState = new State(Status.WARN, 1);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 3000));
    }

    @Test
    void getNewState_WarnWith1000_count1() {
        State lastState = new State(Status.WARN, 1);
        State expectState = new State(Status.OK, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 1000));
    }

    @Test
    void getNewState_WarnWith2000_count1() {
        State lastState = new State(Status.WARN, 1);
        State expectState = new State(Status.OK, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 2000));
    }

    @Test
    void getNewState_WarnWith3000_count1() {
        State lastState = new State(Status.WARN, 1);
        State expectState = new State(Status.WARN, 2);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 3000));
    }

    @Test
    void getNewState_WarnWith1000_count2() {
        State lastState = new State(Status.WARN, 2);
        State expectState = new State(Status.OK, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 1000));
    }

    @Test
    void getNewState_WarnWith2000_count2() {
        State lastState = new State(Status.WARN, 2);
        State expectState = new State(Status.OK, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 2000));
    }

    @Test
    void getNewState_WarnWith3000_count2() {
        State lastState = new State(Status.WARN, 2);
        State expectState = new State(Status.ALERT, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 3000));
    }



    @Test
    void getNewState_AlertWith1000_count0() {
        State lastState = new State(Status.ALERT, 0);
        State expectState = new State(Status.ALERT, 1);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 1000));
    }

    @Test
    void getNewState_AlertWith2000_count0() {
        State lastState = new State(Status.ALERT, 0);
        State expectState = new State(Status.ALERT, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 2000));
    }

    @Test
    void getNewState_AlertWith3000_count0() {
        State lastState = new State(Status.ALERT, 0);
        State expectState = new State(Status.ALERT, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 3000));
    }

    @Test
    void getNewState_AlertWith1000_count1() {
        State lastState = new State(Status.ALERT, 1);
        State expectState = new State(Status.ALERT, 2);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 1000));
    }

    @Test
    void getNewState_AlertWith2000_count1() {
        State lastState = new State(Status.ALERT, 1);
        State expectState = new State(Status.ALERT, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 2000));
    }

    @Test
    void getNewState_AlertWith3000_count1() {
        State lastState = new State(Status.ALERT, 1);
        State expectState = new State(Status.ALERT, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 3000));
    }

    @Test
    void getNewState_AlertWith1000_count2() {
        State lastState = new State(Status.ALERT, 2);
        State expectState = new State(Status.OK, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 1000));
    }

    @Test
    void getNewState_AlertWith2000_count2() {
        State lastState = new State(Status.ALERT, 2);
        State expectState = new State(Status.ALERT, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 2000));
    }

    @Test
    void getNewState_AlertWith3000_count2() {
        State lastState = new State(Status.ALERT, 2);
        State expectState = new State(Status.ALERT, 0);
        Assertions.assertEquals(expectState, stateMachine.getNewState(lastState, 3000));
    }
}