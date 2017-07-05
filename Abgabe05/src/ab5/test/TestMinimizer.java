package ab5.test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import ab5.impl.Eberl.FAFactoryImpl;
import at.syssec.fa.FAFactory;
import at.syssec.fa.NFA;
import at.syssec.fa.RSA;
import at.syssec.fa.impl.FATesting;

public class TestMinimizer {

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

	FATesting refImpl = new FATesting(new FAFactoryRefImpl());

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

	private void testMinimization(NFA n) {
		// Verwendet die Implementieurng von n zur Erzeugung des minimalen
		// Automaten
		RSA r1 = n.toRSA().minimize();

		// Verwendet die Referenzimplementieurng zur Erzeugung des minimalen
		// Automaten. Die Implementierung von n wird hier nicht verwendet.
		RSA r2 = refImpl.minimize(refImpl.toRSA(n));

		// Sind die minimierten Automaten äquivalent?
		assertEquals(true, refImpl.equals(r1, r2));

		// Haben die minimierten Automaten die gleiche Anzahl an Zuständen
		assertEquals(r2.getNumStates(), r1.getNumStates());
	}

	// 0 Punkte - Test muss aber positiv durchlaufen werden. Nur relevant, wenn
	// man nicht die Referenzimplementierung für die Funktionalität der
	// vergangengen Aufgaben verwendet hat.
	@Test
	public void testOperations() {
		// Testen, ob das Komplement korrekt erzeugt wird
		assertEquals(true, refImpl.equals(n1.complement(), refImpl.complement(n1)));
		assertEquals(true, refImpl.equals(n2.complement(), refImpl.complement(n2)));
		assertEquals(true, refImpl.equals(n3.complement(), refImpl.complement(n3)));
		assertEquals(true, refImpl.equals(n4.complement(), refImpl.complement(n4)));
		assertEquals(true, refImpl.equals(n5.complement(), refImpl.complement(n5)));
		assertEquals(true, refImpl.equals(n6.complement(), refImpl.complement(n6)));
		assertEquals(true, refImpl.equals(n7.complement(), refImpl.complement(n7)));
		assertEquals(true, refImpl.equals(n8.complement(), refImpl.complement(n8)));
		assertEquals(true, refImpl.equals(n9.complement(), refImpl.complement(n9)));

		// Testen, ob die Konketenation korrekt erzeugt wird
		assertEquals(true, refImpl.equals(n1.concat(n2), refImpl.concat(n1, n2)));
		assertEquals(true, refImpl.equals(n2.concat(n3), refImpl.concat(n2, n3)));
		assertEquals(true, refImpl.equals(n3.concat(n4), refImpl.concat(n3, n4)));
		assertEquals(true, refImpl.equals(n4.concat(n5), refImpl.concat(n4, n5)));
		assertEquals(true, refImpl.equals(n5.concat(n6), refImpl.concat(n5, n6)));
		assertEquals(true, refImpl.equals(n6.concat(n7), refImpl.concat(n6, n7)));
		assertEquals(true, refImpl.equals(n7.concat(n8), refImpl.concat(n7, n8)));
		assertEquals(true, refImpl.equals(n8.concat(n9), refImpl.concat(n8, n9)));
		assertEquals(true, refImpl.equals(n9.concat(n1), refImpl.concat(n9, n1)));

		// Testen, ob der Durchschnitt korrekt erzeugt wird
		assertEquals(true, refImpl.equals(n1.intersection(n2), refImpl.intersection(n1, n2)));
		assertEquals(true, refImpl.equals(n2.intersection(n3), refImpl.intersection(n2, n3)));
		assertEquals(true, refImpl.equals(n3.intersection(n4), refImpl.intersection(n3, n4)));
		assertEquals(true, refImpl.equals(n4.intersection(n5), refImpl.intersection(n4, n5)));
		assertEquals(true, refImpl.equals(n5.intersection(n6), refImpl.intersection(n5, n6)));
		assertEquals(true, refImpl.equals(n6.intersection(n7), refImpl.intersection(n6, n7)));
		assertEquals(true, refImpl.equals(n7.intersection(n8), refImpl.intersection(n7, n8)));
		assertEquals(true, refImpl.equals(n8.intersection(n9), refImpl.intersection(n8, n9)));
		assertEquals(true, refImpl.equals(n9.intersection(n1), refImpl.intersection(n9, n1)));

		// Testen, ob der +-Operator korrekt ist
		assertEquals(true, refImpl.equals(n1.plus(), refImpl.plus(n1)));
		assertEquals(true, refImpl.equals(n2.plus(), refImpl.plus(n2)));
		assertEquals(true, refImpl.equals(n3.plus(), refImpl.plus(n3)));
		assertEquals(true, refImpl.equals(n4.plus(), refImpl.plus(n4)));
		assertEquals(true, refImpl.equals(n5.plus(), refImpl.plus(n5)));
		assertEquals(true, refImpl.equals(n6.plus(), refImpl.plus(n6)));
		assertEquals(true, refImpl.equals(n7.plus(), refImpl.plus(n7)));
		assertEquals(true, refImpl.equals(n8.plus(), refImpl.plus(n8)));
		assertEquals(true, refImpl.equals(n9.plus(), refImpl.plus(n9)));

		// Testen, ob der *-Operator korrekt ist
		assertEquals(true, refImpl.equals(n1.kleeneStar(), refImpl.kleeneStar(n1)));
		assertEquals(true, refImpl.equals(n2.kleeneStar(), refImpl.kleeneStar(n2)));
		assertEquals(true, refImpl.equals(n3.kleeneStar(), refImpl.kleeneStar(n3)));
		assertEquals(true, refImpl.equals(n4.kleeneStar(), refImpl.kleeneStar(n4)));
		assertEquals(true, refImpl.equals(n5.kleeneStar(), refImpl.kleeneStar(n5)));
		assertEquals(true, refImpl.equals(n6.kleeneStar(), refImpl.kleeneStar(n6)));
		assertEquals(true, refImpl.equals(n7.kleeneStar(), refImpl.kleeneStar(n7)));
		assertEquals(true, refImpl.equals(n8.kleeneStar(), refImpl.kleeneStar(n8)));
		assertEquals(true, refImpl.equals(n9.kleeneStar(), refImpl.kleeneStar(n9)));

		// Testen, ob die Vereinigung korrekt erzeugt wird
		assertEquals(true, refImpl.equals(n1.union(n2), refImpl.union(n1, n2)));
		assertEquals(true, refImpl.equals(n2.union(n3), refImpl.union(n2, n3)));
		assertEquals(true, refImpl.equals(n3.union(n4), refImpl.union(n3, n4)));
		assertEquals(true, refImpl.equals(n4.union(n5), refImpl.union(n4, n5)));
		assertEquals(true, refImpl.equals(n5.union(n6), refImpl.union(n5, n6)));
		assertEquals(true, refImpl.equals(n6.union(n7), refImpl.union(n6, n7)));
		assertEquals(true, refImpl.equals(n7.union(n8), refImpl.union(n7, n8)));
		assertEquals(true, refImpl.equals(n8.union(n9), refImpl.union(n8, n9)));
		assertEquals(true, refImpl.equals(n9.union(n1), refImpl.union(n9, n1)));

		// Testen, ob die Differenz korrekt erzeugt wird
		assertEquals(true, refImpl.equals(n1.minus(n2), refImpl.minus(n1, n2)));
		assertEquals(true, refImpl.equals(n2.minus(n3), refImpl.minus(n2, n3)));
		assertEquals(true, refImpl.equals(n3.minus(n4), refImpl.minus(n3, n4)));
		assertEquals(true, refImpl.equals(n4.minus(n5), refImpl.minus(n4, n5)));
		assertEquals(true, refImpl.equals(n5.minus(n6), refImpl.minus(n5, n6)));
		assertEquals(true, refImpl.equals(n6.minus(n7), refImpl.minus(n6, n7)));
		assertEquals(true, refImpl.equals(n7.minus(n8), refImpl.minus(n7, n8)));
		assertEquals(true, refImpl.equals(n8.minus(n9), refImpl.minus(n8, n9)));
		assertEquals(true, refImpl.equals(n9.minus(n1), refImpl.minus(n9, n1)));
	}

	// 1 Punkt
	@Test
	public void test1() {

		testMinimization(n1.complement());
		testMinimization(n2.complement());
		testMinimization(n3.complement());
		testMinimization(n4.complement());
		testMinimization(n5.complement());
		testMinimization(n6.complement());
		testMinimization(n7.complement());
		testMinimization(n8.complement());
		testMinimization(n9.complement());
	}

	// 1 Punkt
	@Test
	public void test2() {
		testMinimization(n1.concat(n2));
		testMinimization(n2.concat(n3));
		testMinimization(n3.concat(n4));
		testMinimization(n4.concat(n5));
		testMinimization(n5.concat(n6));
		testMinimization(n6.concat(n7));
		testMinimization(n7.concat(n8));
		testMinimization(n8.concat(n9));
		testMinimization(n9.concat(n1));
	}

	// 1 Punkt
	@Test
	public void test3() {
		testMinimization(n1.intersection(n2));
		testMinimization(n2.intersection(n3));
		testMinimization(n3.intersection(n4));
		testMinimization(n4.intersection(n5));
		testMinimization(n5.intersection(n6));
		testMinimization(n6.intersection(n7));
		testMinimization(n7.intersection(n8));
		testMinimization(n8.intersection(n9));
		testMinimization(n9.intersection(n1));
	}

	// 1 Punkt
	@Test
	public void test4() {
		testMinimization(n1.plus());
		testMinimization(n2.plus());
		testMinimization(n3.plus());
		testMinimization(n4.plus());
		testMinimization(n5.plus());
		testMinimization(n6.plus());
		testMinimization(n7.plus());
		testMinimization(n8.plus());
		testMinimization(n9.plus());
	}

	// 1 Punkt
	@Test
	public void test5() {
		testMinimization(n1.kleeneStar());
		testMinimization(n2.kleeneStar());
		testMinimization(n3.kleeneStar());
		testMinimization(n4.kleeneStar());
		testMinimization(n5.kleeneStar());
		testMinimization(n6.kleeneStar());
		testMinimization(n7.kleeneStar());
		testMinimization(n8.kleeneStar());
		testMinimization(n9.kleeneStar());
	}

	// 1 Punkt
	@Test
	public void test6() {
		testMinimization(n1.union(n2));
		testMinimization(n2.union(n3));
		testMinimization(n3.union(n4));
		testMinimization(n4.union(n5));
		testMinimization(n5.union(n6));
		testMinimization(n6.union(n7));
		testMinimization(n7.union(n8));
		testMinimization(n8.union(n9));
		testMinimization(n9.union(n1));
	}

	// 1 Punkt
	@Test
	public void test7() {
		testMinimization(n1.minus(n2));
		testMinimization(n2.minus(n3));
		testMinimization(n3.minus(n4));
		testMinimization(n4.minus(n5));
		testMinimization(n5.minus(n6));
		testMinimization(n6.minus(n7));
		testMinimization(n7.minus(n8));
		testMinimization(n8.minus(n9));
		testMinimization(n9.minus(n1));
	}

	// 1 Punkt
	@Test
	public void test8() {
		testMinimization(n1);
		testMinimization(n2);
		testMinimization(n3);
		testMinimization(n4);
		testMinimization(n5);
		testMinimization(n6);
		testMinimization(n7);
		testMinimization(n8);
		testMinimization(n9);

	}

	// 1 Punkt
	/*
	 * Je nach zufällig gewähltem NFA kann es passieren, dass der dazugehörige
	 * RSA (Stichwort Potenzautomat) sehr groß wird. Für den Fall, dass es
	 * Exceptions gibt, die durch die Referenzimplementierunt toRSA
	 * hervorgerufen werden, wird dies nicht negativ bewertet. Diese Exceptions
	 * treten bei der Referenzimplementierung toRSA aber nur selten auf. Treten
	 * diese Exceptions häufiger auf, ist entweder der maximal verwendete
	 * Speicher von Java zu erhöhen, oder die minimize-Methode hat ein Problem :)
	 */
	@Test
	public void test9() {
		int[] testSizes = new int[] { 5, 10, 20, 30, 40, 50 };
		final int TESTCOUNT = 100;

		for (int testSize : testSizes) {
			for (int i = 0; i < TESTCOUNT; i++) {
				testMinimization(generateRandomFA(testSize));
			}
		}
	}

	private NFA generateRandomFA(int states) {
		Random r = new Random(System.currentTimeMillis());

		// Generiere eine zufällige Anzahl an Endzuständen.
		Set<Integer> acceptingStates = new HashSet<Integer>();
		for (int i = 0; i < r.nextInt(states); i++)
			acceptingStates.add(r.nextInt(states));

		NFA n = factory.generateNFA(states, chars, acceptingStates, 0);

		// Generiere eine zufällige Anzahl an Transitionen. Maximal states^2/4
		// viele
		int actState = 0;
		for (int i = 0; i < r.nextInt(states * states / 4); i++) {
			int nextState = r.nextInt(states);

			n.setTransition(actState, (char) chars.toArray()[r.nextInt(chars.size())], nextState);
			actState = nextState;
		}

		// Generiere eine zufällige Anzahl an Transitionen. Maximal states/4
		// viele
		for (int i = 0; i < r.nextInt(states / 4); i++)
			n.setEpsilonTransition(r.nextInt(states), r.nextInt(states));

		return n;
	}
}