package com.adammcneilly.toa

/**
 * Any time we have a function that we want excluded from our JaCoCo coverage report, we can add this
 * annotation to it.
 *
 * NOTE: We should not use this lightly. Use it for things that are relevant to developers, like Jetpack Compose
 * previews. Do not use this to hide your application code.
 *
 * https://stackoverflow.com/a/66918619/3131147
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class ExcludeFromJacocoGeneratedReport
