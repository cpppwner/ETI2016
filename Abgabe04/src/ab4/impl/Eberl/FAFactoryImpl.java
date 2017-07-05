package ab4.impl.Eberl;

import java.util.Set;

import at.syssec.fa.DFA;
import at.syssec.fa.DFAImpl;
import at.syssec.fa.FAFactory;
import at.syssec.fa.NFA;
import at.syssec.fa.NFAImpl;
import at.syssec.fa.RSA;
import at.syssec.fa.RSACreator;
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

	@Override
	public NFA generateNFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Integer initialState)
			throws IllegalAcceptingStateException, IllegalInitialStateException, IllegalStateException {
		NFAImpl n = new NFAImpl(numStates, characters, acceptingStates, initialState);
		
		//Standardimplementierung
		n.setRsaCreator(FATesting::toRSA);
		
		n.setNfaOperations(new NFAOperationsImpl());
		return n;
	}

	@Override
	public DFA generateDFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Integer initialState)
			throws IllegalAcceptingStateException, IllegalInitialStateException, IllegalStateException {
		DFAImpl d = new DFAImpl(numStates, characters, acceptingStates, initialState);
		
		//Standardimplementierung
		d.setRsaCreator(FATesting::toRSA);
		
		d.setNfaOperations(new NFAOperationsImpl());
		return d;
	}

	@Override
	public RSA generateRSA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Integer initialState)
			throws IllegalAcceptingStateException, IllegalInitialStateException, IllegalStateException {
		RSAImpl r = new RSAImpl(numStates, characters, acceptingStates, initialState);
		
		//Standardimplementierung
		r.setRsaCreator(FATesting::toRSA);
		
		r.setNfaOperations(new NFAOperationsImpl());
		return r;
	}

}
