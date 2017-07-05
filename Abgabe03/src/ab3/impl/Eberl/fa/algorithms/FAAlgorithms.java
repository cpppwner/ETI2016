package ab3.impl.Eberl.fa.algorithms;

import ab3.impl.Eberl.fa.DFAImpl;
import ab3.impl.Eberl.fa.NFAImpl;
import ab3.impl.Eberl.fa.RSAImpl;
import ab3.impl.Eberl.fa.algorithms.details.DFAToRSAConverter;
import ab3.impl.Eberl.fa.algorithms.details.NFAToRSAConverter;
import at.syssec.fa.RSA;

/**
 * Finite Automaton algorithm interface.
 */
public class FAAlgorithms {

    /**
     * Convert RSA to RSA.
     * @param rsa The input RSA to convert.
     * @return Returns RSA passed as input.
     *
     * <p>
     *     This method does not really do any conversions, but rather
     * </p>
     */
    public static RSA toRSA(RSAImpl rsa) {

        return rsa;
    }

    public static RSA toRSA(DFAImpl dfa) {

        return new DFAToRSAConverter(dfa).convert();
    }

    public static RSA toRSA(NFAImpl nfa) {

        return new NFAToRSAConverter(nfa).convert();
    }
}
