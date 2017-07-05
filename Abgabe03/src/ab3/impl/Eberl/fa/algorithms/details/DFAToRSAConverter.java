package ab3.impl.Eberl.fa.algorithms.details;

import ab3.impl.Eberl.fa.DFAImpl;
import ab3.impl.Eberl.fa.RSAImpl;
import at.syssec.fa.RSA;

/**
 * Class converting Deterministic Finite Automaton to Rabin-Scott Automaton.
 */
public class DFAToRSAConverter {

    private final DFAImpl dfa;

    public DFAToRSAConverter(DFAImpl dfa) {
        this.dfa = dfa;
    }

    public RSA convert() {

        boolean hasMissingTransitions = hasMissingTransitions();
        RSA result = new RSAImpl.Builder()
                .setNumStates(hasMissingTransitions ? dfa.getNumStates() + 1 : dfa.getNumStates())
                .setAcceptingStates(dfa.getAcceptingStates())
                .setCharacters(dfa.getSymbols())
                .setInitialState(dfa.getInitialState())
                .build();

        for (int state : dfa.getStates()) {
            for (char symbol : dfa.getSymbols()) {
                Integer nextState = dfa.getNextState(state, symbol);
                if (nextState != null) {
                    result.setTransition(state, symbol, nextState);
                }
            }
        }

        return result;
    }

    private boolean hasMissingTransitions() {

        for (int state : dfa.getStates()) {
            for (char symbol : dfa.getSymbols()) {
                if (dfa.getNextState(state, symbol) == null) {
                    return true;
                }
            }
        }

        return false;
    }
}
