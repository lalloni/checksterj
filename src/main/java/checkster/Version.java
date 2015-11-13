package checkster;

public class Version {

    private String service;

    private int major;

    private int minor;

    private int patch;

    private String meta;

    public Version(String service, int major, int minor, int patch, String meta) {
        super();
        this.service = service;
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.meta = meta;
    }

    public String getService() {
        return service;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public String getMeta() {
        return meta;
    }

}
