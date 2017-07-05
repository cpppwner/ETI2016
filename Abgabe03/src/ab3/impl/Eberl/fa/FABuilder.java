package ab3.impl.Eberl.fa;

import ab3.impl.Eberl.fa.algorithms.FAAlgorithms;
import at.syssec.fa.exceptions.IllegalAcceptingStateException;
import at.syssec.fa.exceptions.IllegalInitialStateException;

import java.util.Set;

/**
 * Base Builder class for all Finite Automatons.
 */
public abstract class FABuilder<TAutomatonClass> {


    /**
     * Denotes the maximum number of states.
     *
     * <p>
     *     States always start with state 0 and go up to (excluding) numStates.
     * </p>
     */
    int numStates = -1;

    /**
     * Characters in the alphabet.
     */
    Set<Character> characters = null;

    /**
     * Accepting (final) states of the NFA.
     */
    Set<Integer> acceptingStates = null;

    /**
     * Initial state of the NFA/
     */
    Integer initialState = null;

    /**
     * Set number of states.
     * @param numStates Number of states.
     * @return {@code this}.
     */
    public FABuilder<TAutomatonClass> setNumStates(int numStates) {
        this.numStates = numStates;
        return this;
    }

    /**
     * Set characters in our alphabet.
     * @param characters The characters contained in the alphabet.
     * @return {@code this}.
     */
    public FABuilder<TAutomatonClass> setCharacters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    /**
     * Set accepting (final) states.
     * @param acceptingStates Accepting (final) states.
     * @return {@code this}.
     */
    public FABuilder<TAutomatonClass> setAcceptingStates(Set<Integer> acceptingStates) {
        this.acceptingStates = acceptingStates;
        return this;
    }

    /**
     * Set initial state.
     * @param initialState The initial state of the NFA.
     * @return {@code this}.
     */
    public FABuilder<TAutomatonClass> setInitialState(Integer initialState) {
        this.initialState = initialState;
        return this;
    }


    /**
     * Validate inputs and build NFA.
     * @return
     */
    public TAutomatonClass build() {
        if(numStates <= 0) {
            throw new IllegalStateException();
        }
        if (acceptingStates == null || acceptingStates.stream().anyMatch(state -> state < 0 || state >= numStates)) {
            throw new IllegalAcceptingStateException();
        }
        if (initialState == null || initialState < 0 || initialState >= numStates) {
            throw new IllegalInitialStateException();
        }

        return doBuild();
    }

    protected abstract TAutomatonClass doBuild();
}
