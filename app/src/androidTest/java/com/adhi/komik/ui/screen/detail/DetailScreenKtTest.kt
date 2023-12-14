package com.adhi.komik.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.adhi.komik.ui.theme.KomikTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            KomikTheme {
                // Ganti pemanggilan DetailContent dengan data fiktif yang sesuai dengan kebutuhan pengujian
                DetailContent(
                    id = 0,
                    name = "Test Komik",
                    description = "Description for testing.",
                    photoUrl = "https://example.com/image.jpg",
                    //                    location = fakeKomik.location,
                    rating = 9.0,
                    isFavorite = false,
                    navigateBack = {},
                    onFavoriteButtonClicked = { _, _ -> }
                )
            }
        }
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText("Test Komik").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description for testing.").assertIsDisplayed()
        //        composeTestRule.onNodeWithText(fakeKomik.location).assertIsDisplayed()
    }

    @Test
    fun addToFavoriteButton_hasClickAction() {
        composeTestRule.onNodeWithTag("add_remove_favorite").assertHasClickAction()
    }

    @Test
    fun detailContent_isScrollable() {
        composeTestRule.onNodeWithTag("scroll").performTouchInput {
            swipeUp()
        }
    }
}
