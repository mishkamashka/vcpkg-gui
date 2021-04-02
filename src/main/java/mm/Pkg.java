package mm;

public class Pkg {
    public final String name;
    public final String version;
    public final String description;

    public Pkg(String name) {
        this.name = name;
        this.version = "";
        this.description = "";
    }

    public Pkg(String name, String description) {
        this.name = name;
        this.version = "";
        this.description = description;
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
