RETAIL SHOP MANAGEMENT SYSTEM

Student Name: Praveen J
Student ID: CODCSD253F-009
Batch:DCSD25.3F
Module: Enterprise Application Development

FEATURES
- Product, customer and employee management
- Sales and cash billing
- Stock entry
- Dashboard statistics
- Sales report filtered by date

DATABASE SETUP
1. Install and start MySQL Server 8.0.
2. Open MySQL Workbench using an administrator account.
3. Open Database/retail_shop.sql using File > Open SQL Script.
4. Execute the script to create the database and import its data.

APPLICATION DATABASE ACCOUNT
Run the following SQL as a MySQL administrator.
Replace YOUR_PASSWORD with a password of your choice.

CREATE USER IF NOT EXISTS 'retail_app'@'localhost'
IDENTIFIED BY 'YOUR_PASSWORD';

ALTER USER 'retail_app'@'localhost'
IDENTIFIED BY 'YOUR_PASSWORD';

GRANT SELECT, INSERT, UPDATE, DELETE
ON retail_shop.*
TO 'retail_app'@'localhost';

PASSWORD CONFIGURATION
1. Open Windows:
   "Edit environment variables for your account"
2. Create a user variable:
   Name: RETAIL_DB_PASSWORD
   Value: the password chosen for retail_app
3. Close and reopen any terminal used to launch the application.
   Sign out and back in if the application cannot read the variable.

RUN THE WINDOWS APPLICATION
1. Ensure MySQL is running on 127.0.0.1, port 3306.
2. Open Application/RetailShopManagement.
3. Double-click RetailShopManagement.exe.
4. Keep the app and runtime folders beside the executable.

RUN THE JAR
1. Install Java 21 or later.
2. Keep the lib folder beside RetailShopManagement.jar.
3. Open a terminal in the JAR folder.
4. Run:
   java -jar RetailShopManagement.jar

OPEN THE SOURCE
1. Install a JDK 21 or later and Apache NetBeans.
2. Select File > Open Project.
3. Select the Source folder containing pom.xml.
4. Allow Maven to download dependencies.
5. Clean and Build, then Run Project.

REPORTS
- Open Reports from the dashboard.
- Enter dates in yyyy-MM-dd format.
- Click View Report.
- The ending date is included.

KNOWN LIMITATIONS
- MySQL requires separate installation and setup.
- Database connection settings use localhost and port 3306.
- Payments support cash only.
- Employee roles are recorded but do not enforce access permissions.
- Open feature windows may need reopening to refresh their data.