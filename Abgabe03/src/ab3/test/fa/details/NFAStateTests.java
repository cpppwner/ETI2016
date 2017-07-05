package ab3.test.fa.details;

import ab3.impl.Eberl.fa.details.NFAState;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 * Test class for testing NFAState class.
 */
public class NFAStateTests {

    @Test
    public void aDefaultConstructedNFAStatePassesArgumentToParentAccordingly() {

        // given
        NFAState nfaStateOne = new NFAState(1, true);
        NFAState nfaStateTwo = new NFAState(2, false);

        // then
        Assert.assertThat(nfaStateOne.getId(), is(1));
        Assert.assertThat(nfaStateOne.isAcceptingState(), is(true));

        Assert.assertThat(nfaStateTwo.getId(), is(2));
        Assert.assertThat(nfaStateTwo.isAcceptingState(), is(false));
    }

    @Test
    public void aDefaultConstructedNFAStateHasNoTransitions() {

        // given
        NFAState nfaState = new NFAState(1, true);

        // then
        Assert.assertThat(nfaState.getNumTransitions(), is(0));
    }

    @Test
    public void aDefaultConstructedNFAStateHasNoEpsilonTransitions() {

        // given
        NFAState nfaState = new NFAState(1, true);

        // then
        Assert.assertThat(nfaState.getNumEpsilonTransitions(), is(0));
    }

    @Test
    public void addEpsilonTransitionAddsTheNextStateToSet() {

        // given
        NFAState nfaStateOne = new NFAState(1, true);
        NFAState nfaStateTwo = new NFAState(2, false);
        NFAState nfaStateThree = new NFAState(3, true);

        // then
        Assert.assertThat(nfaStateOne.getNumEpsilonTransitions(), is(0));

        // and when adding an epsilon transition
        nfaStateOne.addEpsilonTransition(nfaStateTwo);

        // then
        Assert.assertThat(nfaStateOne.getNumEpsilonTransitions(), is(1));
        Assert.assertThat(nfaStateOne.getEpsilonTransitions(), hasItem(nfaStateTwo));
        Assert.assertThat(nfaStateTwo.getNumEpsilonTransitions(), is(0));

        // and when adding another epsilon transition
        nfaStateOne.addEpsilonTransition(nfaStateThree);

        // then
        Assert.assertThat(nfaStateOne.getNumEpsilonTransitions(), is(2));
        Assert.assertThat(nfaStateOne.getEpsilonTransitions(), hasItems(nfaStateTwo, nfaStateThree));
        Assert.assertThat(nfaStateThree.getNumEpsilonTransitions(), is(0));
    }

    @Test
    public void addEpsilonSelfTransitionWorks() {

        // given
        NFAState nfaStateOne = new NFAState(1, true);

        // when
        nfaStateOne.addEpsilonTransition(nfaStateOne);

        // then
        Assert.assertThat(nfaStateOne.getNumEpsilonTransitions(), is(1));
        Assert.assertThat(nfaStateOne.getEpsilonTransitions(), hasItem(nfaStateOne));
    }

    @Test
    public void addingStateWithSameIdToEpsilonTransitionReplacesOriginalStateObject() {

        // given
        NFAState nfaStateOne = new NFAState(1, true);
        NFAState nfaStateTwo = new NFAState(2, false);
        NFAState nfaStateThree = new NFAState(2, true);

        // then
        Assert.assertThat(nfaStateOne.getNumEpsilonTransitions(), is(0));

        // and when adding an epsilon transition
        nfaStateOne.addEpsilonTransition(nfaStateTwo);

        // then
        Assert.assertThat(nfaStateOne.getNumEpsilonTransitions(), is(1));
        Assert.assertThat(nfaStateOne.getEpsilonTransitions(), hasItem(nfaStateTwo));
        Assert.assertThat(nfaStateTwo.getNumEpsilonTransitions(), is(0));

        // and when adding another epsilon transition
        nfaStateOne.addEpsilonTransition(nfaStateThree);

        // then
        Assert.assertThat(nfaStateOne.getNumEpsilonTransitions(), is(1));
        Assert.assertThat(nfaStateOne.getEpsilonTransitions(), hasItems(nfaStateThree));
        Assert.assertThat(nfaStateThree.getNumEpsilonTransitions(), is(0));
    }

    @Test
    public void addTransitionAddsTheNextStateToSet() {

        // given
        NFAState nfaStateOne = new NFAState(1, true);
        NFAState nfaStateTwo = new NFAState(2, false);
        NFAState nfaStateThree = new NFAState(3, true);

        // then
        Assert.assertThat(nfaStateOne.getNumTransitions(), is(0));

        // and when adding first transitions
        nfaStateOne.addTransition('a', nfaStateTwo);
        nfaStateOne.addTransition('b', nfaStateThree);

        // then
        Assert.assertThat(nfaStateOne.getNumTransitions(), is(2));
        Assert.assertThat(nfaStateTwo.getNumTransitions(), is(0));
        Assert.assertThat(nfaStateThree.getNumTransitions(), is(0));

        Assert.assertThat(nfaStateOne.getNextStates('a'), hasItem(nfaStateTwo));
        Assert.assertThat(nfaStateOne.getNextStates('b'), hasItem(nfaStateThree));

        // and when adding some more transitions
        nfaStateOne.addTransition('a', nfaStateThree);
        nfaStateOne.addTransition('b', nfaStateTwo);

        // then
        Assert.assertThat(nfaStateOne.getNumTransitions(), is(4));
        Assert.assertThat(nfaStateTwo.getNumTransitions(), is(0));
        Assert.assertThat(nfaStateThree.getNumTransitions(), is(0));

        Assert.assertThat(nfaStateOne.getNextStates('a'), hasItems(nfaStateTwo, nfaStateThree));
        Assert.assertThat(nfaStateOne.getNextStates('b'), hasItems(nfaStateTwo, nfaStateThree));
    }

    @Test
    public void addingStateWithSameIdToTransitionReplacesOriginalStateObject() {

        // given
        NFAState nfaStateOne = new NFAState(1, true);
        NFAState nfaStateTwo = new NFAState(2, false);
        NFAState nfaStateThree = new NFAState(2, true);

        // and when adding first transitions
        nfaStateOne.addTransition('a', nfaStateTwo);
        nfaStateOne.addTransition('b', nfaStateThree);

        // then
        Assert.assertThat(nfaStateOne.getNumTransitions(), is(2));
        Assert.assertThat(nfaStateTwo.getNumTransitions(), is(0));
        Assert.assertThat(nfaStateThree.getNumTransitions(), is(0));

        Assert.assertThat(nfaStateOne.getNextStates('a'), hasItem(nfaStateTwo));
        Assert.assertThat(nfaStateOne.getNextStates('b'), hasItem(nfaStateThree));

        // and when adding transition with same ids
        nfaStateOne.addTransition('a', nfaStateThree);
        nfaStateOne.addTransition('b', nfaStateTwo);

        // then
        Assert.assertThat(nfaStateOne.getNumTransitions(), is(2));
        Assert.assertThat(nfaStateTwo.getNumTransitions(), is(0));
        Assert.assertThat(nfaStateThree.getNumTransitions(), is(0));

        Assert.assertThat(nfaStateOne.getNextStates('a'), hasItem(nfaStateThree));
        Assert.assertThat(nfaStateOne.getNextStates('b'), hasItem(nfaStateTwo));
    }
}
