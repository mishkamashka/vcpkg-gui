package mm.listeners;

import mm.OperationResult;
import mm.VcpkgService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddButtonListener implements ActionListener {

    private final JFrame frame;
    private final VcpkgService service;
    private final JLabel processLabel;

    public AddButtonListener(JFrame frame, VcpkgService service, JLabel label) {
        this.frame = frame;
        this.service = service;
        this.processLabel = label;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String name = JOptionPane.showInputDialog(frame,"Package name to install:");
        if (name == null || name.equals(""))
            return;

        if (service.getInstallations().size() > 0) {
            Object[] options = {"Proceed", "Cancel"};
            int n = JOptionPane.showOptionDialog(frame,
                    "There is another installation in progress. Having several installations at the same time may load the CPU too much." +
                            "\nDo want to install package anyway?",
                    "Install package",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == 1)
                return;
        }
        processLabel.setText("Installing...");

        Thread t = new Thread(new Runnable() {
            public void run() {
                OperationResult result = service.installPkg(name);
                VcpkgCaller.vcpkgCallResultHandler(result, processLabel, frame, service);
            }
        }, "installing package");
        t.start();
    }
}
