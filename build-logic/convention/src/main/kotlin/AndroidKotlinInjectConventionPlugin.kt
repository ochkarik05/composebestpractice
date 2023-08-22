import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class AndroidKotlinInjectConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs: VersionCatalog = this@with.extensions.getByType<VersionCatalogsExtension>().named("libs")
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }
            dependencies {
                "ksp"(libs.findLibrary("kotlin.inject.compiler.ksp").get())
                "implementation"(libs.findLibrary("kotlin.inject.runtime").get())
            }
        }
    }
}
