package mm.listeners;

import mm.DemoLayout;
import mm.OperationResult;

import javax.swing.*;

public class VcpkgCaller {
    static void vcpkgCall(OperationResult result, JLabel processLabel, JFrame frame) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                processLabel.setText("");
                if (result.exitCode != 0) {
                    JOptionPane.showMessageDialog(frame, result.result);
                } else {
                    DemoLayout.updateTablePanel();
                    JOptionPane.showMessageDialog(frame, result.result);
                }
                frame.setVisible(true);
            }
        });
    }
}
