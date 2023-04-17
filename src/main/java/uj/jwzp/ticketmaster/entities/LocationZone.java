package uj.jwzp.ticketmaster.entities;

import jakarta.persistence.*;

@Entity
@Table(
    name="location_zones",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = { "location_id", "zone" }),
    }
)
public class LocationZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String zone;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int seats;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @OneToOne(mappedBy = "locationZone")
    private TicketPool ticketPool;


    public LocationZone() {}

    public LocationZone(Location location, String name, String zone, int seats) {
        this.location = location;
        this.name = name;
        this.zone = zone;
        this.seats = seats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

}
