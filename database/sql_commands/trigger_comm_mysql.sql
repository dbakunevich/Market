/*1tg_insert*/
DELIMITER $$
CREATE TRIGGER search_history_add_tg
  BEFORE INSERT ON 
    search_history
  FOR EACH ROW
BEGIN
	delete from search_history sh where sh.content_date < (SYSDATE() - 30);
END$$
DELIMITER ;
