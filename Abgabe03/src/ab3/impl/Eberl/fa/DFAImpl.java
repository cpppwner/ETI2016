package ab3.impl.Eberl.fa;

import ab3.impl.Eberl.fa.algorithms.FAAlgorithms;
import ab3.impl.Eberl.fa.details.DFAState;
import at.syssec.fa.DFA;
import at.syssec.fa.NFA;
import at.syssec.fa.RSA;
import at.syssec.fa.exceptions.IllegalCharacterException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of a DFA (Deterministic Finite Automaton)
 */
public class DFAImpl implements DFA {

    private final Set<Character> alphabet;
    private final Map<Integer, DFAState> states;
    private final DFAState initialState;

    private DFAState currentState;

    /**
     * Initialize a new NFA.
     * @param builder The builder containing all data necessary.
     */
    DFAImpl(FABuilder<?> builder) {

        alphabet = builder.characters;
        states = new HashMap<>();

        IntStream.range(0, builder.numStates).forEach(id -> states.put(id, new DFAState(id, builder.acceptingStates.contains(id))));
        initialState = states.get(builder.initialState);

        // TODO bug in original impl?
        // currentState has stateCode 0 and reset needs to be called first to properly initialize it
        currentState = states.get(0);
    }

    @Override
    public Set<Integer> getStates() {

        return Collections.unmodifiableSet(states.keySet());
    }

    @Override
    public int getNumStates() {

        return states.size();
    }

    @Override
    public Set<Character> getSymbols() {

        return alphabet;
    }

    @Override
    public Set<Integer> getAcceptingStates() {

        return states.values()
                .stream()
                .filter(DFAState::isAcceptingState)
                .map(DFAState::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public int getInitialState() {

        return initialState.getId();
    }

    @Override
    public boolean isAcceptingState(int state) {

        if (!states.containsKey(state)) {
            throw new IllegalStateException();
        }

        return states.get(state).isAcceptingState();
    }

    @Override
    public void setTransition(int fromState, char symbol, int toState) {

        if (!states.containsKey(fromState) || !states.containsKey(toState)) {
            throw new IllegalStateException();
        }
        if (!alphabet.contains(symbol)) {
            throw new IllegalCharacterException();
        }

        states.get(fromState).addTransition(symbol, states.get(toState));
    }

    @Override
    public void setEpsilonTransition(int fromState, int toState) {

        // nothing to do, since a DFA does not have epsilon transitions.
    }

    @Override
    public Set<Integer> getNextStates(int fromState, char symbol) {

        Integer nextState = getNextState(fromState, symbol);

        return nextState == null
                ? Collections.emptySet()
                : Collections.singleton(nextState);
    }

    @Override
    public Set<Integer> getNextStatesEpsilon(int fromState) {

        // same checks as for NFA - to be consistent to what is in the JAR
        if (!states.containsKey(fromState)) {
            throw new IllegalStateException();
        }

        // but always return an empty set
        return Collections.emptySet();
    }

    @Override
    public Integer getNextState(int fromState, char symbol) {

        if (!states.containsKey(fromState)) {
            throw new IllegalStateException();
        }
        if (!alphabet.contains(symbol)) {
            throw new IllegalCharacterException();
        }

        DFAState nextState = states.get(fromState).getNextState(symbol);

        return nextState == null ? null : nextState.getId();
    }

    @Override
    public int doStep(char symbol) {

        Integer nextState = getNextState(currentState.getId(), symbol);
        if (nextState == null) {
            throw new IllegalStateException();
        }

        currentState = states.get(nextState);

        return currentState.getId();
    }

    @Override
    public void reset() {

        currentState = initialState;
    }

    @Override
    public int getActState() {

        return currentState.getId();
    }

    @Override
    public boolean isAcceptingState() {

        return currentState.isAcceptingState();
    }

    @Override
    public RSA toRSA() {

        return FAAlgorithms.toRSA(this);
    }

    @Override
    public NFA union(NFA nfa) {
        return null;
    }

    @Override
    public NFA intersection(NFA nfa) {
        return null;
    }

    @Override
    public NFA complement() {
        return null;
    }

    @Override
    public NFA concat(NFA nfa) {
        return null;
    }

    @Override
    public NFA kleeneStar() {
        return null;
    }

    @Override
    public Boolean accepts(String s) {
        return null;
    }

    @Override
    public Boolean acceptsNothing() {
        return null;
    }

    @Override
    public Boolean acceptsEpsilonOnly() {
        return null;
    }

    @Override
    public Boolean acceptsEpsilon() {
        return null;
    }

    @Override
    public Boolean isInfinite() {
        return null;
    }

    @Override
    public Boolean isFinite() {
        return null;
    }

    @Override
    public Boolean subSetOf(NFA nfa) {
        return null;
    }

    @Override
    public Boolean equals(NFA nfa) {
        return null;
    }

    /**
     * Fluent builder class for creating DFA.
     */
    public static class Builder extends FABuilder<DFAImpl> {

        @Override
        protected DFAImpl doBuild() {

            return new DFAImpl(this);
        }
    }
}
