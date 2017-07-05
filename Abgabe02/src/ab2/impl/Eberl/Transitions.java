package ab2.impl.Eberl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Container class for storing {@link Transition}.
 */
class Transitions {

    // map for storing the transitions.
    private final Map<Integer, Map<Character, Transition>> transitionsMap;

    /**
     * Constructor.
     */
    Transitions() {

        transitionsMap = new HashMap<>();
    }

    boolean addState(int state) {
        if (transitionsMap.containsKey(state))
            return false;

        transitionsMap.put(state, new HashMap<>());
        return true;
    }

    boolean addTransition(Transition transition) {

        // check if transition is conflicting
        if (hasTransition(transition.getFromState(), transition.getSymbolRead()))
            return false;

        addState(transition.getFromState());
        addState(transition.getToState());

        transitionsMap.get(transition.getFromState()).put(transition.getSymbolRead(), transition);

        return true;
    }

    boolean hasTransition(int state, char readChar) {

        return (transitionsMap.containsKey(state) && transitionsMap.get(state).containsKey(readChar));
    }

    Transition getTransition(int state, char readChar) {

        if (!hasTransition(state, readChar))
            return null;

        return transitionsMap.get(state).get(readChar);
    }

    Set<Integer> getStates() {
        return transitionsMap.keySet();
    }
}
