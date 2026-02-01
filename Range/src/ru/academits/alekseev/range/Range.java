package ru.academits.alekseev.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double point) {
        return point >= from && point <= to;
    }

    public Range getIntersection(Range range) {
        double maxFrom = Math.max(from, range.from);
        double minTo = Math.min(to, range.to);

        if (maxFrom < minTo) {
            return new Range(maxFrom, minTo);
        }

        return null;
    }

    public Range[] getUnion(Range range) {
        if (range.from <= to && range.to >= from) {
            return new Range[]{new Range(Math.min(from, range.from), Math.max(to, range.to))};
        }

        if (from < range.from) {
            return new Range[]{new Range(from, to), new Range(range.from, range.to)};
        }

        return new Range[]{new Range(range.from, range.to), new Range(from, to)};
    }

    public Range[] getDifference(Range range) {
        if (range.to < from || range.from > to) {
            return new Range[]{new Range(from, to)};
        }

        if (range.from <= from && range.to >= to) {
            return new Range[0];
        }

        if (from < range.from && to > range.to) {
            return new Range[]{new Range(from, range.from), new Range(range.to, to)};
        }

        if (from < range.from) {
            return new Range[]{new Range(from, range.from)};
        }

        return new Range[]{new Range(range.to, to)};
    }

    @Override
    public String toString() {
        return String.format("[%.2f, %.2f]", from, to);
    }
}