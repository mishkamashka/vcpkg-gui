package mm.listeners;

import mm.App;
import mm.OperationResult;
import mm.VcpkgService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetPathButtonListener implements ActionListener {

    private Frame frame;
    private JTextField field;
    private JButton button;
    private VcpkgService service;

    public SetPathButtonListener(Frame frame, JButton button, JTextField field, VcpkgService service) {
        this.frame = frame;
        this.field = field;
        this.button = button;
        this.service = service;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (button.getText().equals("edit")) {
            field.setEditable(true);
            button.setText("ok");
        } else {

            String s = field.getText();

            System.out.println(s);

            Thread t = new Thread(new Runnable() {
                public void run() {
                    OperationResult result = service.testVcpkgPath(s);
                    SwingUtilities.invokeLater(new Runnable(){
                        public void run() {
                            if (result.exitCode != 0) {
                                JOptionPane.showMessageDialog(frame, result.result);
                            } else {
                                App.updateTablePanel();
                                JOptionPane.showMessageDialog(frame, result.result);
                                field.setText(result.name);
                                field.setEditable(false);
                                button.setText("edit");
                            }
                            frame.setVisible(true);
                        }
                    });
                }
            }, "testing vcpkg");
            t.start();
        }

    }
}
