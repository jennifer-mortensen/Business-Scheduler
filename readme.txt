Jennifer Mortensen
jennifer.erin.mortensen@gmail.com

Business Scheduler 1.01 / July 9 2021 Release
Created in Apache NetBeans IDE 12.3 Uses Jave SE 11.0.11 with javafx-sdk-11.0.2 and the the mysql-connector-java-8.0.22 driver.

PURPOSE
Database-driven application that allows the user to manage appointment schedules for a business. Allows the user to browse customers and appointments, as well as add new customers and appointments to the database. Business contacts business location information (such as countries and first level division data), as well as new users must be prepopulated within the database for use with this application.

REPORTS
This application also contains a reports pane which outputs data on appointments by month, contact schedules, and monthly additions by user. The monthly additions by user report allows the user to collect metadata about which users have added new customers and appointments to the database.

USEAGE
The application can be run from the command line in the dist folder with the following:

java -jar "Business_Scheduler.jar" 

To log in, please either input a valid user from the database or use the account 'test' (password: 'test') and the 'ENABLE_TEST_ACCOUNT' flag enabled in the login form. Please note that testing done under this account will not be reported under the reports tab, as the test flag does not generate a real account. All entries added to the database under this test account will be coupled with a NULL username.

(Note: Strictly for this evaluation release, I have disabled the test account flag and instead populated a test account directly into the database. This should allow the evaluator to interface with the reports form more directly.)

Please note that all login attempts are stored in the file login_activity.txt in the root directory.
