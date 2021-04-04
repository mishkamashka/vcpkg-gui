package mm.listeners;

import mm.DemoLayout;
import mm.OperationResult;
import mm.VcpkgService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddButtonListener implements ActionListener {

    private final JFrame frame;
    private final VcpkgService service;
    private final JLabel installationLabel;

    public AddButtonListener(JFrame frame, VcpkgService service, JLabel label) {
        this.frame = frame;
        this.service = service;
        this.installationLabel = label;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String name = JOptionPane.showInputDialog(frame,"Package name to install:");
        installationLabel.setText("Installing...");
        frame.setVisible(true);

        Thread t = new Thread(new Runnable() {
            public void run() {
                OperationResult result = service.installPkg(name);
                SwingUtilities.invokeLater(new Runnable(){
                    public void run() {
                        if (result.exitCode != 0) {
                            //TODO show error msg
                        } else {
                            installationLabel.setText("");
                            JOptionPane.showMessageDialog(frame, "Package " + name + " has been installed");
                            DemoLayout.updateTablePanel();
                            frame.setVisible(true);
                        }
                    }
                });
            }
        }, "Logic Code");
        t.start();

    }

}