package ab3.test.fa.details;

import ab3.impl.Eberl.fa.details.FAState;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 * Tests for state class.
 */
public class FAStateTests {

    @Test
    public void constructingAState() {

        // given
        FAState stateOne = new TestFAState(1, false);
        FAState stateTwo = new TestFAState(2, true);

        // then
        Assert.assertThat(stateOne.getId(), is(1));
        Assert.assertThat(stateOne.isAcceptingState(), is(false));

        Assert.assertThat(stateTwo.getId(), is(2));
        Assert.assertThat(stateTwo.isAcceptingState(), is(true));
    }

    @Test
    public void checkingStateEqualityWithSomethingElseThanStateGivesFalse() {

        // given
        FAState state = new TestFAState(1, false);

        // then
        Assert.assertThat(state.equals(null), is(false));
        Assert.assertThat(state.equals(Integer.valueOf(1)), is(false));
        Assert.assertThat(state.equals(new Object()), is(false));
    }

    @Test
    public void twoStatesAreNotEqualIfIdIsNotEqual() {

        // given
        FAState state = new TestFAState(1, false);

        // then
        Assert.assertThat(state.equals(new TestFAState(2, false)), is(false));
    }

    @Test
    public void twoStatesAreEqualIfTheIdIsEqual() {

        // given
        FAState state = new TestFAState(1, false);

        // when same id/then
        Assert.assertThat(state.equals(new TestFAState(1, false)), is(true));

        // when same id, but different accepting state/then
        Assert.assertThat(state.equals(new TestFAState(1, true)), is(true));
    }

    @Test
    public void hashCodeIsBasedOnStatesId() {

        // given
        FAState stateOne = new TestFAState(1, false);
        FAState stateTwo = new TestFAState(1, true);

        // then
        Assert.assertThat(stateOne.hashCode(), is(Integer.hashCode(1)));
        Assert.assertThat(stateTwo.hashCode(), is(Integer.hashCode(1)));
    }

    /**
     * Test class
     */
    private static class TestFAState extends FAState {

        TestFAState(int id, boolean isAcceptingState) {
            super(id, isAcceptingState);
        }
    }
}
