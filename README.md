# `📲 appman`
> ### Simple CLI App Manager for macOS that simplifies installing and deleting apps with their data.

## What is appman?
appman is an app manager for macOS. Its purpose is to help automate the process of properly uninstalling external apps.

Normally, you have to delete the .app file, and then delete the app's data files manually.

With appman, all you have to do is use the command line.

## Installation
To install appman, execute the following in your Terminal:

```sh
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/blueysh/appman/main/install.sh)"
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
- `spotify`: Spotify by Spotify Inc.
- `arc`: Arc Browser by The Browser Company

## Add Apps
To add apps, create a pull request for `README.md` and add your app under `Available Apps`. In the description for your pull request, add your app data following this template:
```json
{
  "appName": "A Cool App",
  "developerName": "A Cool Developer",
  "versions": {
    "intel": "https://some.cool.app/download/intel.dmg",
    "silicon": "https://some.cool.app/download/silicon.dmg"
  },
  "appSupportDirName": "ACoolApp",
  "downloadsAs": "dmg"
}
```

`downloadsAs` should preferably be set to either `dmg` or `zip`.
`appSupportDirName` **must** be set to the `Application Support` directory name the app creates.

If the app doesn't have an Apple Silicon version, just set `versions.silicon` to `null`.
Or, if the URL is the same for both versions, set `versions.intel` and `versions.silicon` to the same URL.

Both URLs must download the same kind of file as you define in `downloadsAs`.
