# College Room Allotment System (demo)

## Setup
1. Create MySQL database & import schema:
   - Edit `database.sql` if needed, then run:
     ```
     mysql -u root -p < database.sql
     ```
2. Edit DB credentials in `src/db/DatabaseConnection.java` (USER and PASS).
3. Ensure `mysql-connector-java` jar is in `lib/` and on the classpath.
4. Compile & run:
(On Windows use `;` instead of `:`)

## Notes
- This is a demo. Passwords are stored plaintext in DB — **do not** use in production.
- Faculty change requests / approval flow can be extended.
# College Room Allotment System

Java + MySQL + AWT based project.
