package mm.listeners;

import mm.App;
import mm.OperationResult;
import mm.VcpkgServiceImpl;

import javax.swing.*;

public class VcpkgCaller {
    static void vcpkgCallResultHandler(OperationResult result, JLabel processLabel, JFrame frame) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (result.exitCode != 0) {

                    // confirmation needed
                    if (result.exitCode == -3) {
                        JTextArea textArea = new JTextArea(result.result, 10, 50);
                        textArea.setEditable(false);
                        JScrollPane sp = new JScrollPane(textArea);
                        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

                        Object[] options = {"Proceed", "Cancel"};
                        int n = JOptionPane.showOptionDialog(frame,
                                sp,
                                "vcpkg recursive action needed",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[1]);
                        switch (n) {
                            case 0:
                                OperationResult result1 = new VcpkgServiceImpl().removePkgRecursively(result.name);
                                VcpkgCaller.vcpkgCallResultHandler(result1, processLabel, frame);
                            case 1:
                                return;
                        }
                    } else
                        JOptionPane.showMessageDialog(frame, result.result);
                } else {
                    App.updateTablePanel();
                    JOptionPane.showMessageDialog(frame, result.result);
                    App.updateProcessLabel("");
//                    processLabel.setText("");
//                frame.setVisible(true);
                }
            }
        });
    }
}
