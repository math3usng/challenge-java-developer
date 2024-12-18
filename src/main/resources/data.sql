INSERT INTO public.neurotech_client (id, age, income, name) VALUES ('1a2b3c4d-aaaa-46a5-ad55-8d6afe1be04c', 25, 12000, 'Alice');
INSERT INTO public.neurotech_client (id, age, income, name) VALUES ('2b3c4d5e-bbbb-46a5-ad55-8d6afe1be04c', 30, 8000, 'Bob');
INSERT INTO public.neurotech_client (id, age, income, name) VALUES ('3c4d5e6f-cccc-46a5-ad55-8d6afe1be04c', 45, 15000, 'Charlie');
INSERT INTO public.neurotech_client (id, age, income, name) VALUES ('4d5e6f7g-dddd-46a5-ad55-8d6afe1be04c', 70, 3000, 'Diana');
INSERT INTO public.neurotech_client (id, age, income, name) VALUES ('5e6f7g8h-eeee-46a5-ad55-8d6afe1be04c', 18, 10000, 'Eve');
INSERT INTO public.neurotech_client (id, age, income, name) VALUES ('6f7g8h9i-ffff-46a5-ad55-8d6afe1be04c', 35, 6000, 'Frank');
INSERT INTO public.neurotech_client (id, age, income, name) VALUES ('7g8h9i0j-gggg-46a5-ad55-8d6afe1be04c', 50, 20000, 'Grace');
INSERT INTO public.neurotech_client (id, age, income, name) VALUES ('8h9i0j1k-hhhh-46a5-ad55-8d6afe1be04c', 22, 5000, 'Henry');

INSERT INTO public.credit (id, interest_rate, type, client_id) VALUES ('aaa11111-aaaa-4fc6-b678-71a0afc02295', 5, 'FIXED_INTEREST', '1a2b3c4d-aaaa-46a5-ad55-8d6afe1be04c');
INSERT INTO public.credit (id, interest_rate, type, client_id) VALUES ('bbb22222-bbbb-4fc6-b678-71a0afc02295', 6, 'VARIABLE_INTEREST', '2b3c4d5e-bbbb-46a5-ad55-8d6afe1be04c');
INSERT INTO public.credit (id, interest_rate, type, client_id) VALUES ('ccc33333-cccc-4fc6-b678-71a0afc02295', 4, 'PAYROLL', '3c4d5e6f-cccc-46a5-ad55-8d6afe1be04c');
INSERT INTO public.credit (id, interest_rate, type, client_id) VALUES ('ddd44444-dddd-4fc6-b678-71a0afc02295', 4.5, 'PAYROLL', '4d5e6f7g-dddd-46a5-ad55-8d6afe1be04c');
INSERT INTO public.credit (id, interest_rate, type, client_id) VALUES ('eee55555-eeee-4fc6-b678-71a0afc02295', 5, 'FIXED_INTEREST', '5e6f7g8h-eeee-46a5-ad55-8d6afe1be04c');
INSERT INTO public.credit (id, interest_rate, type, client_id) VALUES ('fff66666-ffff-4fc6-b678-71a0afc02295', 6, 'VARIABLE_INTEREST', '6f7g8h9i-ffff-46a5-ad55-8d6afe1be04c');
INSERT INTO public.credit (id, interest_rate, type, client_id) VALUES ('ggg77777-gggg-4fc6-b678-71a0afc02295', 7, 'VARIABLE_INTEREST', '7g8h9i0j-gggg-46a5-ad55-8d6afe1be04c');
INSERT INTO public.credit (id, interest_rate, type, client_id) VALUES ('hhh88888-hhhh-4fc6-b678-71a0afc02295', 5, 'FIXED_INTEREST', '8h9i0j1k-hhhh-46a5-ad55-8d6afe1be04c');
