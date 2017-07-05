package ab2.impl.Eberl;

import ab2.Movement;

/**
 * Tape class for Turing machine.
 */
class Tape {

    static final char SPACE = '#';

    private final StringBuilder tapeContent;
    private int headPosition;

    Tape(char[] initialContent) {

        tapeContent = new StringBuilder(new String(initialContent));
        tapeContent.append(SPACE);
        headPosition = initialContent.length;
    }

    char[] getLeftOfHead() {

        if (isHeadUnderflow())
            return null;
        if (headPosition == 0)
            return new char[0];

        char[] result = new char[headPosition];
        tapeContent.getChars(0, headPosition, result, 0);

        return result;
    }

    char[] getRightOfHead() {

        StringBuilder resultBuilder = new StringBuilder();
        for (int i = headPosition; i < tapeContent.length() - 1 && tapeContent.charAt(i) != SPACE; i++) {
            resultBuilder.append(tapeContent.charAt(i));
        }

        return resultBuilder.toString().toCharArray();
    }

    char getBelowHead() {

        return tapeContent.charAt(headPosition);
    }

    void moveHead(Movement movement) {
        switch (movement) {
            case Left:
                headPosition -= 1;
                break;
            case Right:
                headPosition += 1;
                if (headPosition == tapeContent.length())
                    tapeContent.append(SPACE);
                break;
            case Stay:
                break;
        }
    }

    boolean isHeadUnderflow() {
        return headPosition < 0;
    }

    void write(char symbolWrite) {
        tapeContent.setCharAt(headPosition, symbolWrite);
    }
}
