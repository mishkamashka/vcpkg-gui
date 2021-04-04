package mm;

public class OperationResult {
    public final int exitCode;
    public final String result;

    OperationResult(int exitCode, String result) {
        this.exitCode = exitCode;
        this.result = result;
    }
}
