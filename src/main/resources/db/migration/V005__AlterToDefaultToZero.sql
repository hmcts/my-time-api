ALTER TABLE public.User
  ALTER COLUMN bonusEntitlement SET DEFAULT 0,
ALTER
COLUMN managerId SET DEFAULT 0;

-- Test null is defaulted to 0
INSERT INTO public.User(foreName,
                        surName,
                        startDate,
                        contractHours,
                        bonusEntitlement)
VALUES ('Rob', 'Robson', '2003-11-24', 37, 3);
