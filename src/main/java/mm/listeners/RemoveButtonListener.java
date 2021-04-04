package mm.listeners;

import mm.DemoLayout;
import mm.OperationResult;
import mm.VcpkgService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveButtonListener implements ActionListener {

    private final JFrame frame;
    private final JTable table;
    private final VcpkgService service;
    private final JLabel processLabel;

    public RemoveButtonListener(JFrame frame, VcpkgService service, JLabel label, JTable table) {
        this.frame = frame;
        this.service = service;
        this.processLabel = label;
        this.table = table;
    }

    //TODO fix removing while installing in progress error (find out why it occurs st least..)

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1)
            return;
        String name = (String) table.getValueAt(selectedRow, 0);
//        processLabel.setText("Removing...");
        frame.setVisible(true);

        if (processLabel.getText().equals("Installing...")) {
            JOptionPane.showMessageDialog(frame, "Please wait for the installation process to finish.");
            return;
        }
        Thread t = new Thread(new Runnable() {
            public void run() {
                OperationResult result = service.removePkg(name);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
//                        processLabel.setText("");
                        if (result.exitCode == 0) {
                            DemoLayout.updateTablePanel();
                            frame.setVisible(true);

                        }
                        JOptionPane.showMessageDialog(frame, result.result);
                    }
                });
//                VcpkgCaller.vcpkgCall(result, processLabel, frame);
            }
        }, "removing package");
        t.start();
    }
}
