# `📲 appman`
> ### Simple CLI App Manager for macOS that simplifies installing and deleting apps with their data.

## What is appman?
appman is an app manager for macOS. Its purpose is to help automate the process of properly uninstalling external apps.

Normally, you have to delete the .app file, and then delete the app's data files manually.

With appman, all you have to do is use the command line.

## Installation
To install appman, execute the following in your Terminal:

```sh
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/blueysh/appman/HEAD/install.sh)"
```

## Managing Apps
To install or uninstall an app, execute the following in your Terminal:

```sh
appman [install | uninstall] [app id]
```

You can also use the `i` and `u` aliases in place of `install` and `uninstall` if you desire.

## Available Apps
These are the apps available to manage on appman:

- `sublime_text`: Sublime Text by Sublime Text Team
- `hyper`: Hyper by Vercel
- `discord`: Discord by Discord Inc.
