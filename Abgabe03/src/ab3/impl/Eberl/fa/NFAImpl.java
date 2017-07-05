package ab3.impl.Eberl.fa;

import ab3.impl.Eberl.fa.algorithms.FAAlgorithms;
import ab3.impl.Eberl.fa.details.NFAState;
import at.syssec.fa.NFA;
import at.syssec.fa.RSA;
import at.syssec.fa.exceptions.IllegalCharacterException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of Non-deterministic Finite Automaton.
 */
public class NFAImpl implements NFA {

    private final Set<Character> alphabet;
    private final Map<Integer, NFAState> states;
    private final NFAState initialState;

    private boolean hasEpsilonTransitions;

    /**
     * Initialize a new NFA.
     * @param builder The builder containing all data necessary.
     */
    private NFAImpl(FABuilder<?> builder) {

        alphabet = builder.characters;
        states = new HashMap<>();

        IntStream.range(0, builder.numStates).forEach(id -> states.put(id, new NFAState(id, builder.acceptingStates.contains(id))));
        initialState = states.get(builder.initialState);

        hasEpsilonTransitions = false;
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
                .filter(NFAState::isAcceptingState)
                .map(NFAState::getId)
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

        if (!states.containsKey(fromState) || !states.containsKey(toState)) {
            throw new IllegalStateException();
        }

        states.get(fromState).addEpsilonTransition(states.get(toState));
        hasEpsilonTransitions = true;
    }

    public boolean hasEpsilonTransitions() {

        return hasEpsilonTransitions;
    }

    @Override
    public Set<Integer> getNextStates(int fromState, char symbol) {

        if (!states.containsKey(fromState)) {
            throw new IllegalStateException();
        }
        if (!alphabet.contains(symbol)) {
            throw new IllegalCharacterException();
        }

        return states.get(fromState).getNextStates(symbol).stream().map(NFAState::getId).collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getNextStatesEpsilon(int fromState) {

        if (!states.containsKey(fromState)) {
            throw new IllegalStateException();
        }

        return states.get(fromState).getEpsilonTransitions().stream().map(NFAState::getId).collect(Collectors.toSet());
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
     * Fluent builder class for creating NFA.
     */
    public static class Builder extends FABuilder<NFAImpl> {

        @Override
        protected NFAImpl doBuild() {

            return new NFAImpl(this);
        }
    }
}
