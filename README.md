# My Bookcase Spring Boot Web App + Server
The web application that allows the user to manage his bookcase.</br>
The application supports CRUD operations for an entity. 

## Overview
User authorization is performed using the Spring Security framework. User login must be unique. Otherwise, an appropriate message will be displayed. 
Each application form is properly validated.

<p align="center">
<img src="https://github.com/kornasiowa/MyBookcase_SpringBoot/blob/master/screenshots/1.png" width="90%">
</p>
<p align="center">
<img src="https://github.com/kornasiowa/MyBookcase_SpringBoot/blob/master/screenshots/3.png" width="90%">
</p>

After logging in, the user has access to the list containing the data of his books that can be modified or deleted.
In the book list, items rated 4 and above are marked with a blue background. 
When adding and editing books, additional security was applied to prevent the user from adding two identical books.

<p align="center">
<img src="https://github.com/kornasiowa/MyBookcase_SpringBoot/blob/master/screenshots/5.png" width="90%">
</p>
<p align="center">
<img src="https://github.com/kornasiowa/MyBookcase_SpringBoot/blob/master/screenshots/7.png" width="90%">
</p>

The user can change the first name, last name, password of the account or delete the account completely.

<p align="center">
<img src="https://github.com/kornasiowa/MyBookcase_SpringBoot/blob/master/screenshots/8.png" width="90%">
</p>

The application contains protection that prevent the modification of data by persons who are not the owner of the book (e.g. deletion via url address).

Front-end was created with the use of Thymeleaf.

## Tools
- Spring Boot
- Spring Security
- H2 Database
- Lombok
- Thymeleaf + Bootstrap
