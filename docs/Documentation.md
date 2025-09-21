---
  layout: default.md
  title: "Documentation guide"
  pageNav: 3
---

# Documentation Guide

* We use [**MarkBind**](https://markbind.org/) to manage documentation.
* The `docs/` folder contains the source files for the documentation website.
* To learn how set it up and maintain the project website, follow the guide [[se-edu/guides] Working with Forked MarkBind sites](https://se-education.org/guides/tutorials/markbind-forked-sites.html) for project documentation.

## Editing Markbind Documentation

### Content Files
* Edit any `.md` file directly to update content
* Changes are automatically deployed via GitHub Actions when merged to master
* Use standard Markdown syntax for formatting

### Adding New Pages
* Create new `.md` files in the `docs/` folder
* Add them to the navigation by editing `site.json` under the `siteNav` section

### File Organization
* `images/` - Add screenshots, diagrams, and icons here
* `team/` - Individual team member profile pages
* `diagrams/` - Architecture and technical diagrams
* Main documentation files are in the root `docs/` folder

### Referencing Assets
* Images: `![description](images/filename.png)`
* Links to other pages: `[Page Title](PageName.html)`
* Internal sections: `[Section](#section-header)`

**Style guidance:**

* Follow the [**_Google developer documentation style guide_**](https://developers.google.com/style).
* Also relevant is the [_se-edu/guides **Markdown coding standard**_](https://se-education.org/guides/conventions/markdown.html).


**Converting to PDF**

* See the guide [_se-edu/guides **Saving web documents as PDF files**_](https://se-education.org/guides/tutorials/savingPdf.html).
