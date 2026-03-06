-- bootstrap.sql
-- Initializes the database, roles and permissions for the application.

\set ON_ERROR_STOP on


-- Create database if it does not exist
SELECT 'CREATE DATABASE book_social_network'
    WHERE NOT EXISTS (
    SELECT FROM pg_database WHERE datname = 'book_social_network'
)\gexec


-- Create migration role (used for schema changes)
SELECT format('CREATE ROLE %I LOGIN PASSWORD %L', :'API_DDL_USER', :'API_DDL_PASSWORD')
    WHERE NOT EXISTS (SELECT FROM pg_roles WHERE rolname = :'API_DDL_USER')
\gexec


-- Create application role (used by the app at runtime)
SELECT format('CREATE ROLE %I LOGIN PASSWORD %L', :'API_DML_USER', :'API_DML_PASSWORD')
    WHERE NOT EXISTS (SELECT FROM pg_roles WHERE rolname = :'API_DML_USER')
\gexec


-- Allow both roles to connect to the database
SELECT format('GRANT CONNECT ON DATABASE book_social_network TO %I', :'API_DDL_USER')\gexec
SELECT format('GRANT CONNECT ON DATABASE book_social_network TO %I', :'API_DML_USER')\gexec


-- Connect to the application database
\connect book_social_network


-- Make the migration role owner of the schema
SELECT format('ALTER SCHEMA public OWNER TO %I', :'API_DDL_USER')\gexec

-- Migration role can create objects
SELECT format('GRANT USAGE, CREATE ON SCHEMA public TO %I', :'API_DDL_USER')\gexec

-- Application role can use the schema but cannot create objects
SELECT format('GRANT USAGE ON SCHEMA public TO %I', :'API_DML_USER')\gexec
SELECT format('REVOKE CREATE ON SCHEMA public FROM %I', :'API_DML_USER')\gexec


-- Allow the application to read and write existing tables
SELECT format('GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO %I', :'API_DML_USER')\gexec

-- Allow the application to use sequences (needed for auto-increment IDs)
SELECT format('GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO %I', :'API_DML_USER')\gexec


-- Ensure future tables created by the migration role are accessible by the app
SELECT format(
               'ALTER DEFAULT PRIVILEGES FOR ROLE %I IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO %I',
               :'API_DDL_USER', :'API_DML_USER'
)\gexec

-- Ensure future sequences created by the migration role are accessible by the app
SELECT format(
               'ALTER DEFAULT PRIVILEGES FOR ROLE %I IN SCHEMA public GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO %I',
               :'API_DDL_USER', :'API_DML_USER'
)\gexec


-- Return to default root database
\connect postgres