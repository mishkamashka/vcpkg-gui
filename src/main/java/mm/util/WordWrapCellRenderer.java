package mm.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
    public WordWrapCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//        table.setRowSelectionAllowed(true);

        try {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
        } catch (NullPointerException ex) {
//            table.setRowSelectionInterval(row, row);
            System.out.println("null");
        }
        return this;
    }
}