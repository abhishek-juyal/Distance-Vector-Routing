package distance_vector_routing;

public interface EventList
{
    public boolean add(Event e);
    public Event removeNext();
    public String toString();
    public double getLastPacketTime(int entityFrom, int entityTo);
}
