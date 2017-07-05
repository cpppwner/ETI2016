package ab3.test.fa;

import ab3.impl.Eberl.fa.RSAImpl;
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
 * Tests for RSAImpl class.
 */
public class RSAImplTests {

    @Test(expected = IllegalStateException.class)
    public void whenBuildingRSAWithZeroStatesAnExceptionIsThrown() {

        new RSAImpl.Builder()
                .setNumStates(0)
                .setAcceptingStates(new HashSet<>(Collections.singletonList(1)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void whenBuildingRSAWithNegativeStatesAnExceptionIsThrown() {

        new RSAImpl.Builder()
                .setNumStates(-1)
                .setAcceptingStates(new HashSet<>(Collections.singletonList(1)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalAcceptingStateException.class)
    public void whenBuildingRSAWithAcceptingStatesOutOfRangeAnExceptionIsThrown() {

        new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9, 10)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalInitialStateException.class)
    public void whenBuildingRSAWithNegativeInitialStateAnExceptionIsThrown() {

        new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(-1)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test(expected = IllegalInitialStateException.class)
    public void whenBuildingRSAWithTooHighInitialStateAnExceptionIsThrown() {

        new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(10)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();
    }

    @Test
    public void buildingAnRSAWithValidArgumentsGivesBackAnAppropriateClass() {

        NFA nfa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();

        Assert.assertThat(nfa, is(not(nullValue())));
    }

    @Test
    public void getStatesReturnsASetContainingAllStateIds() {

        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();

        Assert.assertThat(rsa.getStates().size(), is(10));
        Assert.assertThat(rsa.getStates(), hasItems(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test
    public void getNumStatesReturnsNumberOfStates() {

        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();

        Assert.assertThat(rsa.getNumStates(), is(10));
    }

    @Test
    public void getSymbolsReturnsAllSymbolsFromAlphabet() {

        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        Assert.assertThat(rsa.getSymbols().size(), is(4));
        Assert.assertThat(rsa.getSymbols(), hasItems('a', 'b', 'c', 'z'));
    }

    @Test
    public void aDefaultConstructedRSAHasAllTransitionsSetToLastState() {

        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 8)))
                .setInitialState(7)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b')))
                .build();

        for (int stateIndex = 0; stateIndex < 10; stateIndex++) {

            for (char symbol : Arrays.asList('a', 'b')) {

                Assert.assertThat(rsa.getNextState(stateIndex, symbol), is(9));
            }
        }

    }

    @Test
    public void getAcceptingStatesReturnsAllAcceptingStateIds() {

        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // given
        Assert.assertThat(rsa.getAcceptingStates().size(), is(3));
        Assert.assertThat(rsa.getAcceptingStates(), hasItems(1, 5, 9));
    }

    @Test
    public void getInitialStateReturnsInitialStateId() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        Assert.assertThat(rsa.getInitialState(), is(0));
    }

    @Test(expected = IllegalStateException.class)
    public void isAcceptingStateThrowsIllegalStateExceptionIfStateIsNotExisting() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        rsa.isAcceptingState(10);
    }

    @Test
    public void isAcceptingStateReturnsTrueForAcceptingStates() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        Assert.assertThat(rsa.isAcceptingState(1), is(true));
        Assert.assertThat(rsa.isAcceptingState(5), is(true));
        Assert.assertThat(rsa.isAcceptingState(9), is(true));
    }

    @Test
    public void isAcceptingStateReturnsFalseForNonAcceptingStates() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        Assert.assertThat(rsa.isAcceptingState(0), is(false));
        Assert.assertThat(rsa.isAcceptingState(6), is(false));
        Assert.assertThat(rsa.isAcceptingState(8), is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void setTransitionThrowsExceptionIfFromStateIsNotAValidState() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        rsa.setTransition(10, 'a', 1);
    }

    @Test(expected = IllegalStateException.class)
    public void setTransitionThrowsExceptionIfToStateIsNotAValidState() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        rsa.setTransition(1, 'a', 10);
    }

    @Test(expected = IllegalCharacterException.class)
    public void setTransitionThrowsExceptionIfSymbolIsNotInAlphabet() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        rsa.setTransition(1, 'y', 9);
    }

    @Test(expected = IllegalStateException.class)
    public void getNextStatesThrowsExceptionIfStateIsInvalid() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        rsa.getNextStates(10, 'a');
    }

    @Test(expected = IllegalCharacterException.class)
    public void getNextStatesThrowsExceptionIfSymbolIsInvalid() {

        // given
        RSA rsa= new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        rsa.getNextStates(1, 'y');
    }

    @Test
    public void setTransitionAddsTransitions() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when adding first transition
        rsa.setTransition(1, 'a', 9);
        Set<Integer> obtained = rsa.getNextStates(1, 'a');

        // then
        Assert.assertThat(obtained.size(), is(1));
        Assert.assertThat(obtained, hasItem(9));

        // and when adding another transition for same symbol
        rsa.setTransition(1, 'a', 8);
        obtained = rsa.getNextStates(1, 'a');

        // then
        Assert.assertThat(obtained.size(), is(1));
        Assert.assertThat(obtained, hasItem(8));

        // and when adding two more transitions
        rsa.setTransition(1, 'b', 7);
        rsa.setTransition(1, 'b', 6);
        obtained = rsa.getNextStates(1, 'b');

        // then
        Assert.assertThat(obtained.size(), is(1));
        Assert.assertThat(obtained, hasItem(6));
    }

    @Test
    public void setEpsilonTransitionDoesNotAddAny() {

        // given
        RSA rsa= new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when adding first transition
        rsa.setEpsilonTransition(1, 9);
        Set<Integer> obtained = rsa.getNextStatesEpsilon(1);

        // then
        Assert.assertThat(obtained.size(), is(0));

        // and when adding another transition for same symbol
        rsa.setEpsilonTransition(1, 8);
        obtained = rsa.getNextStatesEpsilon(1);

        // then
        Assert.assertThat(obtained.size(), is(0));
    }

    @Test(expected = IllegalStateException.class)
    public void getNextStateThrowsExceptionIfStateIsInvalid() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        rsa.getNextStates(10, 'a');
    }

    @Test(expected = IllegalCharacterException.class)
    public void getNextStateThrowsExceptionIfSymbolIsInvalid() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // then
        rsa.getNextStates(1, 'y');
    }

    @Test
    public void aDefaultConstructedDFAGivesActualStateOfZero() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(0)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when
        int actState = rsa.getActState();

        // then
        Assert.assertThat(actState, is(0));
    }

    @Test
    public void callingResetResetsActualStateToInitialState() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(7)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when
        rsa.reset();

        // then
        Assert.assertThat(rsa.getActState(), is(7));
    }

    @Test
    public void doStepAdvancesToNextState() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(7)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        rsa.setTransition(7, 'a', 1);
        rsa.setTransition(7, 'b', 9);

        rsa.reset();

        // when
        rsa.doStep('a');

        // then
        Assert.assertThat(rsa.getActState(), is(1));
    }

    @Test
    public void isAcceptingStateCheckActState() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(7)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        rsa.setTransition(7, 'a', 1);
        rsa.setTransition(7, 'b', 9);

        rsa.reset();

        // then
        Assert.assertThat(rsa.isAcceptingState(), is(false));

        // when
        rsa.doStep('a');

        // then
        Assert.assertThat(rsa.isAcceptingState(), is(true));
    }

    @Test
    public void convertingAnRSAToRSAGivesSameInstance() {

        // given
        RSA rsa = new RSAImpl.Builder()
                .setNumStates(10) // will give states in range [0, 9]
                .setAcceptingStates(new HashSet<>(Arrays.asList(1, 5, 9)))
                .setInitialState(7)
                .setCharacters(new HashSet<>(Arrays.asList('a', 'b', 'c', 'z')))
                .build();

        // when
        RSA obtained = rsa.toRSA();

        // then
        Assert.assertThat(obtained, is(sameInstance(rsa)));
    }
}
