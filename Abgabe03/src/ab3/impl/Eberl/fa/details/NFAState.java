package ab3.impl.Eberl.fa.details;

import java.util.*;

public class NFAState extends FAState {

    /**
     * Epsilon transitions of NFA state.
     */
    private final Set<NFAState> epsilonTransitions;

    /**
     * Regular transitions of NFA state.
     *
     * <p>
     *     Note: The same character can lead to different states -> non deterministic.
     * </p>
     */
    private final Map<Character, Set<NFAState>> transitions;


    /**
     * Initializes a State instance.
     * @param id               The unique identifier of this state.
     * @param isAcceptingState Flag indicating if this is a final state ({@code true}) or not ({@code false}).
     */
    public NFAState(int id, boolean isAcceptingState) {
        super(id, isAcceptingState);

        epsilonTransitions = new HashSet<>();
        transitions = new HashMap<>();
    }

    /**
     * Get total number of non-epsilon transition.
     * @return Total number of non-epsilon transitions.
     */
    public int getNumTransitions() {

        return transitions.values().stream().mapToInt(Set::size).sum();
    }

    /**
     * Get total number of epsilon transitions.
     * @return Total number of epsilon transitions.
     */
    public int getNumEpsilonTransitions() {

        return epsilonTransitions.size();
    }

    /**
     * Get all epsilon transitions.
     * @return Unmodifiable set containint all epsilon transitions.
     */
    public Set<NFAState> getEpsilonTransitions() {

        return Collections.unmodifiableSet(epsilonTransitions);
    }

    /**
     * Get a set containing all next states for given symbol.
     * @param symbol The symbol for which to retrieve a set of next states.
     * @return An unmodifiable set of next states for the given symbol or an empty set if no such transition exists.
     */
    public Set<NFAState> getNextStates(char symbol) {

        return transitions.containsKey(symbol)
                ? Collections.unmodifiableSet(transitions.get(symbol))
                : Collections.emptySet();
    }

    /**
     * Add a new epsilon transition.
     * @param toState The state to reach via epsilon.
     */
    public void addEpsilonTransition(NFAState toState) {

        epsilonTransitions.add(toState);
    }

    /**
     * Add transition.
     * @param symbol The symbol for which the transition is added.
     * @param toState The next state for the transition.
     */
    public void addTransition(char symbol, NFAState toState) {

        if (!transitions.containsKey(symbol)) {
            transitions.put(symbol, new HashSet<>());
        }

        transitions.get(symbol).add(toState);
    }
}
