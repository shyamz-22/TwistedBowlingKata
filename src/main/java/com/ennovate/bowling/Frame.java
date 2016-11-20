package com.ennovate.bowling;

import java.util.ArrayList;

class Frame {

    private ArrayList<Integer> rolls = new ArrayList<>();
    private int score;
    private Frame nextFrame;
    private int frameIndex;

    Frame roll(int pins) {
        rolls.add(pins);
        return this;
    }

    int score() {
        return new FrameScoreCalculator(this).calculate();
    }

    private int totalPinsKnocked() {
        return rolls.stream().mapToInt(Integer::intValue).sum();
    }

    private boolean isStrike() {
        return (rolls.get(0) == 10) && !(frameIndex == 10);
    }

    private boolean consecutiveStrikes() {
        return isStrike() && nextFrame.isStrike() && !(frameIndex == 9);
    }

    private int pinsKnockedInFirstRoll(Frame frame) {
        return frame.getRolls().get(0);
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

    public void setFrameIndex(int frameIndex) {
        this.frameIndex = frameIndex;
    }

    protected class FrameScoreCalculator {

        private Frame frame;

        public FrameScoreCalculator(Frame frame) {
            this.frame = frame;
        }

        public int calculate() {
            score = totalPinsKnocked();
            if (isStrike()) {
                score += strikeBonus();
            } else if (isSpare()) {
                score += spareBonus();
            }

            return score;
        }

        private int strikeBonus() {

            if (consecutiveStrikes()) {
                return 10 + pinsKnockedInFirstRoll(nextFrame.getNextFrame());
            }
            else if (frameIndex == 9)
            {
                return nextFrame.getRolls().stream()
                        .limit(2)
                        .mapToInt(Integer::intValue)
                        .sum();
            } else {
                return nextFrame.totalPinsKnocked();
            }
        }

        private boolean isSpare() {
            return !isStrike() && (score == 10);
        }

        private int spareBonus() {
            return pinsKnockedInFirstRoll(nextFrame);
        }
    }
}
