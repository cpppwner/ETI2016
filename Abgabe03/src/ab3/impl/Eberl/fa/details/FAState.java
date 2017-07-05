package ab3.impl.Eberl.fa.details;

/**
 * Class representing one state in our Finite Automatons.
 *
 * <p>
 *     Since the transitions vary based on the Automaton, this class is intended to be the base.
 * </p>
 */
public abstract class FAState {

    /**
     * Unique identifier of a State.
     */
    private final int id;

    /**
     * Boolean flag indicating whether this state is an accepting (final) state or not.
     */
    private final boolean isAcceptingState;

    /**
     * Initializes a State instance.
     * @param id The unique identifier of this state.
     * @param isAcceptingState Flag indicating if this is a final state ({@code true}) or not ({@code false}).
     */
    public FAState(int id, boolean isAcceptingState) {
        this.id = id;
        this.isAcceptingState = isAcceptingState;
    }

    /**
     * Get unique identifier of this atate.
     * @return Identifier of this state.
     */
    public int getId() {

        return id;
    }

    /**
     * Get a boolean flag indicating whether this state is an accepting (final) state or not.
     * @return {@code true} if this state is an accepting state, {@code false} otherwise.
     */
    public boolean isAcceptingState() {

        return isAcceptingState;
    }

    @Override
    public boolean equals(Object other) {

        return other instanceof FAState && id == ((FAState) other).id;

    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
