package ab3.test.fa.details;

import ab3.impl.Eberl.fa.details.DFAState;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 * Test class for testing DFAState
 */
public class DFAStateTests {

    @Test
    public void aDefaultConstructedDFAStatePassesArgumentToParentAccordingly() {

        // given
        DFAState dfaStateOne = new DFAState(1, true);
        DFAState dfaStateTwo = new DFAState(2, false);

        // then
        Assert.assertThat(dfaStateOne.getId(), is(1));
        Assert.assertThat(dfaStateOne.isAcceptingState(), is(true));

        Assert.assertThat(dfaStateTwo.getId(), is(2));
        Assert.assertThat(dfaStateTwo.isAcceptingState(), is(false));
    }

    @Test
    public void aDefaultConstructedDFAStateHasNoTransitions() {

        // given
        DFAState dfaState = new DFAState(1, true);

        // then
        Assert.assertThat(dfaState.getNumTransitions(), is(0));
    }

    @Test
    public void getNextStateRetrievesPreviouslyAddedTransitionState() {

        // given
        DFAState dfaStateOne = new DFAState(1, true);
        DFAState dfaStateTwo = new DFAState(2, false);

        dfaStateOne.addTransition('a', dfaStateTwo);
        dfaStateTwo.addTransition('b', dfaStateOne);

        // when/then
        Assert.assertThat(dfaStateOne.getNextState('a'), is(sameInstance(dfaStateTwo)));
        Assert.assertThat(dfaStateTwo.getNextState('b'), is(sameInstance(dfaStateOne)));
    }

    @Test
    public void getNextStateReturnsNullIfNoSuchTransitionExists() {

        // given
        DFAState dfaStateOne = new DFAState(1, true);
        DFAState dfaStateTwo = new DFAState(2, false);

        dfaStateOne.addTransition('a', dfaStateTwo);
        dfaStateTwo.addTransition('b', dfaStateOne);

        // when/then
        Assert.assertThat(dfaStateOne.getNextState('b'), is(nullValue()));
        Assert.assertThat(dfaStateTwo.getNextState('a'), is(nullValue()));
    }

    @Test
    public void addingATransitionForExistingSymbolChangesNextState() {

        // given
        DFAState dfaStateOne = new DFAState(1, true);
        DFAState dfaStateTwo = new DFAState(2, false);
        DFAState dfaStateThree = new DFAState(3, false);

        // when
        dfaStateOne.addTransition('a', dfaStateTwo);

        // then
        Assert.assertThat(dfaStateOne.getNumTransitions(), is(1));
        Assert.assertThat(dfaStateOne.getNextState('a'), is(sameInstance(dfaStateTwo)));

        // and when
        dfaStateOne.addTransition('a', dfaStateThree);

        // then
        Assert.assertThat(dfaStateOne.getNumTransitions(), is(1));
        Assert.assertThat(dfaStateOne.getNextState('a'), is(sameInstance(dfaStateThree)));
    }

    @Test
    public void selfTransitionAlsoWorks() {

        // given
        DFAState dfaStateOne = new DFAState(1, true);

        // when
        dfaStateOne.addTransition('a', dfaStateOne);

        // then
        Assert.assertThat(dfaStateOne.getNumTransitions(), is(1));
        Assert.assertThat(dfaStateOne.getNextState('a'), is(sameInstance(dfaStateOne)));

        // and when adding self-transition for other symbol
        dfaStateOne.addTransition('b', dfaStateOne);

        // then
        Assert.assertThat(dfaStateOne.getNumTransitions(), is(2));
        Assert.assertThat(dfaStateOne.getNextState('b'), is(sameInstance(dfaStateOne)));
    }
}
