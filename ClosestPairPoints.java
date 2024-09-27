/*
Author: Kaustik Namdev Ranaware
GWID  : G45811061
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class ClosestPairPoints {

    // Function to calculate the Euclidean distance between two points
    public static double euclideanDistance(Coordinate p1, Coordinate p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    // Brute force method for finding the closest pair of points
    public static double bruteForce(ArrayList<Coordinate> points, int n) {
        double minDist = Double.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                double dist = euclideanDistance(points.get(i), points.get(j));
                if (dist < minDist) {
                    minDist = dist;
                }
            }
        }
        return minDist;
    }

    public static double closestInBox(ArrayList<Coordinate> strip, double minDist) {
        double minValue = minDist;

        // Sorting strip according to y-coordinate for efficient comparisons
        Collections.sort(strip, Comparator.comparingInt(p -> p.y));

        for (int i = 0; i < strip.size(); ++i) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < minValue; ++j) {
                double dist = euclideanDistance(strip.get(i), strip.get(j));
                if (dist < minValue) {
                    minValue = dist;
                }
            }
        }
        return minValue;
    }

    // Helper function to partition points for median finding
    public static int partition(ArrayList<Coordinate> points, int low, int high, int pivotIndex) {
        Coordinate pivot = points.get(pivotIndex);
        Collections.swap(points, pivotIndex, high);
        int storeIndex = low;
        for (int i = low; i < high; i++) {
            if (points.get(i).x < pivot.x) {
                Collections.swap(points, storeIndex, i);
                storeIndex++;
            }
        }
        Collections.swap(points, storeIndex, high);
        return storeIndex;
    }

    // Quickselect to find the median x-coordinate
    public static Coordinate quickSelect(ArrayList<Coordinate> points, int low, int high, int k) {
        if (low == high) {
            return points.get(low);
        }
        int pivotIndex = low + (high - low) / 2; // select pivot
        pivotIndex = partition(points, low, high, pivotIndex);
        if (k == pivotIndex) {
            return points.get(k);
        } else if (k < pivotIndex) {
            return quickSelect(points, low, pivotIndex - 1, k);
        } else {
            return quickSelect(points, pivotIndex + 1, high, k);
        }
    }

    // T(n) using divide and conquer
    public static double closestRecursive(ArrayList<Coordinate> xPoints, int n) {
        // Base Case
        if (n <= 3) {
            return bruteForce(xPoints, n);
        }

        // Find median x-coordinate (O(n) using quickSelect)
        int mid = n / 2;
        Coordinate midPoint = quickSelect(xPoints, 0, n - 1, mid);

        // Divide the points into left and right halves
        ArrayList<Coordinate> leftHalf = new ArrayList<>(xPoints.subList(0, mid));
        ArrayList<Coordinate> rightHalf = new ArrayList<>(xPoints.subList(mid, n));

        // Dividing the problem --> T(n/2) + T(n/2)
        double leftDist = closestRecursive(leftHalf, leftHalf.size());
        double rightDist = closestRecursive(rightHalf, rightHalf.size());

        // Finding the minimum distance from the left and right sides
        double minDist = Math.min(leftDist, rightDist);

        // This step of merging from left and right takes O(n) time
        ArrayList<Coordinate> strip = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (Math.abs(xPoints.get(i).x - midPoint.x) < minDist) {
                strip.add(xPoints.get(i));
            }
        }

        // Finding the closest points in the strip and comparing them with minDist
        return Math.min(minDist, closestInBox(strip, minDist));
    }

    // Main function to find the closest distance
    public static double findClosestDistance(ArrayList<Coordinate> points, int n) {
        return closestRecursive(points, n);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        //Taking input from user for number of points in 2-D plane
        System.out.print("Enter the number of points n: ");
        int n = scanner.nextInt();

        ArrayList<Coordinate> points = new ArrayList<>();

        // Taking random x,y value
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(100);
            int y = random.nextInt(100);
            points.add(new Coordinate(x, y));
        }
        //Starting clock for calculating elapsed time
        long startTime = System.nanoTime();

        // Finding the closest distance
        double closestDistance = findClosestDistance(points, n);

        //Stopping clock for calculating elapsed time
        long endTime = System.nanoTime();

        long elapsedTime = endTime - startTime;

        System.out.println("Elapsed time: " + elapsedTime + " nanoseconds");

        scanner.close();
    }
}

// Coordinate class to store points
class Coordinate {
    int x, y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
