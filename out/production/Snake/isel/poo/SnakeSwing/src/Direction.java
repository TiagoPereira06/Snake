public  enum Direction {

    LEFT, RIGHT, UP, DOWN;

    public boolean compatibleWith(Direction newDirection) {
        if (this.equals(LEFT) || this.equals(RIGHT)) {
            return UP.equals(newDirection) || DOWN.equals(newDirection);
        } else {
            return LEFT.equals(newDirection) || RIGHT.equals(newDirection);
        }
    }
}

