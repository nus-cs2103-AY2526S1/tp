---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# FAContactsPro Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S1-CS2103-F10-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2526S1-CS2103-F10-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

**Characteristics**:
* Prefers desktop apps over other types
* Can type fast and prefers typing to mouse-based interactions
* Reasonably comfortable using CLI apps

**Behaviours**:
* Deals with a significant number of contacts everyday
* Frequently searches, filters and updates client contact information
* Values efficiency and speed in desktop apps over GUI interactions
* Needs to stay on top of a packed schedule and cannot afford to forget meetings or follow-ups with clients
* Needs to efficiently categorise and segment clients for targeted follow-ups e.g. tagging a customer as “priority-client”

**Pain Points**:
* High workload from organising, editing and sieving through contacts
* Needs constant reminders for client-related events
* Struggles to maintain a single source of truth for all client contact information

**Value proposition**:
As financial advisors deal with multitudinous leads every single day, our desktop application offers value by allowing advisors to efficiently manage large client databases, stay on top of a hectic schedule and streamline follow-ups through reminders and customisable client tagging. We help financial advisors save precious time spent on tedious administrative workload, so they can focus on building client relationships, making informed decisions and growing their business.

### User stories

Priorities: High (must have) - `1`, Low (unlikely to have) - `4`

| Priority | As a …​                | I want to …​                                                                                     | So that I can…​                                                                                                               |
|:--------:|------------------------|--------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------|
|   `1`    | financial advisor      | add a client's contact details (name, phone, email...)                                           | keep track of their information                                                                                               |
|   `1`    | financial advisor      | add a colleague's contact details (name, phone, email...)                                        | keep track of their information                                                                                               |
|   `1`    | financial advisor      | remove a contact                                                                                 | remove a contact when I no longer need it                                                                                     |
|   `1`    | financial advisor      | display all of my contacts that I saved                                                          | view all of my contacts                                                                                                       |
|   `1`    | financial advisor      | exit the application                                                                             | leave the application and do other stuff                                                                                      |
|   `2`    | financial advisor      | search for contacts by name                                                                      | retrieve the information of contacts that match my requirements                                                               |
|   `2`    | financial advisor      | search for contacts by phone                                                                     | retrieve the information of contacts that match my requirements                                                               |
|   `2`    | financial advisor      | search for contacts by email                                                                     | retrieve the information of contacts that match my requirements                                                               |
|   `2`    | financial advisor      | search for contacts by tags                                                                      | retrieve the information of contacts that match my requirements                                                               |
|   `2`    | financial advisor      | edit a contact's information                                                                     | make modifications when my contact's details changes                                                                          |
|   `2`    | financial advisor      | wipe out all of my contacts                                                                      | do a hard reset of the application                                                                                            |
|   `2`    | financial advisor      | sort my contacts ascendingly/descendingly based on different conditions (name, phone, email...)  | look through my contacts easier                                                                                               |
|   `2`    | financial advisor      | view upcoming meetings linked to a client's profile                                              | be prepared with the right context                                                                                            |
|   `2`    | financial advisor      | classify clients in accordance with their outreach stage (e.g. leads, opportunities, sales etc.) | better know what actionable steps are required to take for each of the different types of clients                             |
|   `2`    | financial advisor      | have the ability to access a single view for each client (with all their details/information)    | have an easier time understanding everything I need to know about a client at one glance                                      |
|   `2`    | financial advisor      | record and see all interactions for each client in one place                                     | have full context to make informed decisions and plan next steps for the client                                               |
|   `2`    | first-time user        | have a guidebook/document of all the functionalities available                                   | have an easier time integrating into and navigating the application                                                           |
|   `2`    | financial advisor      | search for client meetings based on dates                                                        | easily view the meetings i have for the particular date                                                                       |
|   `2`    | financial advisor      | view past meetups with a particular client                                                       | plan for future meetups                                                                                                       |
|   `2`    | financial advisor      | sort and record clients based on revenue brought in                                              | prioritize clients accordingly                                                                                                |
|   `2`    | first-time user        | upload / sync all my clients contacts directly                                                   | have easy access to their contacts and not have to add them individually                                                      |
|   `2`    | exiting advisor        | export my client details                                                                         | hand over clients to other advisors                                                                                           |
|   `3`    | financial advisor      | add custom notes to my contacts                                                                  | keep track of additional information about my contacts                                                                        |
|   `3`    | busy financial advisor | set reminders for follow-ups with clients                                                        | be reminded on them and not miss important check-ins                                                                          |
|   `3`    | financial advisor      | store related attachments/files for each contact                                                 | have all relevant documents easily accessible in one place                                                                    |
|   `3`    | financial advisor      | link related contacts together (link together families, business partners, referrals etc.)       | see networks to help with personalisation and gives context for better financial advice (family plans, joint investments etc) |
|   `3`    | seasoned user          | have the ability to select multiple contacts to update/set reminders at once                     | save time by managing multiple clients at once                                                                                |
|   `4`    | financial advisor      | sync the address book with my calender                                                           | keep track of meetings and plan for meetings accordingly                                                                      |
|   `4`    | financial advisor      | use the application offline                                                                      | plan for meetings without the need for network access                                                                         |
|   `4`    | financial advisor      | broadcast information to clients through email                                                   | update them accordingly with market news                                                                                      |
|   `4`    | financial advisor      | set up meeting invites through google / zoom                                                     | book the time of my clients in advance                                                                                        |

### Use cases

**System: FAContactsPro**

**Use case: UC01 - Add new client / colleague contact**

**Actor: User**

**MSS**

1. User types in command to add new contact.

2. FAContactsPro app adds the new contact to the list of contacts and displays a success message.

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests user to try again
  
    * 1a2. User enters a new command to add a new contact.
  
      Steps 1a1-1a2 are repeated until the command and details entered are correct.
  
      Use case resumes from Step 2.


* 1b. FAContactsPro is unable to update the contacts in the storage

    * 1b1. FAContactsPro requests for the user to key in the add contact command again

    * 1b2. The user types in the new command

      Steps 1b1 - 1b2 is repeated until the system is able to update the data in the storage without any errors.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC02 - Delete client / colleague contact**

**Actor: User**

**MSS**

1. User types in command to delete a contact.

2. FAContactsPro app deletes the contact from the list of contacts and displays a success message.

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests user to try again

    * 1a2. User enters a new command to add a new contact.

      Steps 1a1-1a2 are repeated until the command and details entered are correct.

      Use case resumes from Step 2.


* 1b. FAContactsPro is unable to update the contacts in the storage

    * 1b1. FAContactsPro requests for the user to key in the delete contact command again

    * 1b2. The user types in the new command

      Steps 1b1 - 1b2 is repeated until the system is able to update the data in the storage without any errors.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC03 - List all contacts**

**Actor: User**

**MSS**

1. User types in the list command

2. FAContactsPro displays the list of all contact

Use case ends.


**Extensions**

* 1a.  The system detects an error in the list command.

    * 1a1. FAContactsPro requests for the correct command.

    * 1a2. The user types in the new command.

      Steps 1a1-1a2 are repeated until the command entered is correct.

      Use case resumes from Step 2.


* 1b. FAContactsPro is unable to retrieve the contacts from the storage.

    * 1b1. FAContactsPro requests for the user to key in the list command again.

    * 1b2. The user types in the new command.

      Steps 1b1 - 1b2 is repeated until the system is able to retrieve the data from the storage without any errors.

      Use case resumes from Step 2.

* 1c. There are no contacts for FAContactsPro to retrieve.

    * 1c1. FAContactsPro displays no contacts saved message and informs the user that all contacts have been retrieved.

      Use case ends.

**System: FAContactsPro**

**Use case: UC04 - Search for contacts by Name**

**Actor: User**

**MSS**

1. User types in command to search contact by name.

2. FAContactsPro app filters the list of contacts matching the name and lists all contacts.

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests the user to try again.

    * 1a2. User enters a new command to search for contacts by name.

      Steps 1a1-1a2 are repeated until the command and details entered are correct.

      Use case resumes from Step 2.


* 1b. FAContactsPro is unable to retrieve the contacts from the storage.

    * 1b1. FAContactsPro requests for the user to key in the search contact command again.

    * 1b2. The user types in the new command.

      Steps 1b1 - 1b2 is repeated until the system is able to retrieve the data from the storage without any errors.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC05 - Search for contacts by Phone Number**

**Actor: User**

**MSS**

1. User types in command to search contact by phone number.

2. FAContactsPro app filters the list of contacts matching the phone number and lists all contacts.

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests the user to try again.

    * 1a2. User enters a new command to search for contacts by phone.

      Steps 1a1-1a2 are repeated until the command and details entered are correct.

      Use case resumes from Step 2.


* 1b. FAContactsPro is unable to retrieve the contacts from the storage.

    * 1b1. FAContactsPro requests for the user to key in the search contact command again.

    * 1b2. The user types in the new command.

      Steps 1b1 - 1b2 is repeated until the system is able to retrieve the data from the storage without any errors.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC06 - Search for contacts by Email**

**Actor: User**

**MSS**

1. User types in command to search contact by email.

2. FAContactsPro app filters the list of contacts matching the email and lists all contacts.

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests the user to try again.

    * 1a2. User enters a new command to search for contacts by email.

      Steps 1a1-1a2 are repeated until the command and details entered are correct.

      Use case resumes from Step 2.


* 1b. FAContactsPro is unable to retrieve the contacts from the storage.

    * 1b1. FAContactsPro requests for the user to key in the search contact command again.

    * 1b2. The user types in the new command.

      Steps 1b1 - 1b2 is repeated until the system is able to retrieve the data from the storage without any errors.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC07 - Search for contacts by Tag**

**Actor: User**

**MSS**

1. User types in command to search contact by tag.

2. FAContactsPro app filters the list of contacts matching the tag and lists all contacts.

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests the user to try again.

    * 1a2. User enters a new command to search for contacts by tag.

      Steps 1a1-1a2 are repeated until the command and details entered are correct.

      Use case resumes from Step 2.


* 1b. FAContactsPro is unable to retrieve the contacts from the storage.

    * 1b1. FAContactsPro requests for the user to key in the search contact command again.

    * 1b2. The user types in the new command.

      Steps 1b1 - 1b2 is repeated until the system is able to retrieve the data from the storage without any errors.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC08 - Edit a contact’s details**

**Actor: User**

**MSS**

1. User types in command to edit a contact’s details

2. FAContactsPro app successfully edits the contact’s details and displays a success message.

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests the user to try again.

    * 1a2. User enters a new command to edit a contact's details.

      Steps 1a1-1a2 are repeated until the command and details entered are correct.

      Use case resumes from Step 2.


* 1b. FAContactsPro is unable to retrieve the contacts from the storage.

    * 1b1. FAContactsPro requests for the user to key in the edit contact command again.

    * 1b2. The user types in the new command.

      Steps 1b1 - 1b2 is repeated until the system is able to retrieve the data from the storage without any errors.

      Use case resumes from Step 2.

* 1c. FAContactsPro is unable to update the contacts in the storage.

    * 1c1. FAContactsPro requests for the user to key in the edit contact command again.

    * 1c2. The user types in the new command.

      Steps 1c1 - 1c2 is repeated until the system is able to update the data from the storage without any errors.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC09 - Delete all contacts**

**Actor: User**

**MSS**

1. User types in command to delete all contacts from contacts list.

2. FAContactsPro app successfully deletes all contacts and displays a success message.

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests the user to try again.

    * 1a2. User enters a new command to delete all contacts.

      Steps 1a1-1a2 are repeated until the command and details entered are correct.

      Use case resumes from Step 2.


* 1b. FAContactsPro is unable to retrieve the contacts from the storage.

    * 1b1. FAContactsPro requests for the user to key in the delete all contacts command again.

    * 1b2. The user types in the new command.

      Steps 1b1 - 1b2 is repeated until the system is able to retrieve the data from the storage without any errors.

      Use case resumes from Step 2.

* 1c. FAContactsPro is unable to update the contacts in the storage.

    * 1c1. FAContactsPro requests for the user to key in the delete all contacts command again.

    * 1c2. The user types in the new command.

      Steps 1c1 - 1c2 is repeated until the system is able to update the data from the storage without any errors.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC10 - Exit FAContactsPro application**

**Actor: User**

**MSS**

1. User types in command to exit the application.

2. FAContactsPro app displays an exit message and closes the application.

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests the user to try again.

    * 1a2. User enters a new command to exit the application.

      Steps 1a1-1a2 are repeated until the command entered are correct.

      Use case resumes from Step 2.


* 1b. FAContactsPro is unable to save the current list of contacts to the storage before exit.

    * 1b1. FAContactsPro requests for the user to key in the exit command again.

    * 1b2. The user types in the new command.

      Steps 1b1 - 1b2 is repeated until the system is able to update and save the data in the storage without any errors.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC11 - Sort list of contacts by name**

**Actor: User**

**MSS**

1. User types in command to sort the contacts in contacts list by name

2. FAContactsPro app sorts the current list of contacts in the contacts list by name and displays the new sorted contact list.

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests the user to try again.

    * 1a2. User enters a new command to sort contacts by name.

      Steps 1a1-1a2 are repeated until the command entered is correct.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC12 - Sort list of contacts by tags**

**Actor: User**

**MSS**

1. User types in command to sort the contacts in contacts list by tags

2. FAContactsPro app sorts the current list of contacts in the contacts list by tags and displays the new sorted contact list.

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests the user to try again.

    * 1a2. User enters a new command to sort contacts by tags.

      Steps 1a1-1a2 are repeated until the command entered is correct.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC13 - Set Reminder for Contact**

**Actor: User**

**MSS**

1. User types in command to add reminder for a contact on a specific date

2. FAContactsPro app adds the reminder to the contact

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests the user to try again.

    * 1a2. User enters a new command to set reminder for contact with specific date

      Steps 1a1-1a2 are repeated until the command entered is correct.

      Use case resumes from Step 2.

* 1b.  FAContactsPro is unable to retrieve the specified contact from storage

    * 1b1. FAContactsPro app displays an error message and requests the user to try again.

    * 1b2. User enters a new command to set reminder for contact with specific date

      Steps 1b1-1b2 are repeated until the system is able to retrieve the contact from storage without any errors.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC14 - Adding custom notes to contact**

**Actor: User**

**MSS**

1. User types in command to add a custom note to contact

2. FAContactsPro app adds the custom note to the contact

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests the user to try again.

    * 1a2. User enters a new command to add custom note to contact

      Steps 1a1-1a2 are repeated until the command entered is correct.

      Use case resumes from Step 2.

* 1b.  FAContactsPro is unable to retrieve the specified contact from storage

    * 1b1. FAContactsPro app displays an error message and requests the user to try again.

    * 1b2. User enters a new command to add custom note to contact

      Steps 1b1-1b2 are repeated until the system is able to retrieve the contact from storage without any errors.

      Use case resumes from Step 2.

**System: FAContactsPro**

**Use case: UC15 - Viewing all upcoming meetings with contact**

**Actor: User**

**MSS**

1. User types in command to view all upcoming meetings with client

2. FAContactsPro app successfully displays all meetings associated with the specified client

Use case ends.


**Extensions**

* 1a.  FAContacts pro detects an error in the command entered.

    * 1a1. FAContactsPro app displays an error message and requests the user to try again.

    * 1a2. User enters a new command to  view all upcoming meetings with client

      Steps 1a1-1a2 are repeated until the command entered is correct.

      Use case resumes from Step 2.

* 1b.  FAContactsPro is unable to retrieve the specified contact/meetings from storage

    * 1b1. FAContactsPro app displays an error message and requests the user to try again.

    * 1b2. User enters a new command to add custom note to contact

      Steps 1b1-1b2 are repeated until the system is able to retrieve the contact/meetings from storage without any errors.

      Use case resumes from Step 2.

### Non-Functional Requirements

1. **Data performance**: the system should be able to hold up to 10000 contacts
2. **Cross Compatibility**: the system should be cross compatible across different operating systems (Mac, Windows etc.) as long as they have java 17
3. **Performance Requirements**: the system should respond within two seconds
4. **Quality requirements**: the system should be usable by a novice who has never used a contact management system.
5. **Security**: Data should only be accessible by the user of the device of the application
6. **Maintainability**: the code base should be modular, and follow a strict OOP structure
7. **Maintainability**: there should be clear documentations on the code base
8. **Scalability**: the application design should allow for new features without large architectural changes
9. **Data persistence**: Contact details should be saved automatically after every modification.
10. **Compliance**: the system should comply with the relevant data protection regulations if handling personal information

### Glossary
* **Client / Contact**: Refers to an individual whose details are stored in the system. Each client/contact record includes attributes such as name, phone number, email, and tags.
* **Reminder**: A scheduled notification linked to a client or event that alerts the user of upcoming tasks, meetings, or follow-ups.
* **Command**: A typed instruction entered into the system’s CLI (e.g. add, delete, list) to perform an action.
* **Validation**: Rules applied to user inputs (e.g., regex checks for email or phone numbers) to ensure data consistency and prevent errors.
* **Storage / Contact List**: The collection of all stored contact records within the system.
* **System**: Refers to FAContactsPro, the contact management desktop application being designed.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
