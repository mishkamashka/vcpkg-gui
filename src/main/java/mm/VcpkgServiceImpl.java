package mm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VcpkgServiceImpl implements VcpkgService {

    boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    String vcpkgPath = "/vcpkg/vcpkg";

    @Override
    public void testVcpkg(){
        ProcessBuilder builder = createCommand("." + vcpkgPath + " version");
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
        ProcessBuilder builder = createCommand("./vcpkg/vcpkg list");
        List<Pkg> pkges = new ArrayList<>();
        try {
            Process process = builder.start();
            final int exitCode = process.waitFor();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while((line = reader.readLine()) != null) {
                String[] columns = line.split("\\s+\\s+");
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
                        //TODO is it possible for a package to be without description? if so, need to check whether it's version or description
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

    @Override
    public String installPkg(String name) {
        ProcessBuilder builder = createCommand("./vcpkg/vcpkg install " + name);
        StringBuilder result = new StringBuilder();
        try {
            Process process = builder.start();
            final int exitCode = process.waitFor();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (exitCode != 0) {
                while((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } else { //TODO probably need to tell if it had been already installed or just been installed
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
        return result.toString();
    }

    @Override
    public String removePkg(String name) {
        ProcessBuilder builder = createCommand("./vcpkg/vcpkg remove " + name);
        StringBuilder result = new StringBuilder();
        try {
            Process process = builder.start();
            final int exitCode = process.waitFor();
            String line;
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (exitCode != 0) {
                while((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
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
        return result.toString();
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
