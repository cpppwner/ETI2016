package ab4.impl.Eberl;

import at.syssec.fa.NFA;
import at.syssec.fa.NFAOperations;
import at.syssec.fa.RSA;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NFAOperationsImpl implements NFAOperations {

	@Override
	public NFA complement(NFA arg0) {

		// complement of NFA
		// is done by first converting the given NFA to RSA and afterwards swapping the accepting states
		RSA rsa = arg0.toRSA();

		Set<Integer> acceptingStates = IntStream.range(0, rsa.getNumStates())
                .filter(state -> !rsa.getAcceptingStates().contains(state))
                .boxed()
                .collect(Collectors.toSet());

        NFA result = new FAFactoryImpl().generateNFA(rsa.getNumStates(), rsa.getSymbols(), acceptingStates, rsa.getInitialState());

        // copy  transitions
        copyTransitions(rsa, result, 0);

        return result;
	}

	@Override
	public NFA concat(NFA arg0, NFA arg1) {

		// concat two NFAs by adding epsilon transitions from each accepting state of first
		// NFA to start state of second NFA.

		// create new NFA
		int numStates = arg0.getNumStates() + arg1.getNumStates();
		Set<Character> characters = new HashSet<>();
		characters.addAll(arg0.getSymbols());
		characters.addAll(arg1.getSymbols());
		int initialState = arg0.getInitialState();
		Set<Integer> acceptingStates = arg1.getAcceptingStates().stream().map(state -> state + arg0.getNumStates()).collect(Collectors.toSet());

		NFA result = new FAFactoryImpl().generateNFA(numStates, characters, acceptingStates, initialState);

		// fill up transitions
		copyTransitions(arg0, result, 0);
		copyTransitions(arg1, result, arg0.getNumStates());

		// fill up epsilon transitions from first NFA to second
		arg0.getAcceptingStates().forEach(acceptingState -> result.setEpsilonTransition(acceptingState, arg1.getInitialState() + arg0.getNumStates()));

		return result;
	}

	@Override
	public NFA intersection(NFA arg0, NFA arg1) {

	    return complement(union(complement(arg0), complement(arg1)));
	}

	@Override
	public NFA kleeneStar(NFA arg0) {

		// Kleene-Star of NFA
		// add new initial state + epsilon transition from new initial state to previous initial state
		// and also add epsilon transitions from accepting states to previous initial state

		// create new NFA
		int numStates = arg0.getNumStates() + 1;
		Set<Character> characters = arg0.getSymbols();
		Set<Integer> acceptingStates = Stream.concat(arg0.getAcceptingStates().stream().map(state -> state + 1),  Stream.of(0)).collect(Collectors.toSet());

		NFA result = new FAFactoryImpl().generateNFA(numStates, characters, acceptingStates, 0);

		// fill up already existing transitions
		copyTransitions(arg0, result, 1);

		// add new epsilon transition for starting state
		result.setEpsilonTransition(0, arg0.getInitialState() + 1);

		// add epsilon transitions from accepting accepting states (except first one) to previous initial state
		arg0.getAcceptingStates().forEach(acceptingState -> result.setEpsilonTransition(acceptingState + 1, arg0.getInitialState() + 1));

		return result;
	}

	@Override
	public NFA union(NFA arg0, NFA arg1) {

		// union two NFAs by adding new initial state
		// + adding epsilon transitions from new initial state to both previous initial states

		// create new NFA
		int numStates = arg0.getNumStates() + arg1.getNumStates() + 1;
		Set<Character> characters = new HashSet<>();
		characters.addAll(arg0.getSymbols());
		characters.addAll(arg1.getSymbols());
		Set<Integer> acceptingStates = Stream.concat(arg0.getAcceptingStates().stream().map(state -> state + 1),
				                                     arg1.getAcceptingStates().stream().map(state -> state + arg0.getNumStates() + 1))
				.collect(Collectors.toSet());

		NFA result = new FAFactoryImpl().generateNFA(numStates, characters, acceptingStates, 0);

		// fill up transitions
		copyTransitions(arg0, result, 1);
		copyTransitions(arg1, result, arg0.getNumStates() + 1);

		// fill up epsilon transitions from initial state to previous initial states
		result.setEpsilonTransition(0, arg0.getInitialState() + 1);
		result.setEpsilonTransition(0, arg1.getInitialState() + arg0.getNumStates() + 1);

		return result;
	}

	@Override
	public NFA plus(NFA a) {

		// Plus of NFA -> similar to Kleene star, except no new state is added, only
		// epsilon transitions from accepting states to initial state

		// create new NFA
		NFA result = copyNFA(a);

		result.getAcceptingStates().forEach(acceptingState -> result.setEpsilonTransition(acceptingState, result.getInitialState()));

		return result;
	}

	@Override
	public NFA minus(NFA a, NFA b) {

	    return intersection(a, complement(b));
	}

	private static NFA copyNFA(NFA nfa) {

		NFA result = new FAFactoryImpl().generateNFA(nfa.getNumStates(), nfa.getSymbols(), nfa.getAcceptingStates(), nfa.getInitialState());

		// fill up already existing transitions
		copyTransitions(nfa, result, 0);

		return result;
	}

	private static void copyTransitions(NFA source, NFA target, final int offsetInTarget) {

		for (int s = 0; s < source.getNumStates(); s++) {
			final int state = s;
			for (char c : source.getSymbols()) {
				source.getNextStates(s, c).forEach(nextState -> target.setTransition(state + offsetInTarget, c, nextState + offsetInTarget));
			}
			source.getNextStatesEpsilon(s).forEach(nextStateEpsilon -> target.setEpsilonTransition(state + offsetInTarget, nextStateEpsilon + offsetInTarget));
		}
	}
}
