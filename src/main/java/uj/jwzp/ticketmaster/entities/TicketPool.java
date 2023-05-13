package uj.jwzp.ticketmaster.entities;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket_pools")
public class TicketPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int ticketsLeft;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "concert_id", referencedColumnName = "id")
    private Concert concert;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "zone_id", referencedColumnName = "id")
    private LocationZone locationZone;

    @OneToMany(mappedBy = "ticketPool")
    private List<Ticket> ticket;


    public TicketPool() {}

    public TicketPool(Concert concert, LocationZone locationZone, BigDecimal price, int ticketsLeft) {
        this.concert = concert;
        this.locationZone = locationZone;
        this.price = price;
        this.ticketsLeft = ticketsLeft;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTicketsLeft() {
        return ticketsLeft;
    }

    public void setTicketsLeft(int ticketsLeft) {
        this.ticketsLeft = ticketsLeft;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }

	public LocationZone getLocationZone() {
		return locationZone;
	}

	public void setLocationZone(LocationZone locationZone) {
		this.locationZone = locationZone;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
