package mm;

public class OperationResult {
    public final String name;
    public final int exitCode;
    public final String result;

    OperationResult(int exitCode, String result, String name) {
        this.exitCode = exitCode;
        this.result = result;
        this.name = name;
    }
}
