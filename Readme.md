# Compose Best Practice

The **MVI Architecture with Compose-Based UI Demo** is a comprehensive showcase of modern application development
principles. It focuses on the Model-View-Intent (MVI) architecture with Jetpack Compose for building reactive user
interfaces.

## Theming

Theming is a crucial aspect of every project's design. To ensure synchronization with the design team, the project
adopts the Material 3 library. The design system, created with Material 3 Kit, is integrated into the project as the
"designsystem" module under the "core" folder.

### Colors

The project's color palette is derived from the design team's work in Figma or can be generated using
the [Material 3 Theme Builder](https://m3.material.io/theme-builder#/custom) tool.

![Color Palette](/docs/images/colors.png "Color Palette Image")

### Components

To meet the project's specific requirements, custom components are created based on the design system. These components
are found under **core/designsystem/components**.

## Navigation

The application consists of two navigation hierarchies: AppNavigation and MainApp.

![App Navigation](/docs/images/navigation.png "App Navigation Scheme")

1. **AppNavigation**: This graph contains a NavHost with two nested navigation graphs - Auth NavGraph and Main
   Navigation NavGraph. The Auth class manages the start destination selection. If users are not logged in, they are
   redirected to the auth nav graph for actions like Login, Registration, or Forgot Password. Once logged in, the start
   destination changes to "main," and the auth navigation graph is removed from the hierarchy.

2. **MainApp**: This screen includes its own internal NavHost, which incorporates a BottomNavigation component and its
   navigation hierarchy. If the user logs out, the main app screen is removed from the stack, and the login screen is
   displayed again.

## Saving State Demo Screen

The Saving State Demo screen showcases three different state-saving approaches:

![Saving State](/docs/images/saving_state_1.png "Saving State Screenshot")
![Saving State](/docs/images/saving_state_2.png "Saving State Screenshot")

1. The first card, with a counter, does not have any internal state savers. Instead, it reflects the current value of
   the counter from the view state. The current counter value is persistently saved in a view model using the
   SavedStateHandle.

2. The second card has internal state (expanded/collapsed) and uses `rememberSaveable()` with `autoSaver()` to persist
   this state through configuration changes.

3. The third card employs a more advanced technique to save its state using a custom Saver along
   with `rememberSaveable()`.

For further details, refer to the following video from the Android Developers
Team: [Saving State In Android](https://youtu.be/V-s4z7B_Gnc).

## Animation Screen

The Animation screen showcases a HorizontalPager with different types of animations on each card:

- Spring/keyframe animation example
- Vector Drawable animation example
- Custom animation example (Snowflakes)

Additionally, the screen demonstrates the usage of AGSL shaders in Jetpack Compose. The shader applies to the
ViewPager's page while scrolling, rendering the pages in black and white.

## Custom Layout Screen

_This section is currently in progress._

Thank you for exploring the MVI Architecture with Compose-Based UI Demo project. We hope you find it instructive and
inspiring for your own application development endeavors. Happy coding!
