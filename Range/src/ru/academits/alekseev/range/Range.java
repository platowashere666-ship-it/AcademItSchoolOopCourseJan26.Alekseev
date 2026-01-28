package ru.academits.alekseev.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public double getFrom() {
        return from;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double point) {
        return point >= from && point <= to;
    }

    public Range getRangeIntersection(Range other) {
        double maxFrom = Math.max(this.from, other.from);
        double minTo = Math.min(this.to, other.to);

        if (maxFrom <= minTo) {
            return new Range(maxFrom, minTo);
        }

        return null;
    }

    public Range[] getRangeUnion(Range other) {
        double left = Math.min(this.from, other.from);
        double right = Math.max(this.to, other.to);

        if (other.from <= this.to && other.to >= this.from) {
            Range result = new Range(left, right);

            return new Range[]{result};
        }

        if (this.from > other.from) {
            return new Range[]{other, this};
        }

        return new Range[]{this, other};
    }

    public Range[] getRangeDifference(Range other) {
        if (other.to < this.from || other.from > this.to) {
            return new Range[]{this};
        }

        if (other.from <= this.from && other.to >= this.to) {
            return new Range[0];
        }

        Range[] result = new Range[2];
        int i = 0;

        if (this.from < other.from) {
            result[i++] = new Range(this.from, other.from);
        }

        if (this.to > other.to) {
            result[i++] = new Range(other.to, this.to);
        }

        Range[] trimmedResult = new Range[i];
        System.arraycopy(result, 0, trimmedResult, 0, i);

        return trimmedResult;
    }
}