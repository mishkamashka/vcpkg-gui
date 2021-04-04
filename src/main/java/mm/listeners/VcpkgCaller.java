package mm.listeners;

import mm.DemoLayout;
import mm.OperationResult;
import mm.VcpkgServiceImpl;

import javax.swing.*;

public class VcpkgCaller {
    static void vcpkgCall(OperationResult result, JLabel processLabel, JFrame frame) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                processLabel.setText("");
                if (result.exitCode != 0) {
                    if (result.exitCode == -3) {
                        Object[] options = {"Proceed", "Cancel"};
                        int n = JOptionPane.showOptionDialog(frame,
                                result.result,
                                "vcpkg recursive action needed",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[1]);
                        switch (n){
                            case 0:
                                OperationResult result1 = new VcpkgServiceImpl().removePkgRecursively(result.name);
                                VcpkgCaller.vcpkgCall(result1, processLabel, frame);
                            case 1:
                                return;
                        }
                    } else
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
