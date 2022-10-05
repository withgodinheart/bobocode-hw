insert into advisors (cognito_username, email, first_name, last_name, role)
values ('1cog_user', 'advisor1@gmail.com', 'Alex', 'Roe', 'PARTNER'),
       ('2cog_user', 'advisor2@gmail.com', 'Sam', 'Ham', 'PARTNER'),
       ('3cog_user', 'advisor3@gmail.com', 'Alexa', 'Martines', 'ASSOCIATE'),
       ('4cog_user', 'advisor4@gmail.com', 'Arthur', 'Boo', 'PARTNER');

insert into applicants(cognito_username, email, first_name, last_name, city, number, street, zip, role, ssn)
values ('cusername', 'applicant@gmail.com', 'Applicator', 'Lastnameovich', 'Warsaw', '123', 'Street', '78450',
        'ASSOCIATE', '1231928');

insert into applications(amount, status, applicant_id, advisor_id, assigned_at)
values (30000, 'NEW', 1, NULL, NULL),
       (100000, 'ASSIGNED', 1, 1, '2022-09-30 16:37:01'),
       (50000, 'NEW', 1, NULL, NULL),
       (700000, 'NEW', 1, NULL, NULL),
       (300, 'NEW', 1, NULL, NULL);