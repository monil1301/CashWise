package com.shah.cashwise

import com.shah.cashwise.ui.screens.setup.model.generateInviteCredentials
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class InviteCredentialsFactoryTest {

    @Test
    fun codeMatchesCwDashFourDashThreeFormat() {
        val credentials = generateInviteCredentials(Random(seed = 42))

        val parts = credentials.code.split("-")
        assertEquals(3, parts.size, "code should be three dash-separated parts")
        assertEquals("CW", parts[0])
        assertEquals(4, parts[1].length)
        assertEquals(3, parts[2].length)
    }

    @Test
    fun linkPointsAtCashwiseJoinHost() {
        val credentials = generateInviteCredentials(Random(seed = 7))

        assertTrue(
            credentials.link.startsWith("cashwise.app/join/"),
            "link should be rooted at cashwise.app/join/",
        )
        val id = credentials.link.removePrefix("cashwise.app/join/")
        assertEquals(7, id.length, "id portion should be 7 characters")
    }

    @Test
    fun differentSeedsProduceDifferentCredentials() {
        val a = generateInviteCredentials(Random(seed = 1))
        val b = generateInviteCredentials(Random(seed = 2))
        assertNotEquals(a.code, b.code)
        assertNotEquals(a.link, b.link)
    }

    @Test
    fun expiresInSevenDaysByDefault() {
        val credentials = generateInviteCredentials(Random(seed = 0))
        assertEquals(7, credentials.expiresInDays)
    }
}
