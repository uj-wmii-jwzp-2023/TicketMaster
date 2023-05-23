CREATE OR REPLACE FUNCTION fun()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    AS $BODY$ DECLARE ticket_pool_id int;
        BEGIN
            ticket_pool_id = OLD.ticket_pool_id;
            UPDATE ticket_pools
            SET tickets_left = tickets_left + 1
            WHERE id = ticket_pool_id;
            RETURN NULL;
        END;
    $BODY$;


CREATE TRIGGER tickets_left_update
    AFTER DELETE ON tickets
    FOR EACH ROW
    EXECUTE PROCEDURE fun();