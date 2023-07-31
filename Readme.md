# Project description

A demo project that shows the basic principles of building modern applications on the MVI architecture with Compose-Based UI


# Theming

Every  project usually starts from design.

Well-structured design usually contains all the necessary elements inside. As modern projects on Android typically are  built over the [Material 3 library](https://m3.material.io/), the design team usually starts with [Material 3 Kit ](https://www.figma.com/community/file/1035203688168086460/Material-3-Design-Kit)and provides the full design system per project. Usually we are provided with the design system that was built specifically for the project.

So, to be synchronized with your design team, it’s usually a good idea to create the same component set in the project.

Current project contains the whole design system in its own module, named “**designsystem**”. This module lays together with other modules under the “**core**” folder.


## Colors

If the design team uses the right tools, all that you need to get it in the code is just export the colors from Figma design directly to the project.

Sometimes it is not the case, so you can try to generate the color palette using this tool:

[https://m3.material.io/theme-builder#/custom](https://m3.material.io/theme-builder#/custom). For current project this tool was used with base colors were taken directly from the site: [https://chisw.com/](https://chisw.com/)


## 

![alt_text](/docs/images/colors.png "Color Palette Image")



## Components

Out-of-the-box material components do not always fit all project needs. Usually the design team creates a set of custom components in Figma, and these components are used across the project.

Take a look at the **core/designsystem/components** to find what custom components are in the current project.

## Other components

You can also be provided with other components like described
here: [https://m3.material.io/styles](https://m3.material.io/styles). Among that, you will be provided with such
resources as icons, backgrounds, images, etc.

It’s highly recommended to add these components in the same way as you do for Theme/Colors, to be synchronized with your
design team.

# Navigation

The app currently consists of two navigation hierarchies: AppNavigation and MainApp.

![Navigation](/docs/images/navigation.png "App Navigation Scheme")


The AppNavigation graph contains a NavHost with two nested navigation graphs - Auth NavGraph and   Main Navigation NavGraph. The Auth class 
is responsible for selecting the start destination for the AppNavigation graph. If users are not logged in, they are redirected to the 
auth nav graph. This graph has its own hierarchy with an ability to navigate between Login/Registration/Forgot Password
screens
until the users get logged in. In this case the Auth is indicated that user is logged in and the start destination now
is "main".
The auth navigation graph is removed from the hierarchy.

The MainApp screen includes its own internal NavHost, which incorporates a BottomNavigation component and its own
navigation hierarchy.

In addition, if the user logs out, the main app screen is removed from the stack, and the login screen is displayed
again.

# Saving State Demo Screen

Saving State Demo screen shows three different approaches of state saving.

As you can see on the video below, there are three cards. Each of them uses its own way to save state.

<div style="text-align:center">

![Saving State Demo Screencast](docs/videos/screen-20230729-190520.mp4)

</div>

The first card, with a counter, does not have any internal state savers. Instead, it only reflects
current value of counter that comes from view state. The current value of counter is saved persistently
in a view model. The SavedStateHandle is used for this purpose.

The second card has internal state (expanded/collapsed). To allow this state to survive configuration
changes it uses `rememberSaveable()` with `autoSaver()`. You can use this approach if you need to save
something that can be saved in the Bundle.

The third card uses more advanced technic to save its state. It also uses `rememberSaveable()` but this time
the custom Saver is provided.

For more information, see the following video from an Android Developers Team:

[![Saving State In Android](docs/images/saving_state_android_youtube.png)](https://youtu.be/V-s4z7B_Gnc)

# Animation Screen

Animation screen contains HorizontalPager with cards and every card presents different
animation type.

There are the following animation types for now:

- Spring/keyframe animation example
- Vector Drawable animation example
- Custom animation example (Snowflakes)

There is also an example how you can use AGSL shaders in Jetpack Compose. The shader applies
to the ViewPager's page when it starts scrolling, and it makes pages black and white

# Custom Layout Screen

_In progress_
