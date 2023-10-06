#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
    int value;
    int id;
    int pos;
} sjtTuple;

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
    for (int i = 0; i < uniqueElements; i++) {
        (*counts)[i] = tempCounts[i];
    }
    return uniqueElements;
}

int permutationCount(int *duplicateCounts, int uniqueCount) {
    int correctionValue = 1;
    int elementCount = 0;
    for (int i = 0; i < uniqueCount; i++) {
        correctionValue *= factorial(duplicateCounts[i]);
        elementCount += duplicateCounts[i];
    }
    return factorial(elementCount) / correctionValue;
}

void initNums(int* nums, int length) {
    for (int i = 0; i < length; i++) {
        nums[i] = 1;
    }
    for (int i = length; i < 8; i++) {
        nums[i] = 0;
    }
}

char nextRelevant(int *current, int length, int base, char hit) {
    int i = 0;
    if (hit) {
        int hitNum = current[i];
        while (i < length && current[i] == hitNum) {
            i++;
        }
        if (i == length) {
            return 0;
        }
    }
    else  {
        while (i < length && (current[i] + 1) == base)
            i++;
        if (i == length)
            return 0;
    }
    int newNum = ++current[i];
    i--;
    while (i >= 0) {
        current[i] = newNum;
        i--;
    }
    return 1;
}

int calculateNullVariantsCount(int numbers) {
    int counts[] = { numbers, 8 - numbers };
    return permutationCount(counts, 2);
}

long countVariants(int n) {
    long count = 16;
    int nums[8];
    char hit;
    int target = n * n;
    for (int i = 2; i <= 8; i++)
    {
        long nc = calculateNullVariantsCount(i);
        initNums(nums, i);
        do {
            if (squareSum(nums, i) >= target) {
                hit = 1;
                if (squareSum(nums, i) == target) {
                    int *duplicateMap;
                    int uniqueCount = countSortedDuplicates(nums, i, &duplicateMap);
                    count += ((long)permutationCount(duplicateMap, uniqueCount)) * nc * (1L << i);
                    free(duplicateMap);
                }
            }
            else {
                hit = 0;
            }
        } while(nextRelevant(nums, i, n, hit));
    }
    return count;
}

void swapElements(sjtTuple *elements, int *original, int pos1, int pos2) {
    sjtTuple tempE = elements[pos1];
    int tempO = original[pos1];
    elements[pos1] = elements[pos2];
    elements[pos2] = tempE;
    original[pos1] = original[pos2];
    original[pos2] = tempO;


}

char checkOrder(sjtTuple *elements, int length, int uniqueElements, int *counters) {
    for (int i = 0; i < uniqueElements; i++) {
        counters[i] = 0;
    }
    
    for (int i = 0; i < length; i++) {
        if (counters[elements[i].id] != elements[i].pos) {
            return 0;
        }
        counters[elements[i].id]++;
    }
    return 1;
}

void forEachPermutation(int *array, int length, void (*f)(int*, int)) {
    int *duplicateMap;
    int uniqueElements = countSortedDuplicates(array, length, &duplicateMap);
    sjtTuple *elements = malloc(length * sizeof(sjtTuple));
    int pos = 0;
    for (int i = 0; i < uniqueElements; i++) {
        for (int j = 0; j < duplicateMap[i]; j++) {
            elements[pos].value = array[pos];
            elements[pos].id = i;
            elements[pos].pos = j;
            pos++;
        }
    }
    //int stepsRemaining = factorial(length);
    free(duplicateMap);
    
    int *orderCounter = malloc(uniqueElements * sizeof(int));
    int *copy = malloc(length * sizeof(int));
    memcpy(copy, array, length * sizeof(int));

    

    free(elements);
    free(copy);
}

void printAllVersions(int* arr, int length) {
    for (int i = 0; i < length; i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");
}

int main() {
    //int arr[] = {1, 2, 3, 4, 5, 6, 7, 8};
    //forEachPermutation(arr, 5, printAllVersions);
    printf("%ld\n", countVariants(50));
}