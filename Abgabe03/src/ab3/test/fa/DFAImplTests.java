package ab3.test.fa;

import ab3.impl.Eberl.fa.DFAImpl;
import at.syssec.fa.DFA;
import at.syssec.fa.NFA;
import at.syssec.fa.RSA;
import at.syssec.fa.exceptions.IllegalAcceptingStateException;
import at.syssec.fa.exceptions.IllegalCharacterException;
import at.syssec.fa.exceptions.IllegalInitialStateException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;

/**
 * Tests for DFAImpl class.
 */
public class DFAImplTests {

    @Test(expected = IllegalStateException.class)
    public void whenBuildingNFAWithZeroStatesAnExceptionIsThrown() {

        new DFAImpl.Builder()
                .setNumStates(0)
                .setAcceptingStates(new HashSet<>(Collections.singletonList(1)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void whenBuildingNFAWithNegativeStatesAnExceptionIsThrown() {

        new DFAImpl.Builder()
                .setNumStates(-1)
                .setAcceptingStates(new HashSet<>(Collections.singletonList(1)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalAcceptingStateException.class)
    public void whenBuildingNFAWithAcceptingStatesOutOfRangeAnExceptionIsThrown() {

        new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9, 10)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalInitialStateException.class)
    public void whenBuildingNFAWithNegativeInitialStateAnExceptionIsThrown() {

        new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(-1)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalInitialStateException.class)
    public void whenBuildingNFAWithTooHighInitialStateAnExceptionIsThrown() {

        new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(10)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test
    public void buildingAnNFAWithValidArgumentsGivesBackAnApproriateClass() {

        NFA nfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();

        Assert.assertThat(nfa, is(not(nullValue())));
    }

    @Test
    public void getStatesReturnsASetContainingAllStateIds() {

        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();

        Assert.assertThat(dfa.getStates().size(), is(10));
        Assert.assertThat(dfa.getStates(), hasItems(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test
    public void getNumStatesReturnsNumberOfStates() {

        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();

        Assert.assertThat(dfa.getNumStates(), is(10));
    }

    @Test
    public void getSymbolsReturnsAllSymbolsFromAlphabet() {

        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        Assert.assertThat(dfa.getSymbols().size(), is(4));
        Assert.assertThat(dfa.getSymbols(), hasItems('a', 'b', 'c', 'z'));
    }

    @Test
    public void getAcceptingStatesReturnsAllAcceptingStateIds() {

        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // given
        Assert.assertThat(dfa.getAcceptingStates().size(), is(3));
        Assert.assertThat(dfa.getAcceptingStates(), hasItems(1, 5, 9));
    }

    @Test
    public void getInitialStateReturnsInitialStateId() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        Assert.assertThat(dfa.getInitialState(), is(0));
    }

    @Test(expected = IllegalStateException.class)
    public void isAcceptingStateThrowsIllegalStateExceptionIfStateIsNotExisting() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        dfa.isAcceptingState(10);
    }

    @Test
    public void isAcceptingStateReturnsTrueForAcceptingStates() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        Assert.assertThat(dfa.isAcceptingState(1), is(true));
        Assert.assertThat(dfa.isAcceptingState(5), is(true));
        Assert.assertThat(dfa.isAcceptingState(9), is(true));
    }

    @Test
    public void isAcceptingStateReturnsFalseForNonAcceptingStates() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        Assert.assertThat(dfa.isAcceptingState(0), is(false));
        Assert.assertThat(dfa.isAcceptingState(6), is(false));
        Assert.assertThat(dfa.isAcceptingState(8), is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void setTransitionThrowsExceptionIfFromStateIsNotAValidState() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        dfa.setTransition(10, 'a', 1);
    }

    @Test(expected = IllegalStateException.class)
    public void setTransitionThrowsExceptionIfToStateIsNotAValidState() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        dfa.setTransition(1, 'a', 10);
    }

    @Test(expected = IllegalCharacterException.class)
    public void setTransitionThrowsExceptionIfSymbolIsNotInAlphabet() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        dfa.setTransition(1, 'y', 9);
    }

    @Test(expected = IllegalStateException.class)
    public void getNextStatesThrowsExceptionIfStateIsInvalid() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        dfa.getNextStates(10, 'a');
    }

    @Test(expected = IllegalCharacterException.class)
    public void getNextStatesThrowsExceptionIfSymbolIsInvalid() {

        // given
        DFA dfa= new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        dfa.getNextStates(1, 'y');
    }

    @Test
    public void getNextStatesReturnsEmptySetIfNoTransitionsWereSpecified() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when
        Set<Integer> obtained = dfa.getNextStates(1, 'z');

        // then
        Assert.assertThat(obtained.size(), is(0));
    }

    @Test
    public void setTransitionAddsTransitions() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when adding first transition
        dfa.setTransition(1, 'a', 9);
        Set<Integer> obtained = dfa.getNextStates(1, 'a');

        // then
        Assert.assertThat(obtained.size(), is(1));
        Assert.assertThat(obtained, hasItem(9));

        // and when adding another transition for same symbol
        dfa.setTransition(1, 'a', 8);
        obtained = dfa.getNextStates(1, 'a');

        // then
        Assert.assertThat(obtained.size(), is(1));
        Assert.assertThat(obtained, hasItem(8));

        // and when adding two more transitions
        dfa.setTransition(1, 'b', 7);
        dfa.setTransition(1, 'b', 6);
        obtained = dfa.getNextStates(1, 'b');

        // then
        Assert.assertThat(obtained.size(), is(1));
        Assert.assertThat(obtained, hasItem(6));
    }

    @Test
    public void setEpsilonTransitionDoesNotAddAny() {

        // given
        DFA dfa= new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when adding first transition
        dfa.setEpsilonTransition(1, 9);
        Set<Integer> obtained = dfa.getNextStatesEpsilon(1);

        // then
        Assert.assertThat(obtained.size(), is(0));

        // and when adding another transition for same symbol
        dfa.setEpsilonTransition(1, 8);
        obtained = dfa.getNextStatesEpsilon(1);

        // then
        Assert.assertThat(obtained.size(), is(0));
    }

    @Test(expected = IllegalStateException.class)
    public void getNextStateThrowsExceptionIfStateIsInvalid() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        dfa.getNextStates(10, 'a');
    }

    @Test(expected = IllegalCharacterException.class)
    public void getNextStateThrowsExceptionIfSymbolIsInvalid() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        dfa.getNextStates(1, 'y');
    }

    @Test
    public void getNextStateGetsNullIfNoTransitionExists() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when
        Integer obtained = dfa.getNextState(1, 'z');

        // then
        Assert.assertThat(obtained, is(nullValue()));

    }

    @Test
    public void aDefaultConstructedDFAGivesActualStateOfZero() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when
        int actState = dfa.getActState();

        // then
        Assert.assertThat(actState, is(0));
    }

    @Test
    public void callingResetResetsActualStateToInitialState() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(7)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when
        dfa.reset();

        // then
        Assert.assertThat(dfa.getActState(), is(7));
    }

    @Test(expected = IllegalStateException.class)
    public void doStepThrowsExceptionIfNoNextStateExists() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(7)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when
        dfa.doStep('a');
    }

    @Test
    public void doStepAdvancesToNextState() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(7)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        dfa.setTransition(7, 'a', 1);
        dfa.setTransition(7, 'b', 9);

        dfa.reset();

        // when
        dfa.doStep('a');

        // then
        Assert.assertThat(dfa.getActState(), is(1));
    }

    @Test
    public void isAcceptingStateCheckActState() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(7)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        dfa.setTransition(7, 'a', 1);
        dfa.setTransition(7, 'b', 9);

        dfa.reset();

        // then
        Assert.assertThat(dfa.isAcceptingState(), is(false));

        // when
        dfa.doStep('a');

        // then
        Assert.assertThat(dfa.isAcceptingState(), is(true));
    }

    @Test
    public void convertingDFAToRSAWithoutTransitionsGivesAppropriateRSA() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(4)
                .setAcceptingStates(new HashSet<>(Arrays.asList(2, 3)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c')))
                .build();

        // when
        RSA obtained = dfa.toRSA();

        // then
        Assert.assertThat(obtained, is(not(nullValue())));
        Assert.assertThat(obtained.getNumStates(), is(5));
        Assert.assertThat(obtained.getStates(), hasItems(0, 1, 2, 3, 4));
        Assert.assertThat(obtained.getAcceptingStates().size(), is(2));
        Assert.assertThat(obtained.getAcceptingStates(), hasItems(2, 3));
        Assert.assertThat(obtained.getInitialState(), is(0));
        Assert.assertThat(obtained.getSymbols().size(), is(3));
        Assert.assertThat(obtained.getSymbols(), hasItems('a', 'b', 'c'));

        // verify that all transitions are going to last state
        for (int state : obtained.getStates()) {
            for (char symbol : obtained.getSymbols()) {
                Assert.assertThat(obtained.getNextState(state, symbol), is(4));
            }
        }
    }

    @Test
    public void convertingDFAToRSAWithTotalDFA() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(4)
                .setAcceptingStates(new HashSet<>(Arrays.asList(2, 3)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c')))
                .build();

        // set all transitions
        for (int stateId = 0; stateId < dfa.getNumStates(); stateId++) {
            for (char symbol : dfa.getSymbols()) {
                dfa.setTransition(stateId, symbol, Math.min(stateId + 1, dfa.getNumStates() - 1));
            }
        }

        // when
        RSA obtained = dfa.toRSA();

        // then
        Assert.assertThat(obtained, is(not(nullValue())));
        Assert.assertThat(obtained.getNumStates(), is(4));
        Assert.assertThat(obtained.getStates(), hasItems(0, 1, 2, 3));
        Assert.assertThat(obtained.getAcceptingStates().size(), is(2));
        Assert.assertThat(obtained.getAcceptingStates(), hasItems(2, 3));
        Assert.assertThat(obtained.getInitialState(), is(0));
        Assert.assertThat(obtained.getSymbols().size(), is(3));
        Assert.assertThat(obtained.getSymbols(), hasItems('a', 'b', 'c'));

        // verify that all transitions are going to last state
        for (int state : obtained.getStates()) {
            for (char symbol : obtained.getSymbols()) {
                Assert.assertThat(obtained.getNextState(state, symbol), is(Math.min(state + 1, dfa.getNumStates() - 1)));
            }
        }
    }

    @Test
    public void convertingDFAToRSAWithSomeTransitionsSet() {

        // given
        DFA dfa = new DFAImpl.Builder()
                .setNumStates(4)
                .setAcceptingStates(new HashSet<>(Arrays.asList(2, 3)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c')))
                .build();

        // set some transitions
        dfa.setTransition(0, 'a', 1);
        dfa.setTransition(0, 'b', 1);
        dfa.setTransition(1, 'a', 2);
        dfa.setTransition(1, 'c', 2);
        dfa.setTransition(2, 'b', 3);
        dfa.setTransition(2, 'c', 3);

        // when
        RSA obtained = dfa.toRSA();

        // then
        Assert.assertThat(obtained, is(not(nullValue())));
        Assert.assertThat(obtained.getNumStates(), is(5));
        Assert.assertThat(obtained.getStates(), hasItems(0, 1, 2, 3, 4));
        Assert.assertThat(obtained.getAcceptingStates().size(), is(2));
        Assert.assertThat(obtained.getAcceptingStates(), hasItems(2, 3));
        Assert.assertThat(obtained.getInitialState(), is(0));
        Assert.assertThat(obtained.getSymbols().size(), is(3));
        Assert.assertThat(obtained.getSymbols(), hasItems('a', 'b', 'c'));

        // verify the transitions
        Assert.assertThat(obtained.getNextState(0, 'a'), is(1));
        Assert.assertThat(obtained.getNextState(0, 'b'), is(1));
        Assert.assertThat(obtained.getNextState(0, 'c'), is(4));
        Assert.assertThat(obtained.getNextState(1, 'a'), is(2));
        Assert.assertThat(obtained.getNextState(1, 'b'), is(4));
        Assert.assertThat(obtained.getNextState(1, 'c'), is(2));
        Assert.assertThat(obtained.getNextState(2, 'a'), is(4));
        Assert.assertThat(obtained.getNextState(2, 'b'), is(3));
        Assert.assertThat(obtained.getNextState(2, 'c'), is(3));
        Assert.assertThat(obtained.getNextState(3, 'a'), is(4));
        Assert.assertThat(obtained.getNextState(3, 'b'), is(4));
        Assert.assertThat(obtained.getNextState(3, 'c'), is(4));
    }
}
