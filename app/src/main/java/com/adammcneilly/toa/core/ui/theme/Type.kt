package com.adammcneilly.toa.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.adammcneilly.toa.R

private val UrbanistExtraBold = Font(R.font.urbanist_extrabold, FontWeight.ExtraBold)
private val UrbanistSemiBold = Font(R.font.urbanist_semibold, FontWeight.SemiBold)
private val UrbanistBold = Font(R.font.urbanist_bold, FontWeight.Bold)
private val UrbanistMedium = Font(R.font.urbanist_medium, FontWeight.Medium)
private val UrbanistLight = Font(R.font.urbanist_light, FontWeight.Light)
private val UrbanistRegular = Font(R.font.urbanist_regular, FontWeight.Normal)

private val UrbanistFamily = FontFamily(
    UrbanistExtraBold,
    UrbanistSemiBold,
    UrbanistBold,
    UrbanistMedium,
    UrbanistLight,
    UrbanistRegular,
)

val typography = Typography(
    defaultFontFamily = UrbanistFamily,
)
