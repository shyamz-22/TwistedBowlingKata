package com.ennovate.bowling;

import java.util.ArrayList;

class Frame {

    private ArrayList<Integer> rolls = new ArrayList<>();
    private int score;
    private Frame nextFrame;

    Frame roll(int pins) {
        rolls.add(pins);
        return this;
    }

    int score() {
        score = totalPinsKnocked();
        if (isStrike()) {
            score += strikeBonus();
        } else if (isSpare()) {
            score += spareBonus();
        }

        return score;
    }

    private int totalPinsKnocked() {
        return rolls.stream().mapToInt(Integer::intValue).sum();
    }

    private boolean isSpare() {
        return !isStrike() && (score == 10);
    }

    private int spareBonus() {
        return firstRoll(nextFrame);
    }

    private boolean isStrike() {
        return (rolls.get(0) == 10) && !isTenthFrame();
    }

    private int strikeBonus() {

        if (consecutiveStrikes()) {
            return 10 + firstRoll(nextFrame.getNextFrame());
        }
        else if (isNinthFrame())
        {
            return nextFrame.getRolls().stream()
                    .limit(2)
                    .mapToInt(Integer::intValue)
                    .sum();
        } else {
            return nextFrame.totalPinsKnocked();
        }
    }

    private boolean consecutiveStrikes() {
        return isStrike() && nextFrame.isStrike() && !isNinthFrame();
    }

    private int firstRoll(Frame frame) {
        return frame.getRolls().get(0);
    }

    private boolean isNinthFrame() {
        return nextFrame.getNextFrame() == null;
    }

    private boolean isTenthFrame() {
        return nextFrame == null;
    }


    void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }

    private Frame getNextFrame() {
        return nextFrame;
    }

    private ArrayList<Integer> getRolls() {
        return rolls;
    }
}
