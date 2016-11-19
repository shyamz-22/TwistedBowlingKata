package com.ennovate.bowling;

import java.util.LinkedList;

class Game {

    private LinkedList<Frame> frames = new LinkedList<>();

    int score() {
        return frames.stream()
                .map(Frame::score)
                .mapToInt(Integer::intValue)
                .sum();

    }

    void add(Frame currentFrame) {
        addToPrevious(currentFrame);
        frames.add(currentFrame);
    }

    private void addToPrevious(Frame frame) {
        if (!frames.isEmpty()) {
            frames.getLast().setNextFrame(frame);
        }
    }
}
