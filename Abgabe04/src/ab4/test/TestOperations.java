package ab4.test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import ab4.impl.Eberl.FAFactoryImpl;
import at.syssec.fa.FAFactory;
import at.syssec.fa.NFA;
import at.syssec.fa.RSA;
import at.syssec.fa.impl.FATesting;

public class TestOperations {

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

	private void testEquality(NFA n1, NFA n2) {
		assertEquals(true, FATesting.equals(n1, n2));
	}

	//2 Punkte
	@Test
	public void test_complement() {
		testEquality(FATesting.complement(n1), n1.complement());
		testEquality(FATesting.complement(n2), n2.complement());
		testEquality(FATesting.complement(n3), n3.complement());
		testEquality(FATesting.complement(n4), n4.complement());
		testEquality(FATesting.complement(n5), n5.complement());
		testEquality(FATesting.complement(n6), n6.complement());
		testEquality(FATesting.complement(n7), n7.complement());
		testEquality(FATesting.complement(n8), n8.complement());
		testEquality(FATesting.complement(n9), n9.complement());
	}

	//1 Punkt
	@Test
	public void test_concat() {
		testEquality(FATesting.concat(n1, n2), n1.concat(n2));
		testEquality(FATesting.concat(n2, n3), n2.concat(n3));
		testEquality(FATesting.concat(n3, n4), n3.concat(n4));
		testEquality(FATesting.concat(n4, n5), n4.concat(n5));
		testEquality(FATesting.concat(n5, n6), n5.concat(n6));
		testEquality(FATesting.concat(n6, n7), n6.concat(n7));
		testEquality(FATesting.concat(n7, n8), n7.concat(n8));
		testEquality(FATesting.concat(n8, n9), n8.concat(n9));
		testEquality(FATesting.concat(n9, n1), n9.concat(n1));
	}

	//1 Punkt
	@Test
	public void test_intersection() {
		testEquality(FATesting.intersection(n1, n2), n1.intersection(n2));
		testEquality(FATesting.intersection(n2, n3), n2.intersection(n3));
		testEquality(FATesting.intersection(n3, n4), n3.intersection(n4));
		testEquality(FATesting.intersection(n4, n5), n4.intersection(n5));
		testEquality(FATesting.intersection(n5, n6), n5.intersection(n6));
		testEquality(FATesting.intersection(n6, n7), n6.intersection(n7));
		testEquality(FATesting.intersection(n7, n8), n7.intersection(n8));
		testEquality(FATesting.intersection(n8, n9), n8.intersection(n9));
		testEquality(FATesting.intersection(n9, n1), n9.intersection(n1));
	}
	
	//1 Punkt
	@Test
	public void test_plus() {
		testEquality(FATesting.plus(n1), n1.plus());
		testEquality(FATesting.plus(n2), n2.plus());
		testEquality(FATesting.plus(n3), n3.plus());
		testEquality(FATesting.plus(n4), n4.plus());
		testEquality(FATesting.plus(n5), n5.plus());
		testEquality(FATesting.plus(n6), n6.plus());
		testEquality(FATesting.plus(n7), n7.plus());
		testEquality(FATesting.plus(n8), n8.plus());
		testEquality(FATesting.plus(n9), n9.plus());
	}
	
	//2 Punkte
	@Test
	public void test_kleene() {
		testEquality(FATesting.kleeneStar(n1), n1.kleeneStar());
		testEquality(FATesting.kleeneStar(n2), n2.kleeneStar());
		testEquality(FATesting.kleeneStar(n3), n3.kleeneStar());
		testEquality(FATesting.kleeneStar(n4), n4.kleeneStar());
		testEquality(FATesting.kleeneStar(n5), n5.kleeneStar());
		testEquality(FATesting.kleeneStar(n6), n6.kleeneStar());
		testEquality(FATesting.kleeneStar(n7), n7.kleeneStar());
		testEquality(FATesting.kleeneStar(n8), n8.kleeneStar());
		testEquality(FATesting.kleeneStar(n9), n9.kleeneStar());
	}
	
	//1 Punkt
	@Test
	public void test_union() {
		testEquality(FATesting.union(n1, n2), n1.union(n2));
		testEquality(FATesting.union(n2, n3), n2.union(n3));
		testEquality(FATesting.union(n3, n4), n3.union(n4));
		testEquality(FATesting.union(n4, n5), n4.union(n5));
		testEquality(FATesting.union(n5, n6), n5.union(n6));
		testEquality(FATesting.union(n6, n7), n6.union(n7));
		testEquality(FATesting.union(n7, n8), n7.union(n8));
		testEquality(FATesting.union(n8, n9), n8.union(n9));
		testEquality(FATesting.union(n9, n1), n9.union(n1));
	}
	
	//1 Punkt
	@Test
	public void test_minus() {
		testEquality(FATesting.minus(n1, n2), n1.minus(n2));
		testEquality(FATesting.minus(n2, n3), n2.minus(n3));
		testEquality(FATesting.minus(n3, n4), n3.minus(n4));
		testEquality(FATesting.minus(n4, n5), n4.minus(n5));
		testEquality(FATesting.minus(n5, n6), n5.minus(n6));
		testEquality(FATesting.minus(n6, n7), n6.minus(n7));
		testEquality(FATesting.minus(n7, n8), n7.minus(n8));
		testEquality(FATesting.minus(n8, n9), n8.minus(n9));
		testEquality(FATesting.minus(n9, n1), n9.minus(n1));
	}
}