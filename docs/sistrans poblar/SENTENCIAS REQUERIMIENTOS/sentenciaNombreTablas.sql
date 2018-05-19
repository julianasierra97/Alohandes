with
query1 as 
(SELECT owner, table_name as nombreTabla
  FROM all_tables
  where owner = 'ISIS2304A671810'),
  
  query2 as(
  SELECT table_name, column_name, data_type, data_length
FROM USER_TAB_COLUMNS),

query3 as
(SELECT constraint_name, table_name
  FROM user_cons_columns
  )
  
  select query1.nombreTabla, query2.column_name, query2.data_type, query3.constraint_name
  from query1, query2, query3
  where query1.nombreTabla = query2.table_name AND query1.nombreTabla = query3.table_name
  
  ;

