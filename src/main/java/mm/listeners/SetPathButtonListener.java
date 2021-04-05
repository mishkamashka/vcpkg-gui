package mm.listeners;

import mm.VcpkgService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetPathButtonListener implements ActionListener {

    private Frame frame;
    private JTextField field;
    private String path;
    private JButton button;
    private VcpkgService service;

    public SetPathButtonListener(Frame frame, JButton button, JTextField field, String path, VcpkgService service) {
        this.frame = frame;
        this.field = field;
        this.path = path;
        this.button = button;
        this.service = service;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (button.getText().equals("edit")) {
//            JOptionPane.showMessageDialog(frame, path);
            field.setEditable(true);
            button.setText("ok");
        } else {
            String s = field.getText();
            field.setEditable(false);
            System.out.println(s);
            service.testVcpkg(s);
            button.setText("edit");
            //todo check vcpkg valid path, save path in service
        }

    }
}
