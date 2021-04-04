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
                    if (result.exitCode == -5)
                        JOptionPane.showMessageDialog(frame, result.result);
                    //TODO show error msg
                } else {
                    DemoLayout.updateTablePanel();
                    JOptionPane.showMessageDialog(frame, result.result);
                }
            }
        });
    }
}
