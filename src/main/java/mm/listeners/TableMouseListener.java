package mm.listeners;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableMouseListener implements ListSelectionListener {

    private JTable table;

    public TableMouseListener(JTable table){
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());

    }
}
