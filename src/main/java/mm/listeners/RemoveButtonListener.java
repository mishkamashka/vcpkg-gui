package mm.listeners;

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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        //TODO fix removing while installing in progress error (find out why it occurs at least..)
        if (service.getInstallations().size() != 0) {
            JOptionPane.showMessageDialog(frame, "Please wait for the installation process to finish.");
            return;
        }

        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1)
            return;
        String name = (String) table.getValueAt(selectedRow, 0);

        Object[] options = {"Remove", "Cancel"};
        int n = JOptionPane.showOptionDialog(frame,
                "Remove " + name + " package?",
                "Remove package",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (n == 1)
            return;


        processLabel.setText("Removing...");

        Thread t = new Thread(new Runnable() {
            public void run() {
                OperationResult result = service.removePkg(name);
                VcpkgCaller.vcpkgCallResultHandler(result, processLabel, frame, service);
            }
        }, "removing package");
        t.start();

    }
}
