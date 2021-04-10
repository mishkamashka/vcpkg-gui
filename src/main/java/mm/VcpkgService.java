package mm;

import java.util.List;
import java.util.Set;

public interface VcpkgService {

    OperationResult testVcpkgPath(String vcpkgPath);

    List<Pkg> loadInstalledPkges();

    OperationResult installPkg(String name);

    OperationResult removePkg(String name);

    OperationResult removePkgRecursively(String name);

    String getPath();

    Set<String> getInstallations();
}
