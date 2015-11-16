package checkster;

public class Version {

    private String service;

    private Integer major;

    private Integer minor;

    private Integer patch;

    private String meta;

    public Version(String service, Integer major, Integer minor, Integer patch, String meta) {
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

    public Integer getMajor() {
        return major;
    }

    public Integer getMinor() {
        return minor;
    }

    public Integer getPatch() {
        return patch;
    }

    public String getMeta() {
        return meta;
    }

}
