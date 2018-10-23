package ru.tstu.sapr.tablesort.core;

import ru.tstu.sapr.tablesort.ui.MainWindow;

public class Application implements AppEventListener {
  public enum Event { TEST_ALL, TEST_FINISH }

  private MainWindow mainWindow;
  private Model model;

  private Application() {
    mainWindow = new MainWindow(this);
    LogWriter globalLogWriter = mainWindow;
    model = new Model(globalLogWriter, this);
  }

  @Override
  public void onAppEvent(Event event) {
    switch (event) {
      case TEST_ALL:
        model.setDataSize(mainWindow.getDataSize());
        model.testAll();
        break;
      case TEST_FINISH:
        mainWindow.updateList(model.getTestResults());
    }
  }

  public static void main(String[] args) {
    new Application();
  }
}
