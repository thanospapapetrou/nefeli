package io.github.thanospapapetrou.nefeli.oai.pmh;

public class RetryAfterException extends InterruptedException {
    private final int seconds;

    public RetryAfterException(final int seconds) {
        super();
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }
}
