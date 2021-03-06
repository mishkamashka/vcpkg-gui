package mm;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;

public class VcpkgServiceImpl implements VcpkgService {

    private static final boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    private static final Set<String> installations = new HashSet<>();
    private static String path = null;

    @Override
    public OperationResult testVcpkgPath(String vcpkgPath){
        StringBuilder result = new StringBuilder(vcpkgPath);
        ProcessBuilder builder = createCommand(vcpkgPath + " version");
        try {
            Process process = builder.start();
            int exitCode = process.waitFor();
            System.out.println(exitCode);
            if (exitCode != 0) {
                builder = createCommand(vcpkgPath + "/vcpkg version");
                result.append("/vcpkg");
                process = builder.start();
                exitCode = process.waitFor();
                if (exitCode != 0)
                    return new OperationResult(-1, "vcpkg is not found. Check vcpkg installation location and try again.\n" +
                            "You can as well set a VCPKG_PATH environment variable and restart the program.", vcpkgPath);
            }
            setPath(result.toString());
            return new OperationResult(0, "vcpkg is successfully found.\n" +
                    "Consider setting a VCPKG_PATH environment variable so you don't need\nto enter the path every time the program starts.", result.toString());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        //todo change error msg
        return new OperationResult(-2, "service exception", result.toString());
    }

    @Override
    public List<Pkg> loadInstalledPkges() {
        ProcessBuilder builder = createCommand(path + " list");
        List<Pkg> pkges = new ArrayList<>();
        try {
            Process process = builder.start();
            final int exitCode = process.waitFor();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while((line = reader.readLine()) != null) {
                String[] columns = {};
                String[] columnsByDots = line.split("\\.\\.\\. ");

                switch (columnsByDots.length) {
                    case 1:
                        columns = line.split("\\s+\\s+");
                        break;
                    case 3:
                        break;
                    case 2:
                        String[] columns1 = columnsByDots[0].split("\\s+\\s+");
                        String[] columns2 = columnsByDots[1].split("\\s+\\s+");
                        columns = ArrayUtils.insert(0, columns2, columns1);
                }


                Pkg pkg = null;
                switch (columns.length) {
                    case 3:
                        pkg = new Pkg(columns[0], columns[1], columns[2]);
                        break;
                    case 1:
                        pkg = new Pkg(columns[0]);
                        break;
                    case 2:
                        pkg = new Pkg(columns[0],columns[1]);
                }
                pkges.add(pkg);
            }
            reader.close();
            System.out.println(exitCode);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return pkges;
    }

    /**
     *
     * @param name
     * @return exitCode: 0 - success, -2 - exception in vcpkg interaction, -3 - recursive remove needed, other - other vcpkg error.
     */
    @Override
    public OperationResult installPkg(String name) {
        ProcessBuilder builder = createCommand(path + " install " + name);
        StringBuilder result = new StringBuilder("Exception in service.");

        int exitCode = -2;

        if (!installations.add(name))
            return new OperationResult(exitCode, "Package " + name + " is already being installed.\nPlease wait.", name);

        try {
            Process process = builder.start();
            exitCode = process.waitFor();
            installations.remove(name);
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            result = new StringBuilder("");
            if (exitCode != 0) {
                while((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } else {
                result = new StringBuilder("Package ").append(name).append(" has been installed.");
                while((line = reader.readLine()) != null)
                    if (Pattern.compile("The following packages are already installed:").matcher(line).find()) {
                        result = new StringBuilder("The following packages are already installed: ").append(name).append(".");
                        break;
                    }
            }
            reader.close();
            System.out.println(exitCode);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return new OperationResult(exitCode, result.toString(), name);
    }

    /**
     *
     * @param name - package name
     * @return OperationResult - exitCode: 0 - success, -2 - exception in vcpkg interaction, other - vcpkg error
     */
    @Override
    public OperationResult removePkg(String name) {
        ProcessBuilder builder = createCommand(path + " remove " + name);
        StringBuilder result = new StringBuilder("Exception in service.");
        int exitCode = -2;
        try {
            Process process = builder.start();
            exitCode = process.waitFor();
            String line;
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            result = new StringBuilder("");
            if (exitCode != 0) {
                while((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                    if (Pattern.compile("If you are sure you want to remove them, run the command with the --recurse option").matcher(line).find()){
                        exitCode = -3;
                        break;
                    }
                }
            } else {
                result = new StringBuilder("Package ").append(name).append(" has been removed.");
                while((line = reader.readLine()) != null)
                    if (Pattern.compile("The following packages are not installed, so not removed:").matcher(line).find()) {
                        result = new StringBuilder("The following packages are not installed, so not removed: ").append(name).append(".");
                        break;
                    }
            }
            reader.close();
            System.out.println(exitCode);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return new OperationResult(exitCode, result.toString(), name);
    }

    @Override
    public OperationResult removePkgRecursively(String name) {
        ProcessBuilder builder = createCommand(path + " remove --recurse " + name);
        StringBuilder result = new StringBuilder("Exception in service.");
        int exitCode = -2;
        try {
            Process process = builder.start();
            exitCode = process.waitFor();
            String line;
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            result = new StringBuilder("");
            if (exitCode != 0) {
                while((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } else {
                result = new StringBuilder("Package ").append(name).append(" has been removed.");
            }
            reader.close();
            System.out.println(exitCode);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return new OperationResult(exitCode, result.toString(), name);
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

    public String getPath() {
        return (path == null) ? "" : path;
    }

    public void setPath(String path) {
        //todo prbly better to set path here, not through test from App
        this.path = path;
    }

    public Set<String> getInstallations() {
        return installations;
    }
}
