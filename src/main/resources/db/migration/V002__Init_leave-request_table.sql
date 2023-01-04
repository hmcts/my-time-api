CREATE TABLE public.leave_request (
  id integer PRIMARY KEY NOT NULL,
  employee_id integer NOT NULL,
  approver_id integer NOT NULL,
  type varchar(255) NOT NULL,
  status varchar(255) NOT NULL,
  start_date date NOT NULL,
  end_date date NOT NULL,
  request_comment varchar(255),
  approver_comment varchar(255)
);

CREATE SEQUENCE public.leave_request_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE public.leave_request_id_seq OWNED BY public.leave_request.id;
