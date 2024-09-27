**Closest Pair of Points in a 2D Plane**
**Overview**
This project implements an algorithm to find the closest pair of points in a given 2D plane, along with the computation of the elapsed time in nanoseconds. The solution utilizes the Divide and Conquer approach to achieve an optimal time complexity of O(n log n), making it more efficient than the brute force approach, which has a time complexity of O(n²).

**Algorithm**
**1. **Brute Force Approach****
The brute force method compares every pair of points and computes their Euclidean distance, resulting in a time complexity of O(n²). Although simple, it is inefficient for large datasets.

**2. Divide and Conquer Approach**
To improve efficiency, the Divide and Conquer method is implemented:

Step 1: The points are sorted based on their x-coordinates.
Step 2: The set of points is recursively divided into two halves until the base case is reached (when there are 3 or fewer points).
Step 3: After finding the closest pairs in the left and right halves, the algorithm checks for pairs of points that may span the dividing line.
Step 4: The algorithm compares the minimum distances found in both halves and across the dividing line.
This approach improves the time complexity to O(n log n) by reducing the number of comparisons required.

**Code Structure**
**Coordinate Class**: A simple class to represent a point with x and y coordinates.
**Brute Force Function**: Calculates the Euclidean distance between every pair of points in O(n²) time.
**Divide and Conquer Functions**:
**closestRecursive()**: Recursively divides the points and computes the minimum distance in each half.
**closestInBox()**: Checks the points within the strip near the dividing line for potential closer pairs.
**quickSelect()** and **partition()**: Used to efficiently find the median point.
**Main Function**: Accepts the number of points as input, generates random points in a 2D plane, computes the closest pair using the Divide and Conquer approach, and measures the execution time.
