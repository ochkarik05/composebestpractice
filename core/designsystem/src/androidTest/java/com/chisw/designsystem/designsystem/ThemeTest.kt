package com.chisw.designsystem.designsystem

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import com.chisw.designsystem.theme.AppTheme
import com.chisw.designsystem.theme.DarkColors
import com.chisw.designsystem.theme.LightColors
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ThemeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun when_darkThemeFalse_then_usesLightTheme() {
        composeTestRule.setContent {
            AppTheme(
                useDarkTheme = false,
            ) {
                val colorScheme = LightColors
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
            }
        }

    }
    @Test
    fun when_darkThemeTrue_then_usesDarkTheme() {
        composeTestRule.setContent {
            AppTheme(
                useDarkTheme = true,
            ) {
                val colorScheme = DarkColors
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
            }
        }

    }

}

private fun assertColorSchemesEqual(
    expectedColorScheme: ColorScheme,
    actualColorScheme: ColorScheme,
) {
    assertEquals(expectedColorScheme.primary, actualColorScheme.primary)
    assertEquals(expectedColorScheme.onPrimary, actualColorScheme.onPrimary)
    assertEquals(expectedColorScheme.primaryContainer, actualColorScheme.primaryContainer)
    assertEquals(expectedColorScheme.onPrimaryContainer, actualColorScheme.onPrimaryContainer)
    assertEquals(expectedColorScheme.secondary, actualColorScheme.secondary)
    assertEquals(expectedColorScheme.onSecondary, actualColorScheme.onSecondary)
    assertEquals(expectedColorScheme.secondaryContainer, actualColorScheme.secondaryContainer)
    assertEquals(expectedColorScheme.onSecondaryContainer, actualColorScheme.onSecondaryContainer)
    assertEquals(expectedColorScheme.tertiary, actualColorScheme.tertiary)
    assertEquals(expectedColorScheme.onTertiary, actualColorScheme.onTertiary)
    assertEquals(expectedColorScheme.tertiaryContainer, actualColorScheme.tertiaryContainer)
    assertEquals(expectedColorScheme.onTertiaryContainer, actualColorScheme.onTertiaryContainer)
    assertEquals(expectedColorScheme.error, actualColorScheme.error)
    assertEquals(expectedColorScheme.onError, actualColorScheme.onError)
    assertEquals(expectedColorScheme.errorContainer, actualColorScheme.errorContainer)
    assertEquals(expectedColorScheme.onErrorContainer, actualColorScheme.onErrorContainer)
    assertEquals(expectedColorScheme.background, actualColorScheme.background)
    assertEquals(expectedColorScheme.onBackground, actualColorScheme.onBackground)
    assertEquals(expectedColorScheme.surface, actualColorScheme.surface)
    assertEquals(expectedColorScheme.onSurface, actualColorScheme.onSurface)
    assertEquals(expectedColorScheme.surfaceVariant, actualColorScheme.surfaceVariant)
    assertEquals(expectedColorScheme.onSurfaceVariant, actualColorScheme.onSurfaceVariant)
    assertEquals(expectedColorScheme.inverseSurface, actualColorScheme.inverseSurface)
    assertEquals(expectedColorScheme.inverseOnSurface, actualColorScheme.inverseOnSurface)
    assertEquals(expectedColorScheme.outline, actualColorScheme.outline)
}