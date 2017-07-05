package ab3.impl.Eberl.fa.algorithms.details;

import ab3.impl.Eberl.fa.NFAImpl;
import ab3.impl.Eberl.fa.RSAImpl;
import ab3.impl.Eberl.fa.details.DFAState;
import ab3.impl.Eberl.fa.details.FAState;
import at.syssec.fa.RSA;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for converting Non-deterministic Finite Automaton to Rabin-Scott Automaton.
 */
public class NFAToRSAConverter {

    private final NFAImpl nfa;

    public NFAToRSAConverter(NFAImpl nfa) {
        this.nfa = nfa;
    }

    public RSA convert() {

        // first build up the epsilon closure
        Map<Integer, Set<Integer>> epsilonClosure = getEpsilonClosure();

        // next compute new states and transitions
        List<DFAState> newStates = computeNewStates(epsilonClosure);

        // last but not least build up the final RSA
        RSA result = new RSAImpl.Builder()
                .setCharacters(nfa.getSymbols())
                .setInitialState(nfa.getInitialState())
                .setNumStates(newStates.size())
                .setAcceptingStates(newStates.stream().filter(DFAState::isAcceptingState).map(FAState::getId).collect(Collectors.toSet()))
                .build();

        // set transitions for final RSA
        for (DFAState state : newStates) {
            result.getSymbols().forEach(symbol -> result.setTransition(state.getId(), symbol, state.getNextState(symbol).getId()));
        }

        return result;
    }

    private List<DFAState> computeNewStates(Map<Integer, Set<Integer>> epsilonClosure) {

        List<DFAState> finalStates = new ArrayList<>();

        Set<Integer> acceptingStates = nfa.getAcceptingStates();
        Map<Set<Integer>, Integer> statesMap = new HashMap<>();
        Deque<Set<Integer>> statesToCheck = new ArrayDeque<>();

        // push the initial state
        Set<Integer> initial = epsilonClosure.get(nfa.getInitialState());
        finalStates.add(new DFAState(0, nfa.isAcceptingState(nfa.getInitialState())));
        statesToCheck.push(initial);
        statesMap.put(initial, 0);


        while (!statesToCheck.isEmpty()) {

            Set<Integer> currentState = statesToCheck.removeFirst();

            for (char symbol : nfa.getSymbols()) {

                Set<Integer> nextState = currentState.stream()
                        .map(state -> nfa.getNextStates(state, symbol)) // get all next states or each state
                        .flatMap(Collection::stream)                    // flatten
                        .distinct()                                     // distinct ids
                        .map(epsilonClosure::get)                       // get epsilon closures
                        .flatMap(Collection::stream)                    // flatten
                        .collect(Collectors.toSet());                   // get final set

                if (!statesMap.containsKey(nextState)) {
                    finalStates.add(new DFAState(statesMap.size(), nextState.stream().anyMatch(acceptingStates::contains)));
                    statesMap.put(nextState, statesMap.size());
                    statesToCheck.addLast(nextState);
                }

                // add transition for current state to next one
                DFAState currentDFAState = finalStates.get(statesMap.get(currentState));
                currentDFAState.addTransition(symbol, finalStates.get(statesMap.get(nextState)));
            }
        }

        return finalStates;
    }

    /**
     * Get epsilon closure.
     * @return Epsilon closure for each state.
     *
     * <p>
     *     This algorithm basically performs a Depth-First-Search.
     *     Maybe there is a more performant way, but as long as it works in acceptable time.
     * </p>
     */
    private Map<Integer,Set<Integer>> getEpsilonClosure() {

        Map<Integer,Set<Integer>> epsilonClosure = new HashMap<>();
        for (Integer state : nfa.getStates()) {

            Set<Integer> currentSet = new HashSet<>();
            epsilonClosure.put(state, currentSet);

            Stack<Integer> stack = new Stack<>();
            stack.push(state);

            while (!stack.isEmpty()) {
                Integer currentState = stack.pop();
                epsilonClosure.get(state).add(currentState);

                // get next states
                nfa.getNextStatesEpsilon(currentState)
                        .stream()
                        .filter(s -> !currentSet.contains(s))
                        .forEach(s -> {
                            currentSet.add(s);
                            stack.push(s);
                        });
            }
        }

        return epsilonClosure;
    }
}
