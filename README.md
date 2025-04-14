Bug with [moko resources](https://github.com/icerockdev/moko-resources) themed color that occurs when color gets requested from the common code for ios target. 

When the app is forced to be in the dark mode with overriding property `window.overrideUserInterfaceStyle` and you try to fetch the color value in common code: on the first fetch it is correct, on the second and further the color theme is always the same as the system one, not the one that the app is currently in.

<img src="/MokoColorBugGig.gif" width="300"/>
