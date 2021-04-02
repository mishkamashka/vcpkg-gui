package mm;

import java.util.List;

public interface VcpkgService {

    void testVcpkg();

    List<Pkg> loadInstalledPkges();

    Pkg installPkg(String name);

    void removePkg(Pkg pkg);

}
