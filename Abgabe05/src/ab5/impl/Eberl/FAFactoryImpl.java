package ab5.impl.Eberl;

import java.util.Set;

import at.syssec.fa.DFA;
import at.syssec.fa.DFAImpl;
import at.syssec.fa.FAFactory;
import at.syssec.fa.NFA;
import at.syssec.fa.NFAImpl;
import at.syssec.fa.RSA;
import at.syssec.fa.RSAImpl;
import at.syssec.fa.exceptions.IllegalAcceptingStateException;
import at.syssec.fa.exceptions.IllegalInitialStateException;
import at.syssec.fa.impl.FATesting;

/**
 * Die drei Typen von Automaten werden hier erzeugt. Es wird jeweils der
 * RSACreator gesetzt, damit die Automaten über die toRSA-Methode in einen RSA
 * umgewandelt werden können.
 */
public class FAFactoryImpl implements FAFactory {
	
	private FATesting refImpl = new FATesting(this);

	@Override
	public NFA generateNFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Integer initialState)
			throws IllegalAcceptingStateException, IllegalInitialStateException, IllegalStateException {
		NFAImpl n = new NFAImpl(numStates, characters, acceptingStates, initialState);

		n.setNfaOperations(refImpl);
		n.setRsaCreator(refImpl);
		n.setLanguageChecker(refImpl);
		return n;
	}

	@Override
	public DFA generateDFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Integer initialState)
			throws IllegalAcceptingStateException, IllegalInitialStateException, IllegalStateException {
		DFAImpl d = new DFAImpl(numStates, characters, acceptingStates, initialState);
		
		d.setNfaOperations(refImpl);
		d.setRsaCreator(refImpl);
		d.setLanguageChecker(refImpl);
		return d;
	}

	@Override
	public RSA generateRSA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Integer initialState)
			throws IllegalAcceptingStateException, IllegalInitialStateException, IllegalStateException {
		RSAImpl r = new RSAImpl(numStates, characters, acceptingStates, initialState);
		
		r.setNfaOperations(refImpl);
		r.setRsaCreator(refImpl);
		r.setLanguageChecker(refImpl);
		
		r.setMinimizer(new RSAMinimizerImpl());
		return r;
	}

}
