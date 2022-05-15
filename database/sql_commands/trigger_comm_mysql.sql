CREATE EVENT sh_deleting_event
    ON SCHEDULE
      EVERY 1 DAY
    DO
      DELETE FROM search_history WHERE DATEDIFF(content_date, CURRENT_TIMESTAMP) < -29;
