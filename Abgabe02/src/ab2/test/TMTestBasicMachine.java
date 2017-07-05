package ab2.test;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ab2.Movement;
import ab2.TM;
import ab2.TMConfig;

public class TMTestBasicMachine {

	private static TM machine;
	
	@BeforeClass
	public static void setup(){
		machine = TestUtils.getImpl();
		
		machine.setSymbols(new HashSet<Character>(Arrays.asList('a', '#')));
		machine.addTransition(3, '#', 1, '#', Movement.Left);
		machine.addTransition(1, 'a', 2, '#', Movement.Stay);
		machine.addTransition(2, '#', 1, '#', Movement.Left);
		machine.addTransition(1, '#', 0, '#', Movement.Stay);
		machine.setInitialState(3);
		machine.setInitialTapeContent("#aaaaa".toCharArray());
		
		while (!machine.isHalt())
			machine.doNextStep();
	}
	
	@Test
	public void testLeft() {
		TMConfig config = machine.getTMConfig();
		Assert.assertArrayEquals(config.getLeftOfHead(), new char[0]);
	}

	@Test
	public void testHead() {
		TMConfig config = machine.getTMConfig();
		Assert.assertEquals(config.getBelowHead(), '#');
	}
	
	@Test
	public void testRight() {
		TMConfig config = machine.getTMConfig();
		Assert.assertArrayEquals(config.getRightOfHead(), new char[0]);
	}

}
