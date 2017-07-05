package ab2.impl.Eberl;

import java.util.HashSet;
import java.util.Set;

import ab2.Movement;
import ab2.TM;
import ab2.TMConfig;

public class TMImpl implements TM {

	private static final int HALT_STATE = 0;

	private Set<Character> symbols;
	private Transitions transitions;
	private int currentState;
	private Tape tape;

	public TMImpl() {
		reset();
	}

	@Override
	public void reset() {

		// symbols
		symbols = new HashSet<>();

		// reset transitions and add the halt state
		transitions = new Transitions();
		transitions.addState(HALT_STATE);

		// current state
		currentState = HALT_STATE;

		// tape content
		tape = new Tape(new char[0]);
	}

	@Override
	public void setSymbols(Set<Character> symbols) throws IllegalArgumentException {
		if (!symbols.contains(Tape.SPACE))
			throw new IllegalArgumentException();

		this.symbols.addAll(symbols);
	}

	@Override
	public Set<Character> getSymbols() {

		return symbols;
	}

	@Override
	public void addTransition(int fromState, char symbolRead, int toState, char symbolWrite, Movement movement)
			throws IllegalArgumentException {

		// check if symbol read is in alphabet
		if (!symbols.contains(symbolRead))
			throw new IllegalArgumentException("symbolRead not in alphabet");

		// check if symbol write is in alphabet
		if (!symbols.contains(symbolWrite))
			throw new IllegalArgumentException("symbolWrite not in alphabet");

		// transition from halt state -> not allowed
		if (fromState == HALT_STATE)
			throw new IllegalArgumentException("transition from halt state not allowed");

		if (transitions.hasTransition(fromState, symbolRead))
			throw new IllegalArgumentException("ambiguous transition");

		// now build up the transition
		Transition transition = new Transition.Builder()
				.setFromState(fromState)
				.setToState(toState)
				.setSymbolRead(symbolRead)
				.setSymbolWrite(symbolWrite)
				.setMovement(movement)
				.build();

		transitions.addTransition(transition);
	}

	@Override
	public Set<Integer> getStates() {

		return transitions.getStates();
	}

	@Override
	public void setInitialState(int state) {

		currentState = state;
	}

	@Override
	public void setInitialTapeContent(char[] content) {

		tape = new Tape(content);
	}

	@Override
	public void doNextStep() throws IllegalStateException {

		if (isHalt())
			throw new IllegalStateException("halt state reached");
		if (tape.isHeadUnderflow())
			throw new IllegalStateException("head underflow");
		if (transitions == null || !transitions.hasTransition(currentState, tape.getBelowHead()))
			throw new IllegalStateException("illegal transition");

		Transition transition = transitions.getTransition(currentState, tape.getBelowHead());
		tape.write(transition.getSymbolWrite());
		if (transition.getMovement() != null)
			tape.moveHead(transition.getMovement());
		currentState = transition.getToState();
	}

	@Override
	public boolean isHalt() {

		return currentState == HALT_STATE;
	}

	@Override
	public boolean isCrashed() {

		return (tape.isHeadUnderflow() || !getStates().contains(currentState));
	}

	@Override
	public TMConfig getTMConfig() {

		if (isCrashed())
			return null;

		return new TMConfig(tape.getLeftOfHead(), tape.getBelowHead(), tape.getRightOfHead());
	}
}