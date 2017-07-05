package ab3.test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import ab3.impl.Eberl.FAFactoryImpl;
import at.syssec.fa.FAFactory;
import at.syssec.fa.NFA;
import at.syssec.fa.RSA;
import at.syssec.fa.impl.FATesting;

public class TestToRSA {

	private NFA n1; // leere Menge
	private NFA n2; // epsilon
	private NFA n3; // a*
	private NFA n4; // {a,b}*
	private NFA n5; // {a,b}* | c*
	private NFA n6; // {a,b,c}*
	private NFA n7; // Bsp 5.1
	private NFA n8; // Bsp 5.2
	private NFA n9; // irgendwas :)

	FAFactory factory = new FAFactoryImpl();

	public static final Set<Character> chars = new HashSet<>();

	static {
		chars.add('a');
		chars.add('b');
		chars.add('c');
	}

	@Before
	public void InitializeNFA1() {
		Set<Integer> accept = new TreeSet<Integer>();

		n1 = factory.generateNFA(1, chars, accept, 0);
	}

	@Before
	public void InitializeNFA2() {
		Set<Integer> accept = new TreeSet<Integer>();
		accept.add(0);

		n2 = factory.generateNFA(5, chars, accept, 0);
	}

	@Before
	public void InitializeNFA3() {
		Set<Integer> accept = new TreeSet<Integer>();
		accept.add(0);

		n3 = factory.generateNFA(1, chars, accept, 0);

		n3.setTransition(0, 'a', 0);
	}

	@Before
	public void InitializeNFA4() {
		Set<Integer> accept = new TreeSet<Integer>();
		accept.add(0);

		n4 = factory.generateNFA(5, chars, accept, 0);

		n4.setTransition(0, 'a', 0);
		n4.setTransition(0, 'b', 0);
	}

	@Before
	public void InitializeNFA5() {
		Set<Integer> accept = new TreeSet<Integer>();
		accept.add(0);
		accept.add(1);

		n5 = factory.generateNFA(2, chars, accept, 0);

		n5.setTransition(0, 'a', 0);
		n5.setTransition(0, 'b', 0);
		n5.setTransition(1, 'c', 1);
		n5.setEpsilonTransition(0, 1);
	}

	@Before
	public void InitializeNFA6() {
		Set<Integer> accept = new TreeSet<Integer>();
		accept.add(0);
		accept.add(1);

		n6 = factory.generateNFA(2, chars, accept, 0);

		n6.setTransition(0, 'a', 0);
		n6.setTransition(0, 'b', 0);
		n6.setTransition(1, 'c', 1);
		n6.setEpsilonTransition(0, 1);
		n6.setEpsilonTransition(1, 0);
	}

	@Before
	public void InitializeNFA7() {
		Set<Integer> accept = new TreeSet<Integer>();
		accept.add(4);

		n7 = factory.generateNFA(5, chars, accept, 0);

		n7.setTransition(0, 'b', 1);
		n7.setTransition(0, 'a', 2);
		n7.setTransition(0, 'a', 3);
		n7.setTransition(1, 'b', 2);
		n7.setTransition(1, 'a', 4);
		n7.setTransition(2, 'a', 2);
		n7.setTransition(2, 'b', 4);
		n7.setTransition(3, 'b', 2);
	}

	@Before
	public void InitializeNFA8() {
		Set<Integer> accept = new TreeSet<Integer>();
		accept.add(3);
		accept.add(4);

		n8 = factory.generateNFA(5, chars, accept, 0);

		n8.setTransition(0, 'a', 1);
		n8.setTransition(0, 'b', 2);
		n8.setTransition(1, 'b', 4);
		n8.setTransition(2, 'b', 2);
		n8.setTransition(2, 'a', 1);
		n8.setTransition(2, 'a', 3);
		n8.setTransition(4, 'b', 2);
		n8.setTransition(4, 'a', 3);

		n8.setEpsilonTransition(1, 0);
		n8.setEpsilonTransition(4, 0);
		n8.setEpsilonTransition(3, 1);
		n8.setEpsilonTransition(4, 2);
	}

	@Before
	public void InitializeNFA9() {
		Set<Integer> accept = new TreeSet<Integer>();
		accept.add(7);

		n9 = factory.generateNFA(8, chars, accept, 0);

		n9.setTransition(5, 'a', 6);
		n9.setTransition(5, 'b', 1);
		n9.setTransition(6, 'b', 7);
		n9.setTransition(6, 'a', 3);
		n9.setTransition(7, 'b', 7);
		n9.setTransition(7, 'a', 7);
		n9.setTransition(3, 'b', 4);
		n9.setTransition(3, 'a', 0);
		n9.setTransition(0, 'b', 1);
		n9.setTransition(0, 'a', 0);
		n9.setTransition(2, 'a', 3);
		n9.setTransition(2, 'b', 1);
		n9.setTransition(1, 'a', 2);
		n9.setTransition(1, 'b', 1);
		n9.setTransition(4, 'b', 5);
		n9.setTransition(4, 'a', 2);
		n9.setTransition(1, 'c', 4);
		n9.setTransition(2, 'c', 6);
		n9.setTransition(5, 'c', 3);
		n9.setEpsilonTransition(1, 6);
		n9.setEpsilonTransition(2, 4);
		n9.setEpsilonTransition(7, 3);
	}

	private void testToRSA(NFA n) {
		RSA r = n.toRSA();
		assertEquals(true, FATesting.isValidRSA(r));
		assertEquals(true, FATesting.equals(r, n));
	}

	@Test
	public void test_NFA1() {
		testToRSA(n1);
	}

	@Test
	public void test_NFA2() {
		testToRSA(n2);
	}

	@Test
	public void test_NFA3() {
		testToRSA(n3);
	}

	@Test
	public void test_NFA4() {
		testToRSA(n4);
	}

	@Test
	public void test_NFA5() {
		testToRSA(n5);
	}

	@Test
	public void test_NFA6() {
		testToRSA(n6);
	}

	@Test
	public void test_NFA7() {
		testToRSA(n7);
	}

	@Test
	public void test_NFA8() {
		testToRSA(n8);
	}

	@Test
	public void test_NFA9() {
		testToRSA(n9);
	}
}