<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
</head>
<body>
    <header>
        <div class="container">
            <h1>ShopFinder</h1>
        </div>
    </header>
    <div class="container">
        <div class="content">
            <h2>Project Overview</h2>
            <p>
            This project is developed as a semester project for the Data Base course. It implements ShopFinder that is an Android application . The application is designed to help users efficiently find and manage shops located in various visiting places. Users can search for shops either by their name or by the name of the visiting palace. For example, users can find all shops named <strong>Bata</strong> or all shops located in <strong>Anarkali</strong>.
        </p>
        <p>
            The application supports two types of accounts:
        </p>
        <ul>
            <li><strong>Shopkeepers:</strong> They can add multiple shops to specific visiting places, manage and edit shop details, and view information about their shops.</li>
            <li><strong>Users:</strong> They can search for shops, view detailed information about each shop, rate them, and leave comments.</li>
        </ul>
        <p>
            The application includes features for managing shop listings and searching for shops based on various criteria.
        </p>

<h2>Database Handler</h2>
<p>
    A key component of this project is the <code>DBHandler</code> class, which manages all database operations within the application. The <code>DBHandler</code> class extends <code>SQLiteOpenHelper</code> and plays a crucial role in the application's functionality by handling database creation, schema management, and data operations.
</p>

<h3>Key Features of <code>DBHandler</code>:</h3>
<ul>
    <li><strong>Database Creation:</strong> The <code>onCreate</code> method initializes the database schema by creating tables for shops and visiting places. It sets up essential constraints and relationships between tables to ensure data integrity.</li>
    <li><strong>Database Upgrades:</strong> The <code>onUpgrade</code> method handles schema changes and upgrades. It ensures that existing data is preserved and appropriately migrated when the database schema evolves.</li>
    <li><strong>Data Insertion:</strong> Methods such as <code>insertIntoVisitingPlaces</code> and <code>insertShop</code> are responsible for adding new records to the database. These methods validate input data and handle any necessary checks before inserting new entries.</li>
    <li><strong>Data Retrieval:</strong> The class includes methods like <code>getAllShops</code> and <code>getShopByName</code>, which retrieve data based on specific criteria. This functionality allows users and shopkeepers to query the database efficiently.</li>
    <li><strong>Data Management:</strong> The <code>DBHandler</code> class provides methods for updating and deleting records, ensuring that changes to shop or visiting place details are reflected in the database seamlessly.</li>
</ul>
<p>
    Overall, the <code>DBHandler</code> class is central to the ShopFinder application, facilitating robust interaction with the SQLite database and maintaining the integrity and consistency of the application's data.
</p>

<h2>Screens and Their Functions</h2>

<h3>Main Screen</h3>
<p>
    The main screen is the entry point of the application. It provides options for existing users or shopkeepers to log in to their accounts and for new users or shopkeepers to create an account.
</p>

<h3>Sign Up Screen</h3>
<p>
    The sign-up screen enables new users or shopkeepers to register for an account by entering their username, email address, phone number, and password.
</p>

<h3>Visiting Palace Screen</h3>
<p>
    This screen allows owners to add new visiting places. Required details include the place name, location, city, and type. This information is essential for shopkeepers to associate their shops with specific visiting places.
</p>

<h3>Forget Password Screen</h3>
<p>
    The forget password screen assists users or shopkeepers in resetting their passwords if they forget them. Users need to enter their phone number to receive a password reset link.
</p>

<h3>New Password Screen</h3>
<p>
    After providing the correct phone number, users or shopkeepers are directed to this screen to enter and confirm their new password to regain access to their account.
</p>

<h3>User Screen</h3>
<p>
    The user screen serves as the main dashboard for regular users. It displays a list of favorite shops that the user has saved previously. Users can search for shops, view details of their saved shops, and manage their profile information.
</p>

<h3>User Save Shop Detail Screen</h3>
<p>
    This screen displays details of shops that the user has saved, including shop name, location, and type. Users can check comments and ratings from other users, providing easy access to their favorite shops.
</p>

<h3>User Search Shop Screen</h3>
<p>
    Users can search for shops by name or visiting place. This screen includes a search bar that acts as a filter to help users find specific shops.
</p>

<h3>User Search Shop Detail Screen</h3>
<p>
    After a search, users are directed to this screen to view detailed information about the selected shop, including shop details, ratings, and comments from other users. Users can also save the shop to their list of favorites from this screen.
</p>

<h3>Shopkeeper Screen</h3>
<p>
    The shopkeeper screen is for shopkeepers who manage and add shops. It allows shopkeepers to add new shops, edit existing shop details, and view information about their shops.
</p>

<h3>Shop Detail Screen</h3>
<p>
    This screen shows detailed information about a specific shop, including the shop’s name, location, and other relevant details. Shopkeepers can view and update this information.
</p>

<h3>Edit Shop Detail Screen</h3>
<p>
    Shopkeepers use this screen to edit the details of their shops, including updating the shop name, location, and type.
</p>

<h3>Add Shop Screen</h3>
<p>
    The add shop screen allows shopkeepers to add new shops to the system by providing details such as shop name, number, street name, city, area, type, and the associated visiting place.
</p>

<h2>Database Handler</h2>
<p>
    The <strong>DBHandler</strong> class is a critical component of the ShopFinder application, handling all database operations. It extends <code>SQLiteOpenHelper</code> and manages the creation and updating of the SQLite database used by the application.
</p>
<p>
    <strong>Key Responsibilities of DBHandler:</strong>
</p>
<ul>
    <li><strong>Database Creation:</strong> Defines the structure of the database, including tables and columns.</li>
    <li><strong>Data Insertion:</strong> Provides methods to insert new records into the database, such as adding new shops and visiting places.</li>
    <li><strong>Data Retrieval:</strong> Implements methods to query and retrieve data from the database, such as fetching shop details and user comments.</li>
    <li><strong>Data Update:</strong> Allows updating existing records, such as editing shop details.</li>
    <li><strong>Data Deletion:</strong> Provides functionality to delete records from the database when necessary.</li>
</ul>
<p>
    The <code>DBHandler</code> class ensures data integrity and provides a structured way to interact with the application's database. It is crucial for managing the data associated with shops, visiting places, user comments, and ratings.
</p>

<h2>Running the Application</h2>
<p>
    To run the ShopFinder application on your local machine, follow these steps:
</p>
<ol>
    <li>Create a new Android Studio project on your machine.</li>
    <li>Download the layout XML code and Java code from the repository.</li>
    <li>Paste the files into your project, including the <code>AndroidManifest.xml</code> file that contains details of all screen activities.</li>
    <li>Ensure that you have an Android emulator set up or connect an Android device to your computer.</li>
    <li>Click the "Run" button in Android Studio to build and run the application on your device.</li>
    <li>To test the application, use the provided APK file. Transfer the APK to your Android device and open it to install.</li>
</ol>

<h2>Files Included</h2>
<ul>
    <li><strong>APK File:</strong> The compiled APK file for installing the application on an Android device.</li>
    <li><strong>AndroidManifest.xml:</strong> Contains essential app configuration details, including information about all screen activities.</li>
    <li><strong>Source Code:</strong> Includes the Java code and XML layout files for the application.</li>
</ul>
</div>
</div>
<footer>
</footer>
</body>
</html>
