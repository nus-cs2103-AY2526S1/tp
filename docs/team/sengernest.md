---
layout: page
title: Sengernest's Project Portfolio Page
---

### Project: ExcoLink

ExcoLink is a desktop application designed to help the executive committee (exco) of large university clubs manage their members efficiently. With many sub-committees and members holding different roles, keeping track of contact details, sub-committee assignments, and participation records can be challenging. ExcoLink streamlines this process by providing a quick and reliable way to retrieve and manage member information.
The app features a Command Line Interface (CLI) for fast, precise input, alongside a Graphical User Interface (GUI) powered by JavaFX for ease of use. Written in Java, ExcoLink has a codebase of around 10,000 lines of code (kLoC).

Given below are my contributions to the project.

* *New Feature*: Added the ability to detect duplicate members
    * What it does: Prevents the user from adding a member that already exists in the address book (based on name and other identifying fields). If the user attempts to add a duplicate, the system will reject the command and display a warning.
    * Justification: This feature improves the product significantly because duplicate records can cause confusion and inconsistencies in managing member information. By preventing duplicates, the app ensures data integrity and saves the exco time when retrieving member details.
    * Highlights: This enhancement required modifying the add command to include duplicate checks, as well as updating the storage and model layers to enforce uniqueness. It affects existing features (like adding and editing members) and lays the groundwork for future features that depend on accurate member records.
    * Credits: {mention here if you reused any code/ideas from elsewhere or if a third-party library is heavily used in the feature so that a reader can make a more accurate judgement of how much effort went into the feature}

* *Code contributed*: [RepoSense link]()

* *Project management*:
    * Managed releases v1.3 - v1.5rc (3 releases) on GitHub

* *Enhancements to existing features*:
    * Updated the GUI color scheme (Pull requests [\#33](), [\#34]())
    * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* *Documentation*:
    * User Guide:
        * Added documentation for the features delete and find [\#72]()
        * Did cosmetic tweaks to existing documentation of features clear, exit: [\#74]()
    * Developer Guide:
        * Added implementation details of the delete feature.

* *Community*:
    * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
    * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
    * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
    * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* *Tools*:
    * Integrated a third party library (Natty) to the project ([\#42]())
    * Integrated a new Github plugin (CircleCI) to the team repo

* {you can add/remove categories in the list above}