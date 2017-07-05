package ab6.impl.Eberl;

import java.util.*;
import java.util.stream.Collectors;

import ab6.FAFactoryRefImpl;
import ab6.SearchTools;
import at.syssec.fa.DFA;
import at.syssec.fa.NFA;
import at.syssec.fa.RSA;

import static java.util.stream.IntStream.concat;

public class SearchToolsImpl implements SearchTools {


	@Override
	public List<Integer> findPattern(String text, String pattern) {

        // if pattern is longer than the text, there cannot be any match
        if (pattern.length() > text.length()) {
            return Collections.emptyList();
        }

        // number of states is pattern length + accepting state
        int numStates = pattern.length() + 1;
        // alphabet for automaton is each distinct character from pattern + text
        // could generate a full blown alphabet, but don't want to waste space
        Set<Character> alphabet = concat(text.chars(), pattern.chars())
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());

		// construct DFA
        DFA dfa = new FAFactoryRefImpl().generateDFA(numStates, alphabet, Collections.singleton(numStates - 1), 0);


        // setup transitions for remaining states
        for (int i = 0; i < pattern.length(); i++) {
            for (char c : alphabet) {
                if (c == pattern.charAt(i)) {
                    // match case - transition to next state
                    dfa.setTransition(i, c, i + 1);
                } else {
                    // mismatch case - transition back
                    dfa.setTransition(i, c, 0);
                }
            }
        }

        // search string - the brute force way of string searching
        List<Integer> result = new LinkedList<>();
        for (int i = 0; i < text.length(); i++) {
            if (i + pattern.length() > text.length()) {
                // offset + pattern length is bigger than string
                // cannot find pattern any more, just abort
                break;
            }

            for (int j = 0; j < pattern.length(); j++) {
                dfa.doStep(text.charAt(i + j));
            }
            if (dfa.isAcceptingState()) {
                // found pattern - add index to result
                result.add(i);
            }
            dfa.reset(); // reset state to initial
        }

        return result;
	}

	@Override
	public Boolean checkPattern(String text, String pattern) {

        // number of states needs to be counted this time
        // all characters except Kleene Star (*) are counted as one, Kleene Star is not counted at all
        int numStates = (int)pattern.chars()
                .filter(c -> c != '*')
                .count() + 1;

        // alphabet for automaton is each distinct character from pattern + text
        // but filter out the "any character" (.) and the Kleene Star (*) first
        // could generate a full blown alphabet, but don't want to waste space
        Set<Character> alphabet = concat(text.chars(), pattern.chars().filter(c -> c != '*' && c != '.'))
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());

        // construct NFA
        NFA nfa = new FAFactoryRefImpl().generateNFA(numStates, alphabet, Collections.singleton(numStates - 1), 0);

        // setup transitions
        for (int i = 0, j = 0; i < pattern.length(); i++) {
            final int state = j;
            if (pattern.charAt(i) == '.') {
                // if pattern is dot, setup transition to next state for all elements from alphabet
                alphabet.forEach(c -> nfa.setTransition(state, c, state + 1));
                j += 1;
            }
            else if (pattern.charAt(i) == '*') {
                // otherwise if it's the Kleene Star, check the previous character
                // and set up transition for previous character(s) to same state
                if (pattern.charAt(i - 1) == '.')
                    alphabet.forEach(c -> nfa.setTransition(state, c, state));
                else
                    nfa.setTransition(state, pattern.charAt(i - 1), state);
            } else {
                // it's a character without special meaning -> advance to next state
                nfa.setTransition(state, pattern.charAt(i), state + 1);
                j += 1;
            }
        }

        // convert NFA to RSA
        RSA rsa = nfa.toRSA();

        // and check the text
        text.chars().forEach(c -> rsa.doStep((char)c));

        return rsa.isAcceptingState();
	}
}
