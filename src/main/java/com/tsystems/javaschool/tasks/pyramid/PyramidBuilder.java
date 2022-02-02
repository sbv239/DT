package com.tsystems.javaschool.tasks.pyramid;

import java.util.List;

public class PyramidBuilder {

    int rowConter;

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */

    public int[][] buildPyramid(List<Integer> inputNumbers) throws CannotBuildPyramidException {
        if (!isPossible(inputNumbers)) {
            throw new CannotBuildPyramidException();
        }

        int[][] myArray = new int[rowConter + 1][2 * rowConter + 1];

        toFillArray(myArray, inputNumbers);
        return myArray;
    }

    private void toFillArray(int[][] array, List<Integer> inputNumbers) {
        int counter = 0;

        inputNumbers.sort(Integer::compareTo);

        for (int i = 0; i < array.length; i++) {
            int startIndex = array[i].length / 2 - i;
            for (int j = startIndex; j <= array[i].length - 1 - startIndex; j += 2) {
                array[i][j] = inputNumbers.get(counter);
                counter++;
            }
        }
    }

    private boolean isPossible(List<Integer> inputNumbers) {
        if (inputNumbers.contains(null)) {
            return false;
        }
        for (long i = 1; i <= inputNumbers.size(); i += 2 + rowConter++) {
            if (inputNumbers.size() == i) {
                return true;
            }
        }
        return false;
    }
}
