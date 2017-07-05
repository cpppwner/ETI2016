package ab3.test.fa;

import ab3.impl.Eberl.fa.FABuilder;
import ab3.impl.Eberl.fa.algorithms.FAAlgorithms;
import at.syssec.fa.RSA;
import at.syssec.fa.exceptions.IllegalAcceptingStateException;
import at.syssec.fa.exceptions.IllegalInitialStateException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * Test class for testing {@link FABuilder}
 */
public class FABuilderTests {

    @Test(expected = IllegalStateException.class)
    public void whenBuildingFAWithZeroStatesAnExceptionIsThrown() {

        new MockBuilder()
                .setNumStates(0)
                .setAcceptingStates(new HashSet<>(Collections.singletonList(1)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void whenBuildingFAWithNegativeStatesAnExceptionIsThrown() {

        new MockBuilder()
                .setNumStates(-1)
                .setAcceptingStates(new HashSet<>(Collections.singletonList(1)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalAcceptingStateException.class)
    public void whenBuildingFAWithAcceptingStatesOutOfRangeAnExceptionIsThrown() {

        new MockBuilder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9, 10)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalInitialStateException.class)
    public void whenBuildingFAWithNegativeInitialStateAnExceptionIsThrown() {

        new MockBuilder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(-1)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalInitialStateException.class)
    public void whenBuildingFAWithTooHighInitialStateAnExceptionIsThrown() {

        new MockBuilder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(10)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test
    public void buildingAnFAWithValidArgumentsGivesBackAnApproriateClass() {

        MockFiniteAutomaton fa = new MockBuilder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();

        Assert.assertThat(fa, is(not(nullValue())));
        Assert.assertThat(fa.builder, is(not(nullValue())));
    }

    private static class MockFiniteAutomaton {

        MockBuilder builder;

        MockFiniteAutomaton(MockBuilder builder) {
            this.builder = builder;
        }
    }

    private static class MockBuilder extends FABuilder<MockFiniteAutomaton> {


        @Override
        protected MockFiniteAutomaton doBuild() {
            return new MockFiniteAutomaton(this);
        }
    }
}
