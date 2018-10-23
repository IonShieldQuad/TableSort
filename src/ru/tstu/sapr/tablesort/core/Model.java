package ru.tstu.sapr.tablesort.core;

import ru.tstu.sapr.tablesort.core.sorter.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Model {

  public static final String[] SORT_METHODS_NAMES = {
    "Quick sort", "Cocktail sort", "Shell sort",
    "Heap sort", "Radix sort", "Gnome sort",
    "Selection sort", "Insertion sort", "Bucket sort"
  };

  private static final class Method {
    static final int QUICK_SORT     = 0; // by keliz
    static final int COCKTAIL_SORT  = 1; // by Medvedev
    static final int SHELL_SORT     = 2; // by Makarov
    static final int HEAP_SORT      = 3; // by makcimbx
    static final int RADIX_SORT     = 4; // by IonShieldQuad
    static final int GNOME_SORT     = 5; // by MagLoner
    static final int SELECTION_SORT = 6; // by Merkulov
    static final int INSERTION_SORT = 7; // by Eremin
    static final int BUCKET_SORT    = 8; // by Erkhova
  }

  private LogWriter logWriter;
  private int[] data;
  private int size;
  private List<SortResult> results;
  private boolean finished;
  private AppEventListener listener;

  Model(LogWriter logWriter, AppEventListener listener) {
    this.logWriter = logWriter;
    this.listener = listener;
    size = 0;
    data = new int[size];
    results = new ArrayList<>();
    finished = true;
  }

  void setDataSize(int size) {
    this.size = size;
  }
  
  List<SortResult> getTestResults() {
    return results;
  }
  
  boolean isTestFinished() {
    return finished;
  }
  
  int[] getData() {
    return data;
  }
  
  private void generateData() {
    logWriter.writeMessage("Generating new data set...");
    Random random = new Random();
    data = new int[size];

    for (int i = 0; i < data.length; i++)
      data[i] = random.nextInt(size + 1);

    logWriter.writeMessage("Data set generated");
  }

  void testAll() {
    
    if (!finished) {
      logWriter.writeMessage("Test not started, reason: previous test not finished");
      return;
    }
    
    List<Thread> threads = new ArrayList<>();
    logWriter.writeMessage("Testing all sort methods");

    results.clear();
    finished = false;
    generateData();
    
    //Generates threads
    for (int method = 0; method < SORT_METHODS_NAMES.length; ++method) {
      int m = method;
      threads.add(new Thread(() -> testMethod(m)));
    }
    
    //Starts threads
    threads.forEach(Thread::start);
    
    logWriter.writeMessage("Test threads started");
  }

  private void testMethod(int methodIndex) {
    Timer timer = new Timer();
    int[] copy = Arrays.copyOf(data, data.length);
    logWriter.writeMessage("Sorting data by: " + SORT_METHODS_NAMES[methodIndex]);

    Sorter sorter;
    switch (methodIndex) {
      case Method.QUICK_SORT:
        sorter = new QuickSorter();
        break;
      case Method.COCKTAIL_SORT:
        sorter = new CocktailSorter();
        break;
      case Method.SHELL_SORT:
        sorter = new ShellSorter();
        break;
      case Method.HEAP_SORT:
        sorter = new HeapSorter();
        break;
      case Method.RADIX_SORT:
        sorter = new RadixSorter();
        break;
      case Method.GNOME_SORT:
        sorter = new GnomeSorter();
        break;
      case Method.SELECTION_SORT:
        sorter = new SelectionSorter();
        break;
      case Method.INSERTION_SORT:
        sorter = new InsertionSorter();
        break;
      case Method.BUCKET_SORT:
        sorter = new BucketSorter();
        break;
      default:
        throw new IllegalArgumentException("Unknown sort method");
    }

    timer.start();
    copy = sorter.sort(copy);
    timer.stop();

    logWriter.writeMessage(String.format("Data sorted by " + SORT_METHODS_NAMES[methodIndex] + " in %d us", timer.getMicros()));
    
    results.add(new SortResult(methodIndex, timer.getMicros(), copy));
    
    if (results.size() >= SORT_METHODS_NAMES.length) {
      onTestFinish();
    }
    
  }
  
  private void onTestFinish() {
    finished = true;
    logWriter.writeMessage("Test finished successfully");
    listener.onAppEvent(Application.Event.TEST_FINISH);
  }
}
