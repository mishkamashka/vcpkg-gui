package mm;

import java.util.List;

public interface VcpkgService {

    void testVcpkg(String vcpkgPath);

    List<Pkg> loadInstalledPkges();

    OperationResult installPkg(String name);

    OperationResult removePkg(String name);

    OperationResult removePkgRecursively(String name);

}
