package ab3.impl.Eberl.fa;

import ab3.impl.Eberl.fa.algorithms.FAAlgorithms;
import at.syssec.fa.RSA;

/**
 * Implementation of Rabin-Scott-Automaton class.
 */
public class RSAImpl extends DFAImpl implements RSA {

    private RSAImpl(FABuilder<?> builder) {
        super(builder);

        for (Character symbol : getSymbols()) {
            getStates().forEach(state -> setTransition(state, symbol, getNumStates() - 1));
        }
    }

    @Override
    public RSA toRSA() {

        return FAAlgorithms.toRSA(this);
    }

    @Override
    public RSA minimize() {
        return null;
    }

    /**
     * Fluent builder class for creating DFA.
     */
    public static class Builder extends FABuilder<RSAImpl> {

        @Override
        protected RSAImpl doBuild() {

            return new RSAImpl(this);
        }
    }
}
