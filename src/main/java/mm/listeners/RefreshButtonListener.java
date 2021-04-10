package mm.listeners;

import mm.App;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RefreshButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        App.updateTablePanel();
    }
}
