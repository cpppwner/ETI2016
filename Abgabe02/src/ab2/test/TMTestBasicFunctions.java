package ab2.test;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import ab2.Movement;
import ab2.TM;
import ab2.TMConfig;

public class TMTestBasicFunctions {

	private TM machine = TestUtils.getImpl();
	
	@Test
	public void testSymbols() {
		//Tests for equality of given symbols and returned symbols
		HashSet<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b', 'c', '#'));
		machine.reset();
		machine.setSymbols(alphabet);
		Assert.assertEquals(alphabet, machine.getSymbols());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNoSpace() {
		//Tests for acceptance of symbols without '#' among them
		HashSet<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b', 'c'));
		machine.reset();
		machine.setSymbols(alphabet);
	}
	
	@Test
	public void testStates() {
		//Tests for correct state set after adding some transitions
		machine.reset();
		machine.setSymbols(new HashSet<Character>(Arrays.asList('a', 'b', 'c', '#')));
		machine.addTransition(1, 'a', 2, 'a', Movement.Left);
		machine.addTransition(2, 'b', 3, 'c', Movement.Stay);
		machine.addTransition(2, 'a', 3, 'c', Movement.Stay);
		Assert.assertEquals(new HashSet<Integer>(Arrays.asList(0,1,2,3)), machine.getStates());
	}

	@Test(expected=IllegalStateException.class)
	public void testNoTransition() {
		//Tries to do a step with no corresponding transition
		machine.reset();
		machine.setSymbols(new HashSet<Character>(Arrays.asList('a', 'b', 'c', '#')));
		machine.addTransition(1, 'a', 2, 'a', Movement.Left);
		machine.addTransition(2, 'b', 3, 'c', Movement.Stay);
		machine.addTransition(2, 'a', 3, 'c', Movement.Stay);
		machine.setInitialState(1);
		machine.doNextStep();
	}

	@Test(expected=IllegalStateException.class)
	public void testNoTransition2() {
		//Tries to do a step with no transitions at all
		machine.reset();
		machine.setSymbols(new HashSet<Character>(Arrays.asList('a', 'b', 'c', '#')));
		machine.setInitialState(1);
		machine.doNextStep();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHaltTransition() {
		//Tries to add a transition from halt state
		machine.reset();
		machine.setSymbols(new HashSet<Character>(Arrays.asList('a', 'b', 'c', '#')));
		machine.addTransition(0, 'a', 1, 'b', Movement.Stay);
	}
	
	@Test
	public void testHalt() {
		//Tries to reach halt state through a single transition
		machine.reset();
		machine.setSymbols(new HashSet<Character>(Arrays.asList('a', 'b', 'c', '#')));
		machine.addTransition(1, '#', 0, '#', Movement.Right);
		machine.setInitialState(1);
		machine.doNextStep();
		Assert.assertTrue(machine.isHalt());
	}

	@Test
	public void testCrash() {
		//Produces a crash (no transitions) and checks the crashed flag afterwards
		machine.reset();
		machine.setSymbols(new HashSet<Character>(Arrays.asList('a', 'b', 'c', '#')));
		machine.setInitialState(1);
		try{
			machine.doNextStep();
		}catch(IllegalStateException ex){}
		Assert.assertTrue(machine.isCrashed());
	}
	
	@Test 
	public void testTape() {
		//Tape must be empty and head position should be 0 after reset
		TM x = TestUtils.getImpl();
		x.setSymbols(new HashSet<Character>(Arrays.asList('a','b','#')));
		x.setInitialTapeContent("#a#b#".toCharArray());
		TMConfig config = x.getTMConfig();
		Assert.assertTrue(new String(config.getLeftOfHead()).equals("#a#b#") && 
				config.getBelowHead() == '#' && 
				config.getRightOfHead().length == 0);
	}

}
