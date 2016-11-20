package com.ennovate.bowling;

import com.ennovate.bowling.score.FrameScoreCalculator;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Frame {

    @Getter
    private ArrayList<Integer> rolls;

    @Getter
    @Setter
    private Frame nextFrame;

    @Getter
    @Setter
    private int frameIndex;

    Frame() {
        this.rolls = new ArrayList<>();
    }

    Frame roll(int pins) {
        rolls.add(pins);
        return this;
    }

    int score() {
        return new FrameScoreCalculator(this).score();
    }

}
