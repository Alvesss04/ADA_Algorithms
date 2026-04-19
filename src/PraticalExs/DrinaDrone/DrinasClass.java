package PraticalExs.DrinaDrone;

import java.util.Arrays;

public class DrinasClass {

    Offer[] res;    // Array of all offers (jobs) added to the system
    int[] profits;  // profits[i] = max profit achievable considering the first i offers (after sorting)
    int count = 0;  // Tracks how many offers have been added so far

    // Constructor: initializes storage for 'length' offers
    // profits array has length+1 to handle the base case profits[0] = 0
    public DrinasClass(int length) {
        res = new Offer[length];
        profits = new int[length + 1];
    }

    // Adds a new offer (job) to the system
    // st = start time, dt = duration, p = profit (price)
    // endTime = when the job finishes (start + duration)
    public void add(int st, int dt, int p) {
        int endTime = st + dt;
        res[count++] = new Offer(st, endTime, p);
    }

    // Binary search: finds the index of the latest offer (job) that finishes
    // before or exactly when offer i starts — i.e. the last compatible job
    // "Compatible" means it doesn't overlap with job i
    // Returns -1 if no such job exists (meaning job i has no compatible predecessor)
    private int latestCompatible(int i) {
        int high = i - 1;
        for (int lower = 0; lower <= high;) {
            int mid = (lower + high) / 2;
            if (res[mid].end() <= res[i].start())
                lower = mid + 1;  // mid finishes before i starts → search right
            else
                high = mid - 1;   // mid overlaps with i → search left
        }
        return high; // index of the latest compatible job (-1 if none)
    }

    // Dynamic Programming: fills the profits[] array
    // After sorting offers by end time, for each offer i we decide:
    //   Option A: skip offer i → profit stays profits[i-1]
    //   Option B: take offer i → add its price to the best profit up to its latest compatible job
    // We take the maximum of both options
    private void setProfits() {
        Arrays.sort(res, 0, count); // sort offers by end time (Offer must implement Comparable)
        profits[0] = 0;             // base case: 0 offers selected = 0 profit
        for (int i = 1; i <= count; i++) {
            int p = latestCompatible(i - 1); // index of last compatible job for offer i-1
            // profits[p + 1] = best profit up to and including the latest compatible job
            // (+1 because profits is 1-indexed: profits[j] = best profit for first j offers)
            profits[i] = Math.max(profits[i - 1], res[i - 1].price() + profits[p + 1]);
        }
    }

    // Entry point: runs the DP and returns the maximum profit achievable
    // by selecting a set of non-overlapping offers
    public int solveProblem() {
        setProfits();
        return profits[count]; // profits[count] = best profit using all count offers
    }
}