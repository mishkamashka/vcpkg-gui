package mm;

import javax.swing.*;
import java.util.List;

public class App {

    public static void main(String args[]){
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        frame.getContentPane().add(button1);
        frame.getContentPane().add(button2);

        frame.setVisible(true);
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
