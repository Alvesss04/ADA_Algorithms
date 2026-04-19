package PraticalExs.DrinaDrone;
public record Offer(int start, int end, int price) implements Comparable<Offer> {

    @Override
    public int end() {
        return end;
    }

    @Override
    public int price() {
        return price;
    }

    @Override
    public int compareTo(Offer other) {
        return Integer.compare(this.end(), other.end());
    }
}