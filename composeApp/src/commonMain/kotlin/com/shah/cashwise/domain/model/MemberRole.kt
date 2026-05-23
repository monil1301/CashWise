package com.shah.cashwise.domain.model

/**
 * Role assigned to an invited member in a Shared wallet. Mapped to the
 * three-segment selector in the "Invite a member" sheet. Role permissions /
 * audit enforcement live in the future domain layer — for now this is purely
 * a UI selection.
 */
enum class MemberRole {
    Admin,
    Member,
    Viewer,
}
