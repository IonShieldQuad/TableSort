package ru.tstu.sapr.tablesort.core.sorter;

/* by Medvedev */
public class CocktailSorter extends Sorter {

  @Override
  void sortInternal(int[] array) {
    boolean swapped;
    do {
      swapped = false;

      for (int i = 0; i <= array.length - 2; i++) {
        if (array[i] > array[i + 1]) {
          int temp = array[i];
          array[i] = array[i + 1];
          array[i + 1] = temp;
          swapped = true;
        }
      }

      if (!swapped) {
        break;
      }
      swapped = false;

      for (int i = array.length - 2; i >= 0; i--) {
        if (array[i] > array[i + 1]) {
          int temp = array[i];
          array[i] = array[i + 1];
          array[i + 1] = temp;
          swapped = true;
        }
      }
    } while (swapped);
  }
}
