package com.shah.cashwise.ui.screens.setup.model

import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.invite_role_admin
import cashwise.composeapp.generated.resources.invite_role_admin_hint
import cashwise.composeapp.generated.resources.invite_role_member
import cashwise.composeapp.generated.resources.invite_role_member_hint
import cashwise.composeapp.generated.resources.invite_role_viewer
import cashwise.composeapp.generated.resources.invite_role_viewer_hint
import com.shah.cashwise.domain.model.MemberRole
import org.jetbrains.compose.resources.StringResource

/** Localized chip label for a [MemberRole]. */
fun MemberRole.nameRes(): StringResource = when (this) {
    MemberRole.Admin -> Res.string.invite_role_admin
    MemberRole.Member -> Res.string.invite_role_member
    MemberRole.Viewer -> Res.string.invite_role_viewer
}

/** Localized description shown under the role selector explaining the role. */
fun MemberRole.hintRes(): StringResource = when (this) {
    MemberRole.Admin -> Res.string.invite_role_admin_hint
    MemberRole.Member -> Res.string.invite_role_member_hint
    MemberRole.Viewer -> Res.string.invite_role_viewer_hint
}
