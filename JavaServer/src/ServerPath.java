public enum ServerPath {
    HTML_DIRECTORY("JavaServer/static/html/");

    private final String directory;

    ServerPath(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return this.directory;
    }
}
