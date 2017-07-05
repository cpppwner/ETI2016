package ab3.impl.Eberl.fa.details;

import java.util.HashMap;
import java.util.Map;

/**
 * State class for DFAs and RSAs.
 */
public class DFAState extends FAState {

    /**
     * Map containing transitions.
     */
    private final Map<Character, DFAState> transitions;

    /**
     * Initializes a State instance.
     * @param id               The unique identifier of this state.
     * @param isAcceptingState Flag indicating if this is a final state ({@code true}) or not ({@code false}).
     */
    public DFAState(int id, boolean isAcceptingState) {
        super(id, isAcceptingState);

        transitions = new HashMap<>();
    }

    /**
     * Get number of transitions.
     * @return Number of transitions for this state.
     */
    public int getNumTransitions() {

        return transitions.size();
    }

    /**
     * Get next state for given symbol.
     * @param symbol The symbol for which to retrieve the next state.
     * @return The nexst {@link DFAState} or {@code null} if no next state is mapped.
     */
    public DFAState getNextState(char symbol) {

        return transitions.getOrDefault(symbol, null);
    }

    /**
     * Add a new transition to this state.
     * @param symbol The symbol for which to add a transition.
     * @param toState The next state for the transition.
     */
    public void addTransition(char symbol, DFAState toState) {

        transitions.put(symbol, toState);
    }
}
