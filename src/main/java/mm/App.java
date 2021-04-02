package mm;

import java.util.List;

public class App {
    public static void main( String[] args ) {
        VcpkgService service = new VcpkgServiceImpl();
        String pkg = service.installPkg("bigint");
        System.out.println(pkg);
        List<Pkg> list = service.loadInstalledPkges();
        list.forEach(p -> System.out.println(p.toString()));
        pkg = service.removePkg("bigint");
        System.out.println(pkg);
    }
}
