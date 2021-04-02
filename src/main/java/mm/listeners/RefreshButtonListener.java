package mm.listeners;

import mm.DemoLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RefreshButtonListener implements ActionListener {
    private final JFrame frame;

    public RefreshButtonListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        DemoLayout.updateTablePanel();
        frame.setVisible(true);
    }
}
