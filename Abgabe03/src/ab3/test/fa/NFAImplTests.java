package ab3.test.fa;

import ab3.impl.Eberl.fa.NFAImpl;
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
 * Tests for NFAImpl class.
 */
public class NFAImplTests {

    @Test(expected = IllegalStateException.class)
    public void whenBuildingNFAWithZeroStatesAnExceptionIsThrown() {

        new NFAImpl.Builder()
                .setNumStates(0)
                .setAcceptingStates(new HashSet<>(Collections.singletonList(1)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void whenBuildingNFAWithNegativeStatesAnExceptionIsThrown() {

        new NFAImpl.Builder()
                .setNumStates(-1)
                .setAcceptingStates(new HashSet<>(Collections.singletonList(1)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalAcceptingStateException.class)
    public void whenBuildingNFAWithAcceptingStatesOutOfRangeAnExceptionIsThrown() {

        new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9, 10)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalInitialStateException.class)
    public void whenBuildingNFAWithNegativeInitialStateAnExceptionIsThrown() {

        new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(-1)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalInitialStateException.class)
    public void whenBuildingNFAWithTooHighInitialStateAnExceptionIsThrown() {

        new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(10)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test
    public void buildingAnNFAWithValidArgumentsGivesBackAnApproriateClass() {

        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();

        Assert.assertThat(nfa, is(not(nullValue())));
    }

    @Test
    public void getStatesReturnsASetContainingAllStateIds() {

        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();

        Assert.assertThat(nfa.getStates().size(), is(10));
        Assert.assertThat(nfa.getStates(), hasItems(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test
    public void getNumStatesReturnsNumberOfStates() {

        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();

        Assert.assertThat(nfa.getNumStates(), is(10));
    }

    @Test
    public void getSymbolsReturnsAllSymbolsFromAlphabet() {

        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        Assert.assertThat(nfa.getSymbols().size(), is(4));
        Assert.assertThat(nfa.getSymbols(), hasItems('a', 'b', 'c', 'z'));
    }

    @Test
    public void getAcceptingStatesReturnsAllAcceptingStateIds() {

        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // given
        Assert.assertThat(nfa.getAcceptingStates().size(), is(3));
        Assert.assertThat(nfa.getAcceptingStates(), hasItems(1, 5, 9));
    }

    @Test
    public void getInitialStateReturnsInitialStateId() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        Assert.assertThat(nfa.getInitialState(), is(0));
    }

    @Test(expected = IllegalStateException.class)
    public void isAcceptingStateThrowsIllegalStateExceptionIfStateIsNotExisting() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        nfa.isAcceptingState(10);
    }

    @Test
    public void isAcceptingStateReturnsTrueForAcceptingStates() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        Assert.assertThat(nfa.isAcceptingState(1), is(true));
        Assert.assertThat(nfa.isAcceptingState(5), is(true));
        Assert.assertThat(nfa.isAcceptingState(9), is(true));
    }

    @Test
    public void isAcceptingStateReturnsFalseForNonAcceptingStates() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        Assert.assertThat(nfa.isAcceptingState(0), is(false));
        Assert.assertThat(nfa.isAcceptingState(6), is(false));
        Assert.assertThat(nfa.isAcceptingState(8), is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void setTransitionThrowsExceptionIfFromStateIsNotAValidState() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        nfa.setTransition(10, 'a', 1);
    }

    @Test(expected = IllegalStateException.class)
    public void setTransitionThrowsExceptionIfToStateIsNotAValidState() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        nfa.setTransition(1, 'a', 10);
    }

    @Test(expected = IllegalCharacterException.class)
    public void setTransitionThrowsExceptionIfSymbolIsNotInAlphabet() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        nfa.setTransition(1, 'y', 9);
    }

    @Test(expected = IllegalStateException.class)
    public void getNextStatesThrowsExceptionIfStateIsInvalid() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        nfa.getNextStates(10, 'a');
    }

    @Test(expected = IllegalCharacterException.class)
    public void getNextStatesThrowsExceptionIfSymbolIsInvalid() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        nfa.getNextStates(1, 'y');
    }

    @Test
    public void getNextStatesReturnsEmptySetIfNoTransitionsWereSpecified() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when
        Set<Integer> obtained = nfa.getNextStates(1, 'z');

        // then
        Assert.assertThat(obtained.size(), is(0));
    }

    @Test
    public void setTransitionAddsTransitions() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when adding first transition
        nfa.setTransition(1, 'a', 9);
        Set<Integer> obtained = nfa.getNextStates(1, 'a');

        // then
        Assert.assertThat(obtained.size(), is(1));
        Assert.assertThat(obtained, hasItem(9));

        // and when adding another transition for same symbol
        nfa.setTransition(1, 'a', 8);
        obtained = nfa.getNextStates(1, 'a');

        // then
        Assert.assertThat(obtained.size(), is(2));
        Assert.assertThat(obtained, hasItems(8, 9));

        // and when adding two more transitions
        nfa.setTransition(1, 'b', 7);
        nfa.setTransition(1, 'b', 6);
        obtained = nfa.getNextStates(1, 'b');

        // then
        Assert.assertThat(obtained.size(), is(2));
        Assert.assertThat(obtained, hasItems(6, 7));
    }

    @Test(expected = IllegalStateException.class)
    public void setEpsilonTransitionThrowsExceptionIfFromStateIsNotAValidState() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        nfa.setEpsilonTransition(10, 1);
    }

    @Test(expected = IllegalStateException.class)
    public void setEpsilonTransitionThrowsExceptionIfToStateIsNotAValidState() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        nfa.setEpsilonTransition(1, 10);
    }

    @Test(expected = IllegalStateException.class)
    public void getEpsilonTransitionThrowsExceptionIfStateIsInvalid() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        nfa.getNextStatesEpsilon(10);
    }

    @Test
    public void setEpsilonTransitionAddsTransitions() {

        // given
        NFA nfa = new NFAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when/then
        Set<Integer> obtained = nfa.getNextStatesEpsilon(1);
        Assert.assertThat(obtained.size(), is(0));
        Assert.assertThat(((NFAImpl)nfa).hasEpsilonTransitions(), is(false));

        // when adding first transition
        nfa.setEpsilonTransition(1, 9);
        obtained = nfa.getNextStatesEpsilon(1);

        // then
        Assert.assertThat(obtained.size(), is(1));
        Assert.assertThat(obtained, hasItem(9));
        Assert.assertThat(((NFAImpl)nfa).hasEpsilonTransitions(), is(true));

        // and when adding another transition for same symbol
        nfa.setEpsilonTransition(1, 8);
        obtained = nfa.getNextStatesEpsilon(1);

        // then
        Assert.assertThat(obtained.size(), is(2));
        Assert.assertThat(obtained, hasItems(8, 9));
        Assert.assertThat(((NFAImpl)nfa).hasEpsilonTransitions(), is(true));

        // and when adding two more transitions
        nfa.setEpsilonTransition(1, 7);
        nfa.setEpsilonTransition(1, 6);
        obtained = nfa.getNextStatesEpsilon(1);

        // then
        Assert.assertThat(obtained.size(), is(4));
        Assert.assertThat(obtained, hasItems(6, 7, 8, 9));
        Assert.assertThat(((NFAImpl)nfa).hasEpsilonTransitions(), is(true));
    }

    @Test
    public void convertingDFAToRSAWithTotalDFA() {

        // given (basically dfa)
        NFA dfa = new NFAImpl.Builder()
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
    public void convertingDFAToRSAWithSomeTransitionsSetAndNoEpsilonTransitions() {

        // given (based on example 4.4a from lecture slides)
        NFA dfa = new NFAImpl.Builder()
                .setNumStates(3)
                .setAcceptingStates(Collections.singleton(1))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('0', '1')))
                .build();

        // set some transitions
        dfa.setTransition(0, '1', 1);
        dfa.setTransition(0, '1', 2);
        dfa.setTransition(1, '1', 1);
        dfa.setTransition(1, '0', 0);
        dfa.setTransition(1, '0', 2);
        dfa.setTransition(2, '1', 0);

        // when
        RSA obtained = dfa.toRSA();

        // then
        Assert.assertThat(obtained, is(not(nullValue())));
        Assert.assertThat(obtained.getNumStates(), is(6));
        Assert.assertThat(obtained.getStates(), hasItems(0, 1, 2, 3, 4, 5));
        Assert.assertThat(obtained.getAcceptingStates().size(), is(3));
        Assert.assertThat(obtained.getAcceptingStates(), hasItems(2, 4, 5));
        Assert.assertThat(obtained.getInitialState(), is(0));
        Assert.assertThat(obtained.getSymbols().size(), is(2));
        Assert.assertThat(obtained.getSymbols(), hasItems('0', '1'));

        // verify the transitions
        Assert.assertThat(obtained.getNextState(0, '0'), is(1));
        Assert.assertThat(obtained.getNextState(0, '1'), is(2));

        Assert.assertThat(obtained.getNextState(1, '0'), is(1));
        Assert.assertThat(obtained.getNextState(1, '1'), is(1));

        Assert.assertThat(obtained.getNextState(2, '0'), is(3));
        Assert.assertThat(obtained.getNextState(2, '1'), is(4));

        Assert.assertThat(obtained.getNextState(3, '0'), is(1));
        Assert.assertThat(obtained.getNextState(3, '1'), is(5));

        Assert.assertThat(obtained.getNextState(4, '0'), is(3));
        Assert.assertThat(obtained.getNextState(4, '1'), is(2));

        Assert.assertThat(obtained.getNextState(5, '0'), is(3));
        Assert.assertThat(obtained.getNextState(5, '1'), is(5));
    }

    @Test
    public void convertingDFAToRSAWithEpsilonTransitions() {

        // given (based on example 4.4b from lecture slides)
        NFA dfa = new NFAImpl.Builder()
                .setNumStates(5)
                .setAcceptingStates(Collections.singleton(4))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();

        // set transitions
        dfa.setTransition(0, 'b', 2);
        dfa.setEpsilonTransition(0, 1);
        dfa.setTransition(1, 'a', 0);
        dfa.setTransition(1, 'a', 4);
        dfa.setEpsilonTransition(1, 2);
        dfa.setEpsilonTransition(1, 3);
        dfa.setTransition(2, 'b', 4);
        dfa.setTransition(3, 'a', 4);
        dfa.setEpsilonTransition(4, 3);

        // when
        RSA obtained = dfa.toRSA();

        // then
        Assert.assertThat(obtained, is(not(nullValue())));
        Assert.assertThat(obtained.getNumStates(), is(5));
        Assert.assertThat(obtained.getStates(), hasItems(0, 1, 2, 3, 4));
        Assert.assertThat(obtained.getAcceptingStates().size(), is(3));
        Assert.assertThat(obtained.getAcceptingStates(), hasItems(1, 2, 3));
        Assert.assertThat(obtained.getInitialState(), is(0));
        Assert.assertThat(obtained.getSymbols().size(), is(2));
        Assert.assertThat(obtained.getSymbols(), hasItems('a', 'b'));

        // verify the transitions
        Assert.assertThat(obtained.getNextState(0, 'a'), is(1));
        Assert.assertThat(obtained.getNextState(0, 'b'), is(2));

        Assert.assertThat(obtained.getNextState(1, 'a'), is(1));
        Assert.assertThat(obtained.getNextState(1, 'b'), is(2));

        Assert.assertThat(obtained.getNextState(2, 'a'), is(3));
        Assert.assertThat(obtained.getNextState(2, 'b'), is(3));

        Assert.assertThat(obtained.getNextState(3, 'a'), is(3));
        Assert.assertThat(obtained.getNextState(3, 'b'), is(4));

        Assert.assertThat(obtained.getNextState(4, 'a'), is(4));
        Assert.assertThat(obtained.getNextState(4, 'b'), is(4));
    }
}
