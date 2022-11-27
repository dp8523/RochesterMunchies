---
geometry: margin=1in
---
# PROJECT Design Documentation

> _The following template provides the headings for your Design
> Documentation.  As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics._

## Team Information
* Team name: Better Monkeys
* Team members
  * Lucie Lim
  * Dara Prak
  * Jaden Seaton
  * Robert Huang
  * Adam Pang

## Executive Summary

A snack e-store where users can browse, purchase, and rate snacks. 
They can also view their purchase history just in case they loved
a snack but dont remember the name.

### Purpose
The purpose behind the project is to learn about the development 
process of a website from scratch. In this case we learned the 
basics for a e-store website with functions for the owner and 
customers, such as adding new products, changing info of the 
product, browsing products, and updating customers shopping 
cart.

### Glossary and Acronyms
> _Provide a table of terms and acronyms._

| Term | Definition |
|------|------------|
| SPA | Single Page |


## Requirements

This section describes the features of the application.

User authentication: Depending on username and password the 
user will have certain responsibilities, and features they 
are able to access. The owner will be able to add, remove 
and change info of a product. Customers will see their 
subscription, reviews, and browse snacks.

Shopping cart: Users will be able to add and remove 
stuff from their cart, and update it to their satisfaction.

Rating System: Users will be able to create a review 
of a snack that would show others how good a snack is 
worth buying. They are able to see the average rating 
of a snack along with individual ratings of others. 

Order History: users will be able to see their order 
history for every snack purchase they made. They can
see the date, time and what snacks they bought.


### Definition of MVP
A system where a user can make a snack, search for a 
specific snack, search for snacks given a word of the
snack, delete a snack, get all snacks, and update 
details of a snack.

### MVP Features
- Delete a single snack
- Update a single snack
- Create a new snack
- Get entire inventory
- Search for a snack
- Get a single snack

### Roadmap of Enhancements
- Order history
- Ratings & reviews for snacks


## Application Domain

This section describes the application domain.

![Domain Model](UpdatedDomainAnalysis.png)

This is a domain model of our snack e-store, it shows the relations
between the user, and the features of our website such as the 
shopping cart, where the customer can add and remove items from 
the cart. The model also displays the relationship between the 
owner and the inventory, adding, removing, and updating snacks.
Also it shows new features such as the subscription where the
customer can edit a subscription and change how frequently 
they can recieve snacks.


## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

The e-store web application, is built using the Model–View–ViewModel (MVVM) architecture pattern. 

The Model stores the application data objects including any functionality to provide persistance. 

The View is the client-side SPA built with Angular utilizing HTML, CSS and TypeScript. The ViewModel provides RESTful APIs to the client (View) as well as any logic required to manipulate the data objects from the Model.

Both the ViewModel and Model are built using Java and Spring Framework. Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the e-store application.

When a user first encounters our website they are presented with the homepage and 
a navigation bar with 4 buttons: Home, Catalog, Shopping cart and Login. Initially
the user is given the homepage to view. They are greeted with messages enticing them 
to shop and some snacks to browse. If they want to see more they can click on the 
catalog button and they are sent to a page where all the snacks are present, they 
can click on a snack and view the details of a snack as well. They can click on 
the shooping cart link in the nav bar however it will tell them to login first to 
access it. 

The login link will show 2 options one is for current buyers of the store
to login and a register button so that new users can sign up. Once a user signs up 
they now have access to the shopping cart and can add snacks to their shopping cart.
They can add snacks to their cart by going to the individual snack and clicking on
the add snack button and it will be sent to their shopping cart. They can change the 
contents of their shopping cart and checkout when they are satisfied with their 
order. 

As a owner they have access to different features when they log in. When 
an admin logins in they have access to the inventory, and they cannot see the shopping
cart. They can add, delete, and update a snack. When updating a snack they have to 
go to the individual snack and change the contents of a snack. When any user is done
with their session they can logout and the webpage changes back to the main 4 buttons,
and assumes guest access.


### View Tier
> _Provide a summary of the View Tier UI of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _You must also provide sequence diagrams as is relevant to a particular aspects 
> of the design that you are describing.  For example, in e-store you might create a 
> sequence diagram of a customer searching for an item and adding to their cart. 
> Be sure to include an relevant HTTP reuqests from the client-side to the server-side 
> to help illustrate the end-to-end flow._

![Successfully Add to Cart Sequence Diagram](AddToCartSequenceDiagram.png)
![Create a Snack Sequence Diagram](Create_a_snack_sequence_diagram.png)

The main component that holds all the sub components together is the app component and it
holds the navigation bar along with all the features of the website together. It allows for a 
central domain for where all users, buyers, and owners can access the website and its features.

Within the app component is the homepage which is the first thing a user will see upon 
entering the website. It serves as a welcoming message to the user and it allows them to see a sample
of snacks in the inventory. Entices them to stay and see more about the website. 

The login componenet restricts certain actions and features from different users. Owners who log in as admin
are able to access inventory of the snacks, they can add, edit, and delete snacks, howvever they are
unable to view/access their shopping cart. 

  As a buyer with an acconut they are able to view their cart,
  add items to their cart, remove items, edit the quantity of items, and check out. They are unable to 
  edit information about the snacks. 

  As a user who does not have an account on the website they cannot 
  have access to any of these features, all they can do is browse the store. If they want to buy an item
  they can register an account and they are allowed to have access to a buyers features. 

  This switch is made due to multiple callings sent to the buyer controller from the login component.
  Once the user had entered their username, the login component will then go the auth-service file to 
  the user service file and then that would send an http request of GET along with the username to the
  buyer controller where it would process the request and send back info if the process was fullfilled
  and if the user is confirmed. If the process was fulfilled an http resposne would be status code OK (200),
  and the user would be directed to the appropriate page with limited features.

When a buyer wants to browse the snacks they are able to when they click on the catalog link.
They are given all the snacks in a list and they can search for a snack when they type a key word
and several snack options will appear. This is due to the Http Request of GET along with the the
name of the snack sent from the snack service file to the snack controller file. 

Once a buyer clicks on a snack to view they are routed to that snack details page. If they desire that snack they click 
add to cart and that calls the addToCart method from the snack-detail componenet to the user service file.
The user service file will send a Http request of PUT along with the username of the buyer, and the id if the snack to 
the BuyerController. The BuyerController file will process the demand in the Buyer and Snack DAO files and send
a response status of OK (200) and an updated buyer cart if successful. If the buyer feels they want to browse more they 
can click the go back button. 

By clicking the shopping cart link in the navigation bar buyers are sent to a separate page 
where they can view the items they added to their cart. Automatically the quantity of each item in the cart is 1 but this can be 
changed on the buyers discrection. 

  To increase or decrease the number of each snack in the cart, or even remove the item the buyer will click on either the + or 
  button and that will call either the addCart or deleteCart method from the shopping cart component to the user service file.
  The file will then send a http request of either PUT or DELETE along with the the buyers username and the snack's id to the 
  Buyer controller. If successfull a status code of OK (200) along with the updated buyer's shopping cart will be sent to the component.
  Buyers can buy as many snacks as they want however they cannot buy more than the snacks stock quantity. Based on how many
  snacks a buyer has in the cart the total cost will fluxuate, and so will the indivdual prices
  for each snack. Once a buyer is satisfied they can click checkout and a success purchase will
  be made.

When an admin is logged in they are limited to viewing only the inventory, homepage, and login.
In the iventory the admin side they are able to view all the snacks in the e-store. They can also search for 
specific snacks using a search bar. This process is similar to the buyers search however the snacks that appear
can be edited. They can edit all the information of a snack and save it. The updated information will be shown 
to current and future buyers once this change is made. Admins can also can add a new snack or delete an entire 
snack from the the website.

  By filling out all the information boxes in the add snack section and click add snack this will make the inventory
  component call the add method in the snack service file. The method will create a snack object and send a http request 
  of POST with that snack object to the SnackController. The snack controller will send a createSnack method
  to the SnackDAO file to update the JSON file that contains all the snacks in the store. A response entity will
  be sent back containing a status code of OK (200) and the new snack if successfull. The new snack will be sent to the inventory
  of the owner and to the buyers side.

  To delete a snack the admin can click on a button below the snack with an x mark and that will call the delete method
  in the inventory component. That will call the deleteSnack method in the user service which will send a Http request of DELETE
  followed by the id of the snack to the SnackController. The SnackController will then call the deleteSnack method, processes
  the request and then return a resposne entity of OK(200) to the front.



### ViewModel Tier
> _Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class diagrams) with some details such as critical attributes and methods._

![ViewModel Controllers](ViewModel_Controllers.png)

### Model Tier
> _Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class diagrams) with some details such as critical attributes and methods._

![Model Buyer UML Diagram](Model_Buyer_UML_Diagram.png)
![Model ShoppingCart UML Diagram](Model_ShoppingCart_UML_Diagram.png)
![Model Snack UML Diagram](Model_Snack_UML_Diagram.png)

### Static Code Analysis/Design Improvements
Our Buyer implementation files have not been covered yet, and should the project continue, 
it would be in our best interest to focus on the buyer files and testing each individual
function. Testing these functions would allow us to continue developing the shopping
cart files and implement an organized method for handling our data persistence for 
buyers. 
Design improvements would lie primarily in the shopping cart files, as our code
analysis shows that we have malfunctioning methods that need to be revised. 
  - We can focus on improving our shopping cart by implementing simpler
  data structures and elaborating in the future into a more abstract class.

> _With the results from the Static Code Analysis exercise, 
> discuss the resulting issues/metrics measurements along with your analysis
> and recommendations for further improvements. Where relevant, include 
> screenshots from the tool and/or corresponding source code that was flagged._

## Testing
> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing
Passed All Criteria: 27
Passed Some Criteria: 0
Passed No Criteria: 2
The main issue with acceptance criteria testing is that we haven't
completed the frontend of the 10% features for out product.
There is 0 passed com criteria because we split up the frontend
and backend parts of features and right now only the front end of
the 10% features havent been completed so there is 0 for some
criteria passed.
On the backend, a lot of our stories passed all the criteria.

### Unit Testing and Code Coverage
Our unit testing strategy is primarily in creating a fake database
of snacks and using that fake database to assert various functions
and conditional statements. The code coverage achieved from unit 
testing our snack files have shown to be above 90% covered, where
the final 10% lie in additional functions yet to be covered and will
be our primary focus for code coverage. 
![api controller coverage](api_controller_coverage.png)
![api model coverage](api_model_coverage.png)
![api persistence coverage](api_persistence_coverage.png)
The teams code coverage is finished with the mvp and 10% for the backend.

