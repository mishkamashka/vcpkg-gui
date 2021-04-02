package mm;

import java.io.*;
import java.util.List;

public class VcpkgServiceImpl implements VcpkgService {

    boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");


    @Override
    public void testVcpkg(){
        ProcessBuilder builder = createCommand("./vcpkg/vcpkg version");
        try {
            Process process = builder.start();
            final int exitCode = process.waitFor();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            System.out.println(exitCode);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Pkg> loadInstalledPkges() {
        return null;
    }

    @Override
    public Pkg installPkg(String name) {
        return null;
    }

    @Override
    public void removePkg(Pkg pkg) {

    }

    private ProcessBuilder createCommand(String command) {
        ProcessBuilder builder = new ProcessBuilder();
        if (isWindows) {
            builder.command("cmd.exe", "/c", command);
        } else {
            builder.command("sh", "-c", command);
        }
        builder.directory(new File(System.getProperty("user.home")));
        return builder;
    }
}
