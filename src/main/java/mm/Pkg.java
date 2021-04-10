package mm;

import java.util.regex.Pattern;

public class Pkg {
    public final String name;
    public final String version;
    public final String description;

    public Pkg(String name) {
        this.name = name;
        this.version = "";
        this.description = "";
    }

    public Pkg(String name, String field) {
        this.name = name;
        if (Pattern.compile("\\d").matcher(field).find()) {
            this.version = field;
            this.description = "";
        }
        else {
            this.description = field;
            this.version = "";
        }
    }

    public Pkg(String name, String version, String description) {
        this.name = name;
        this.version = version;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name + " " + this.version + " " + this.description;
    }

    public String[] toStringArray() {
        return new String[]{this.name, this.version, this.description};
    }


}
