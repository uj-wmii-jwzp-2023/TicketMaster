package uj.jwzp.ticketmaster.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "ticket_pool_id", nullable = false)
	private TicketPool ticketPool;

	@OneToOne
	@JoinColumn()
	private User reservedBy;

	@OneToOne
	@JoinColumn()
	private User purchasedBy;

	@Column()
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime reservedAt;

	@Column()
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime purchasedAt;

	public Ticket() {}

	public Ticket(TicketPool ticketPool, User reservedBy, User purchasedBy, LocalDateTime reservedAt, LocalDateTime purchasedAt) {
		this.ticketPool = ticketPool;
		this.reservedBy = reservedBy;
		this.purchasedBy = purchasedBy;
		this.reservedAt = reservedAt;
		this.purchasedAt = purchasedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TicketPool getTicketPool() {
		return ticketPool;
	}

	public void setTicketPool(TicketPool ticketPool) {
		this.ticketPool = ticketPool;
	}

	public User getReservedBy() {
		return reservedBy;
	}

	public void setReservedBy(User reservedBy) {
		this.reservedBy = reservedBy;
	}

	public User getPurchasedBy() {
		return purchasedBy;
	}

	public void setPurchasedBy(User purchasedBy) {
		this.purchasedBy = purchasedBy;
	}

	public LocalDateTime getReservedAt() {
		return reservedAt;
	}

	public void setReservedAt(LocalDateTime reservedAt) {
		this.reservedAt = reservedAt;
	}

	public LocalDateTime getPurchasedAt() {
		return purchasedAt;
	}

	public void setPurchasedAt(LocalDateTime purchasedAt) {
		this.purchasedAt = purchasedAt;
	}

}
