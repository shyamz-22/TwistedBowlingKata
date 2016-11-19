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
        score = scoreWithoutBonus();
        if (isStrike()) {
            score += strikeBonus();
        } else if (isSpare()) {
            score += spareBonus();
        }

        return score;
    }

    private int scoreWithoutBonus() {
        return rolls.stream().mapToInt(Integer::intValue).sum();
    }

    private boolean consecutiveStrikes() {
        return isStrike() && nextFrame.isStrike() && !isNinthFrame();
    }

    private boolean isNinthFrame() {
        return nextFrame.getNextFrame() == null;
    }

    private boolean isTenthFrame() {
        return nextFrame == null;
    }

    private boolean isStrike() {
        return (rolls.get(0) == 10) && !isTenthFrame();
    }

    private Integer spareBonus() {
        return firstRoll(nextFrame);
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
            return nextFrame.scoreWithoutBonus();
        }
    }

    private boolean isSpare() {
        return !isStrike() && (score == 10);
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

    private int firstRoll(Frame frame) {
        return frame.getRolls().get(0);
    }
}
