CREATE OR REPLACE FUNCTION tickets_left_decremental()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    AS $BODY$ DECLARE ticket_pool_id bigint;
        BEGIN
            ticket_pool_id = NEW.ticket_pool_id;
            UPDATE ticket_pools
            SET tickets_left = tickets_left - 1
            WHERE id = ticket_pool_id;
            RETURN NULL;
        END;
    $BODY$;


CREATE TRIGGER tickets_left_on_insert_update
    AFTER INSERT ON tickets
    FOR EACH ROW
    EXECUTE PROCEDURE tickets_left_decremental();


CREATE OR REPLACE FUNCTION user_balance_updater()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    AS $BODY$ DECLARE purchased_by_id bigint;
              DECLARE ticket_price numeric;
        BEGIN
            ticket_price = (SELECT price FROM ticket_pools WHERE id = NEW.ticket_pool_id);
            purchased_by_id = NEW.purchased_by_id;
            UPDATE users
            SET cash = cash - ticket_price
            WHERE id = purchased_by_id;
            RETURN NULL;
        END;
    $BODY$;


CREATE TRIGGER user_balance_update
    AFTER UPDATE ON tickets
    FOR EACH ROW
    EXECUTE PROCEDURE user_balance_updater();