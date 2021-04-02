package mm;

import java.util.List;

public interface VcpkgService {

    void testVcpkg();

    List<Pkg> loadInstalledPkges();

    String installPkg(String name);

    String removePkg(String name);

}
