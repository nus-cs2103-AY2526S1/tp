---
layout: page
title: Setting up and getting started
---

* Table of Contents
{:toc}


--------------------------------------------------------------------------------------------------------------------

## Setting up the project in your computer

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**

Follow the steps in the following guide precisely. Things will not work out if you deviate in some steps.
</div>

First, **fork** this repo, and **clone** the fork into your computer.

If you plan to use Intellij IDEA (highly recommended):
1. **Configure the JDK**: Follow the guide [_[se-edu/guides] IDEA: Configuring the JDK_](https://se-education.org/guides/tutorials/intellijJdk.html) to ensure Intellij is configured to use **JDK 17**.
1. **Import the project as a Gradle project**: Follow the guide [_[se-edu/guides] IDEA: Importing a Gradle project_](https://se-education.org/guides/tutorials/intellijImportGradleProject.html) to import the project into IDEA.<br>
  :exclamation: Note: Importing a Gradle project is slightly different from importing a normal Java project.
1. **Verify the setup**:
   1. Run the `seedu.address.Main` and try a few commands.
   1. [Run the tests](Testing.md) to ensure they all pass.

--------------------------------------------------------------------------------------------------------------------

## Before writing code

1. **Configure the coding style**

   If using IDEA, follow the guide [_[se-edu/guides] IDEA: Configuring the code style_](https://se-education.org/guides/tutorials/intellijCodeStyle.html) to set up IDEA's coding style to match ours.

   <div markdown="span" class="alert alert-primary">:bulb: **Tip:**

   Optionally, you can follow the guide [_[se-edu/guides] Using Checkstyle_](https://se-education.org/guides/tutorials/checkstyle.html) to find how to use the CheckStyle within IDEA e.g., to report problems _as_ you write code.
   </div>

1. **Set up CI**

   This project comes with a GitHub Actions config files (in `.github/workflows` folder). When GitHub detects those files, it will run the CI for your project automatically at each push to the `master` branch or to any PR. No set up required.

1. **Learn the design**

   When you are ready to start coding, we recommend that you get some sense of the overall design by reading about [AddressBook’s architecture](DeveloperGuide.md#architecture).

1. **Do the tutorials**
   These tutorials will help you get acquainted with the codebase.

   * [Tracing code](https://se-education.org/guides/tutorials/ab3TracingCode.html)
   * [Adding a new command](https://se-education.org/guides/tutorials/ab3AddRemark.html)
   * [Removing fields](https://se-education.org/guides/tutorials/ab3RemovingFields.html)

--------------------------------------------------------------------------------------------------------------------

## Setting up the Jekyll documentation website

If you want to work on the project documentation website (this website), you'll need to set up Jekyll locally.

### Prerequisites

Before setting up Jekyll, ensure you have Ruby installed on your system:

```bash
ruby --version
```

If Ruby is not installed, install it using your system's package manager.

### Installation Steps

1. **Install Ruby development headers and build tools** (required for compiling native gems):

   **On Ubuntu/Debian:**
   ```bash
   sudo apt update
   sudo apt install ruby-dev build-essential
   ```

   **On macOS:**
   ```bash
   # If using Homebrew
   brew install ruby
   # Development tools should already be available via Xcode Command Line Tools
   ```

   **On Windows:**
   Install Ruby through [RubyInstaller](https://rubyinstaller.org/) with the DevKit.

2. **Navigate to the docs directory:**
   ```bash
   cd docs
   ```

3. **Install Bundler** (if not already installed):
   ```bash
   gem install bundler
   ```

4. **Install Jekyll and dependencies:**
   ```bash
   bundle install
   ```

   <div markdown="span" class="alert alert-info">:information_source: **Note:**
   
   If you encounter permission errors, you can install gems locally instead of system-wide:
   ```bash
   bundle install --path vendor/bundle
   ```
   </div>

### Running the Jekyll Site Locally

1. **Start the Jekyll server:**
   ```bash
   bundle exec jekyll serve
   ```

2. **Access the website:**
   Open your web browser and navigate to `http://localhost:4000` or `http://127.0.0.1:4000`

3. **Live reloading:**
   The Jekyll server will automatically reload when you make changes to the documentation files.

### Troubleshooting

**Error: `mkmf.rb can't find header files for ruby`**
- This means you need to install Ruby development headers. Follow step 1 in the installation instructions above.

**Error: `Permission denied` when installing gems**
- Use `bundle install --path vendor/bundle` to install gems locally instead of system-wide.
- Alternatively, use a Ruby version manager like `rbenv` or `rvm` to manage Ruby installations in your user directory.
