package mm.listeners;

import mm.App;
import mm.OperationResult;
import mm.VcpkgService;
import mm.VcpkgServiceImpl;

import javax.swing.*;

public class VcpkgCaller {

    private static JTextArea textArea;

    static void vcpkgCallResultHandler(OperationResult result, JLabel processLabel, JFrame frame, VcpkgService service) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (result.exitCode != 0) {
                    // confirmation needed
                    if (result.exitCode == -3) {
                        textArea = new JTextArea(result.result, 10, 50);
                        textArea.setEditable(false);
                        JScrollPane sp = new JScrollPane(textArea);
                        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

                        Object[] options = {"Proceed", "Cancel"};
                        int n = JOptionPane.showOptionDialog(frame,
                                sp,
                                "Confirmations",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[1]);
                        if (n == 0) {
                            OperationResult result1 = new VcpkgServiceImpl().removePkgRecursively(result.name);
                            VcpkgCaller.vcpkgCallResultHandler(result1, processLabel, frame, service);
                        } else
                            return;
                    } else {
                        textArea = new JTextArea(result.result, 10, 50);
                        textArea.setEditable(false);
                        JScrollPane sp = new JScrollPane(textArea);
                        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                        JOptionPane.showMessageDialog(frame, sp);
                    }
                } else {
                    App.updateTablePanel();
                    JOptionPane.showMessageDialog(frame, result.result);
                }

                if (service.getInstallations().size() > 0)
                    App.updateProcessLabel("Installing...");
                else
                    App.updateProcessLabel("");
            }
        });
    }
}
