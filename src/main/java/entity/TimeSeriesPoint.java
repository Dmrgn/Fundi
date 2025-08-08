package entity;

public class TimeSeriesPoint {
    private final long epochMillis;
    private final double close;

    public TimeSeriesPoint(long epochMillis, double close) {
        this.epochMillis = epochMillis;
        this.close = close;
    }

    public long getEpochMillis() {
        return epochMillis;
    }

    public double getClose() {
        return close;
    }
}
