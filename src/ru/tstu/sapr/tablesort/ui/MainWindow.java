package ru.tstu.sapr.tablesort.ui;

import ru.tstu.sapr.tablesort.core.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;

public class MainWindow extends JFrame implements LogWriter {
  //Application level
  private AppEventListener listener;
  //UI components
  private JPanel rootPanel;
  private JTextArea logArea;
  private JButton btnTestAll;
  private JTable tInfo;
  private JTextField dataSizeField;
  
  private static final String WINDOW_TITLE = "Sorting algorithms";
  private static final String[] COLUMN_NAMES = { "Sorting method", "Time (us)" };

  public MainWindow(AppEventListener listener) {
    this.listener = listener;
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setContentPane(rootPanel);
    setTitle(WINDOW_TITLE);
    setResizable(false);
    initComponents();
    setVisible(true);
    pack();
  }
  
  public int getDataSize() {
    return Integer.parseInt(dataSizeField.getText());
  }

  @Override
  synchronized public void writeMessage(String msg) {
    logArea.append(msg);
    logArea.append("\n");
  }

  private void initComponents() {
    btnTestAll.addActionListener(e ->
      listener.onAppEvent(Application.Event.TEST_ALL));
    
    
    //Sets model for info table
    tInfo.setModel(new DefaultTableModel(COLUMN_NAMES, Model.SORT_METHODS_NAMES.length) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });
  }

  public void updateList(List<SortResult> results) {
    TableModel model = tInfo.getModel();
    for (int i = 0; i < results.size(); ++i) {
      SortResult result = results.get(i);
      model.setValueAt(Model.SORT_METHODS_NAMES[result.getMethodIndex()], i, 0);
      model.setValueAt(result.getTime(), i, 1);
    }
  }
}
