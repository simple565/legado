pluginManagement {
    repositories {
        // maven("https://maven.aliyun.com/repository/gradle-plugin")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // 镜像仓库,无法连接源仓库自行启用镜像仓库,不要提交修改
        // maven(url = "https://maven.aliyun.com/repository/public")
        // maven(url = "https://maven.aliyun.com/repository/google")
        // maven(url = "https://repo.huaweicloud.com/repository/maven")
        // maven(url = "https://s01.oss.sonatype.org/content/groups/public")
        maven(url = "https://jitpack.io")
        google()
        mavenCentral()
    }
}

rootProject.name = "legado"

include(":app")
include(":modules:book")
include(":modules:rhino")
