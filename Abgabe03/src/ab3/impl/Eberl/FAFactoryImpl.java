package ab3.impl.Eberl;

import java.util.Set;

import ab3.impl.Eberl.fa.DFAImpl;
import ab3.impl.Eberl.fa.NFAImpl;
import ab3.impl.Eberl.fa.RSAImpl;
import at.syssec.fa.DFA;
import at.syssec.fa.FAFactory;
import at.syssec.fa.NFA;
import at.syssec.fa.RSA;
import at.syssec.fa.exceptions.IllegalAcceptingStateException;
import at.syssec.fa.exceptions.IllegalInitialStateException;

/**
 * Factory uses appropriate builders to build up the instances.
 * The algorithm instances are passed on, which to conversions.
 */
public class FAFactoryImpl implements FAFactory {

	@Override
	public NFA generateNFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Integer initialState)
			throws IllegalAcceptingStateException, IllegalInitialStateException, IllegalStateException {


		return new NFAImpl.Builder()
				.setNumStates(numStates)
				.setCharacters(characters)
				.setAcceptingStates(acceptingStates)
				.setInitialState(initialState)
				.build();
	}

	@Override
	public DFA generateDFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Integer initialState)
			throws IllegalAcceptingStateException, IllegalInitialStateException, IllegalStateException {

		return new DFAImpl.Builder()
				.setNumStates(numStates)
				.setCharacters(characters)
				.setAcceptingStates(acceptingStates)
				.setInitialState(initialState)
				.build();
	}

	@Override
	public RSA generateRSA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Integer initialState)
			throws IllegalAcceptingStateException, IllegalInitialStateException, IllegalStateException {

		return new RSAImpl.Builder()
				.setNumStates(numStates)
				.setCharacters(characters)
				.setAcceptingStates(acceptingStates)
				.setInitialState(initialState)
				.build();
	}
}
