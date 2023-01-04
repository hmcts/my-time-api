SELECT pg_catalog.set_config('search_path', '', false);
SET
statement_timeout = 0;
SET
lock_timeout = 0;
SET
idle_in_transaction_session_timeout = 0;
SET
client_encoding = 'UTF8';
SET
standard_conforming_strings = on;
SET
check_function_bodies = false;
SET
xmloption = content;
SET
client_min_messages = warning;
SET
row_security = off;

-- Migration simply to check it gets applied through flyway...
SELECT COUNT(DISTINCT 'table_name')
FROM information_schema.columns
WHERE table_schema = 'public';

