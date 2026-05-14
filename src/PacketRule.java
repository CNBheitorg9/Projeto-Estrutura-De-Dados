public class PacketRule implements Comparable<PacketRule> {

    private int id;
    private String sourceIp;
    private String destinationIp;
    private int priority;

    public PacketRule(int id, String sourceIp, String destinationIp, int priority) {
        this.id = id;
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getDestinationIp() {
        return destinationIp;
    }

    public void setDestinationIp(String destinationIp) {
        this.destinationIp = destinationIp;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(PacketRule other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "PacketRule{" +
                "id=" + id +
                ", sourceIp='" + sourceIp + '\'' +
                ", destIp='" + destinationIp + '\'' +
                ", priority=" + priority +
                '}';
    }
}