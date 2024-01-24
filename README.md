# Security Management System

## A simple application to simulate a cybersecurity management system

This application will allow for the managers and engineers at a cybersecurity company to be able to manage the firms 
that employ them. The security company will be able to add/remove companies that hire them, view the companies that 
currently employ them, and sort the companies based on their risk ratings. This application can help with organization 
and for an easy overhead view of all the companies they manage. This project is of interest for me because I've been 
interested in cybersecurity for as long as I can remember and I plan to have a career in cybersecurity in the future. 

## User Stories:
- As a user, I want to be able to ***view*** a list of all the companies that I manage security for, and have the 
ability to view *only* companies with a specific risk rating, or *all* companies sorted by their risk rating in 
descending order.
- As a user, I want to be able to ***view*** the information (name, risk rating, etc.) of a company that I 
manage.
- As a user, I want to be able to ***add*** new companies to my list of companies and give them a name, a specification 
of the type of industry they work in and a risk rating of high, medium, or low, based on how likely they are
  to get attacked. 
- As a user, I want to be able to ***remove*** companies from the list of companies that I manage.
- As a user, I want to be able to ***change*** the name, risk rating, or industry of the companies in the list.
- As a user, I want to be able to choose to ***save*** all the companies that I have added into my security console into
 a file that I specify.
- As a user, I want to be able to choose to ***load*** a saved list of companies into the application.
  - If a company that is being added from the file already exists, I want to be able to skip it and still load the rest
  of the companies in the file.


## Instructions for Grader:
- You can generate the first required action related to adding X's to Y's by using the respective name, industry, and 
risk rating fields under the "Add a Company" label and clicking the "Add Company" button.
  - *Note:* Risk rating must be either "low", "medium", or "high".
- You can generate the second required action related to adding Xs to a Y by clicking the "View Companies" button
under the "View Companies" label.
  - You may also choose to sort the companies by risk rating from high to low by clicking the "View Sorted Companies" 
  button.
  - As well, you may choose to view only companies with a certain risk rating by using the drop-drop menu under the
  "View Companies" label and clicking the "View Risk Companies" button beside it.
- You can locate the visual component by looking at the main screen of the
  console. It's the image with green text that reads: "Cybersecurity Console".
  I used a text to PNG converter tool to transform the text to an image and added it as a splashscreen.
- You can save the state of my application by locating the "Menu" bar at the top-left of the application and clicking 
the "Save" button. You may then choose to name the file as you like, and it will automatically save it to the ./data
folder, as the save file dialog will automatically open to this folder.
- You can load the state of my application by locating the "Menu" bar at the top-left of the application and clicking
  the "Load" button and choosing a file
  - *Note:* The load file dialog will automatically open the ./data folder to choose files from.
- You can remove a company from the state of the application by choosing the name of the company from the drop-down menu
under the "Remove a Company" label in the middle of the screen, and then clicking the "Remove Company" button.

## Phase 4: Task 2:
```
Event Log:

Thu Nov 23 22:00:59 PST 2023
Company 'Apple' added.

Thu Nov 23 22:00:59 PST 2023
Company 'Adidas' added.

Thu Nov 23 22:00:59 PST 2023
Companies loaded from file: testWriterGeneralCompanies.json

Thu Nov 23 22:01:03 PST 2023
Company 'Nike' added.

Thu Nov 23 22:01:11 PST 2023
Viewed all companies with a medium risk rating

Thu Nov 23 22:01:12 PST 2023
Viewed all companies with a low risk rating

Thu Nov 23 22:01:13 PST 2023
Viewed all companies.

Thu Nov 23 22:01:15 PST 2023
Viewed the sorted list of companies.

Thu Nov 23 22:01:18 PST 2023
Company 'Adidas' removed.

Thu Nov 23 22:01:21 PST 2023
Company 'Apple' removed.

Thu Nov 23 22:01:23 PST 2023
Viewed all companies with a high risk rating
```

## Phase 4: Task 3:

If I had more time to work on my project, I would refactor a few things:
- First I would refactor the JSON classes (JsonReader and JsonWriter) to ... ??
- I would also refactor ButtonManager and MenuManager  to extend the GraphicImplementation class.
  - This would be helpful because instead of both of the classes having a field of GraphicImplementation, they can
  simply use the methods provided in GraphicImplementation.

## Phase 4: Task 3:

If I had more time to work on my project, I would refactor a few different things:
- First I would refactor my JSON classes (JsonReader and JsonWriter) to extend the CompanyManager class.
  - This would be beneficial as it would reduce coupling within my program. It would also be beneficial 
  because both the classes use the list of companies that the CompanyManager class has, and instead of passing it as an
  argument and letting the CompanyManager class handle it, they could directly modify the list.
- I would also refactor ButtonManager and MenuManager to extend the GraphicImplementation class.
  - This would be helpful because instead of both the classes having a field of GraphicImplementation (and vice 
  versa) and calling methods on its object, they could simply use the methods provided in GraphicImplementation. This would also reduce coupling within
  the program and would help clean up my code. 

Both of these refactoring changes would help to reduce coupling within my program, create a more hierarchical 
and systematic design of my program, reduce the amount of code (and possibly code duplication), and help with the
debugging of my application. Another change I could make is to better follow the single-responsibility rule by 
introducing more classes in my project and better delegating the different tasks. I could also refactor the
companyManager class to better follow the singleton pattern as my application is already using only one instance of it.