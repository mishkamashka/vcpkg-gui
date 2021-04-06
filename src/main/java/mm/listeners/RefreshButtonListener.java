package mm.listeners;

import mm.App;

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
        App.updateTablePanel();
        frame.setVisible(true);
    }
}
