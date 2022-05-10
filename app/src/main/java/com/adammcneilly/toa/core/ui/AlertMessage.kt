package com.adammcneilly.toa.core.ui

import java.util.UUID

/**
 * An [AlertMessage] is some message that renders to the user in some alerting fashion.
 *
 * Optionally, this message can contain some action button that the user can interact with.
 */
data class AlertMessage(
    val message: UIText,
    val actionText: UIText? = null,
    val onActionClicked: () -> Unit = {},
    val onDismissed: () -> Unit = {},
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val duration: Duration = Duration.SHORT,
) {

    /**
     * This enum defines the various durations that an [AlertMessage] can be rendered on the screen.
     */
    enum class Duration {
        SHORT,
        LONG,
        INDEFINITE,
    }

    override fun equals(other: Any?): Boolean {
        return other is AlertMessage &&
            other.message == this.message &&
            other.actionText == this.actionText &&
            other.duration == this.duration
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + (actionText?.hashCode() ?: 0)
        result = 31 * result + id.hashCode()
        result = 31 * result + duration.hashCode()
        return result
    }
}
