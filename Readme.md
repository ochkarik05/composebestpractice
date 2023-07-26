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

![alt_text](/docs/images/navigation.png "App Navigation Scheme")


The AppNavigation graph contains a NavHost with two nested navigation graphs - Auth NavGraph and   Main Navigation NavGraph. The Auth class 
is responsible for selecting the start destination for the AppNavigation graph. If users are not logged in, they are redirected to the 
auth nav graph. This graph has its own hierarchy with an ability to navigate between Login/Registration/Forgot Password screens
until the users get logged in. In this case the Auth is indicated that user is logged in and the start destination now is "main". 
The auth navigation graph is removed from the hierarchy.

The MainApp screen includes its own internal NavHost, which incorporates a BottomNavigation component and its own
navigation hierarchy.

In addition, if the user logs out, the main app screen is removed from the stack, and the login screen is displayed
again.
