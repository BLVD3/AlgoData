#include <stdio.h>
#include <stdlib.h>

int squareSum(int *array, int length) {
    int sum = 0;
    for (int i = 0; i < length; i++) {
        sum += array[i] * array[i];
    }
    return sum;
}

int factorial(int n) {
    int val = 1;
    for (int i = 2; i <= n; i++) {
        val *= i;
    }
    return val;
}

int countSortedDuplicates(int *data, int dataSize, int **counts) {
    int lastElement = data[0];
    int uniqueElements = 1;
    int *tempCounts = (int*)calloc(dataSize, sizeof(int));
    tempCounts[0] = 1;
    for (int i = 1; i < dataSize; i++) {
        if (lastElement != data[i]) {
            lastElement = data[i];
            uniqueElements++;
        }
        tempCounts[uniqueElements - 1]++;
    }
    *counts = malloc(sizeof(int) * uniqueElements);
    for (int i = 0; i < uniqueElements; i++)
    {
        (*counts)[i] = tempCounts[i];
    }
    return uniqueElements;
}

int permutationCount(int *duplicateCounts, int uniqueCount) {
    int correctionValue = 1;
    int elementCount = 0;
    for (int i = 0; i < uniqueCount; i++)
    {
        correctionValue *= factorial(duplicateCounts[i]);
        elementCount += duplicateCounts[i];
    }
    return factorial(elementCount) / correctionValue;
}

int main() {

}