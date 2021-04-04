package mm;

import javax.swing.*;
import java.util.List;

public class App {

    public static void main(String args[]){
        JFrame frame = new JFrame("My First GUI");
        Object[] options = {"Yes, please",
                "No, thanks",
                "No eggs, no ham!"};
        int res = JOptionPane.showOptionDialog(frame,
                "Would you like some green eggs to go "
                        + "with that ham?",
                "A Silly Question",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);

        System.out.println(res);
    }

    static void vcpkgUsage() {
        VcpkgService service = new VcpkgServiceImpl();
        OperationResult pkg = service.installPkg("bigint");
        System.out.println(pkg.result);
        List<Pkg> list = service.loadInstalledPkges();
        list.forEach(p -> System.out.println(p.toString()));
        pkg = service.removePkg("bigint");
        System.out.println(pkg);
    }
}
