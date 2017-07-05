package ab2.impl.Eberl;

import ab2.Movement;

/**
 * Simple transition class for the Turing machine.
 */
class Transition {

    private final int fromState;
    private final int toState;
    private final char symbolRead;
    private final char symbolWrite;
    private final Movement movement;

    private Transition(Builder builder) {
        this.fromState = builder.fromState;
        this.toState = builder.toState;
        this.symbolRead = builder.symbolRead;
        this.symbolWrite = builder.symbolWrite;
        this.movement = builder.movement;
    }

    int getFromState() {
        return fromState;
    }

    int getToState() {
        return toState;
    }

    char getSymbolRead() {
        return symbolRead;
    }

    char getSymbolWrite() {
        return symbolWrite;
    }

    Movement getMovement() {
        return movement;
    }

    static class Builder {

        private int fromState;
        private int toState;
        private char symbolRead;
        private char symbolWrite;
        private Movement movement;

        Builder setFromState(int fromState) {
            this.fromState = fromState;
            return this;
        }

        Builder setToState(int toState) {
            this.toState = toState;
            return this;
        }

        Builder setSymbolRead(char symbolRead) {
            this.symbolRead = symbolRead;
            return this;
        }

        Builder setSymbolWrite(char symbolWrite) {
            this.symbolWrite = symbolWrite;
            return this;
        }

        Builder setMovement(Movement movement) {
            this.movement = movement;
            return this;
        }

        Transition build() {
            return new Transition(this);
        }
    }
}
