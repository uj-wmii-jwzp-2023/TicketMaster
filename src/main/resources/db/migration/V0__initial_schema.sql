create table concerts (
	id bigserial not null,
	name varchar(255) not null,
	location_id bigint not null,
	primary key (id)
);

create table location_zones (
	id bigserial not null,
	name varchar(255) not null,
	seats integer not null,
	zone varchar(255) not null,
	location_id bigint not null,
	primary key (id)
);

create table locations (
	id bigserial not null,
	name varchar(255) not null,
	primary key (id)
);

create table ticket_pools (
	id bigserial not null,
	price numeric(38, 2) not null,
	tickets_left integer not null,
	concert_id bigint,
	zone_id bigint,
	primary key (id)
);

create table tickets (
	id bigserial not null,
	purchased_at timestamp(6),
	reserved_at timestamp(6),
	purchased_by_id bigint,
	reserved_by_id bigint,
	ticket_pool_id bigint not null,
	primary key (id)
);

create table users (
	id bigserial not null,
	cash numeric(38, 2) not null,
	password varchar(255) not null,
	role varchar(255) not null,
	username varchar(255) not null,
	primary key (id)
);

alter table if exists location_zones
add constraint UKa776x4ul6v42tyk7kl704rvd8 unique (location_id, zone);

alter table if exists locations
add constraint UK_qvgktk8bt8v993m1k9ld5036k unique (name);

alter table if exists users
add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

alter table if exists concerts
add constraint FKm2f89w27qxt78acmv6lr9x36x foreign key (location_id) references locations;

alter table if exists location_zones
add constraint FKptjrrrpkmvrbqkttqnm115hdg foreign key (location_id) references locations;

alter table if exists ticket_pools
add constraint FKtmt13270w70i3i76phiol4oon foreign key (concert_id) references concerts;

alter table if exists ticket_pools
add constraint FKn0c9ktbreik602iq4trub2h6c foreign key (zone_id) references location_zones;

alter table if exists tickets
add constraint FKrkjx1brfcok06dflrox5ive3g foreign key (purchased_by_id) references users;

alter table if exists tickets
add constraint FKk1siy7ts3y5s7xdan41io6v6e foreign key (reserved_by_id) references users;

alter table if exists tickets
add constraint FKtpvbu9w9pfdbnvscvbhapki2f foreign key (ticket_pool_id) references ticket_pools;
