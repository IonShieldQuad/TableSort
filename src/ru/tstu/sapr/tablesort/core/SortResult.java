package ru.tstu.sapr.tablesort.core;

public class SortResult {
  private int methodIndex;
  private long time;
  private int[] data;

  SortResult(int methodIndex, long time, int[] data) {
    this.methodIndex = methodIndex;
    this.time = time;
    this.data = data;
  }

  public int getMethodIndex() {
    return methodIndex;
  }

  public long getTime() {
    return time;
  }
}
