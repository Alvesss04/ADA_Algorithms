package PraticalExs.DrinaDrone;

import java.util.Arrays;

public class DrinasClass {
    Offer[] res;
    int[] profits;
    int count = 0;

    public DrinasClass(int length) {
        res = new Offer[length];
        profits = new int[length + 1];
    }

    public void add(int st, int dt, int p) {
        int endTime = st + dt;
        res[count++] = new Offer(st, endTime, p);
    }

    private int latestCompatible(int i) {
        int high = i - 1;
        for (int lower = 0; lower <= high;) {
            int mid = (lower + high) / 2;
            if (res[mid].end() <= res[i].start())
                lower = mid + 1;
            else
                high = mid - 1;
        }
        return high;
    }

    private void setProfits() {
        Arrays.sort(res, 0, count);
        profits[0] = 0;
        for (int i = 1; i <= count; i++) {
            int p = latestCompatible(i - 1);
            profits[i] = Math.max(profits[i - 1], res[i - 1].price() + profits[p + 1]);
        }
    }

    public int solveProblem() {
        setProfits();
        return profits[count];
    }
}