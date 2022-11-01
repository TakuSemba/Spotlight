load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "2.4"
RULES_JVM_EXTERNAL_SHA = "2393f002b0a274055a4e803801cd078df90d1a8ac9f15748d1f803b97cfcdc9c"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

# Add support for Kotlin: https://github.com/bazelbuild/rules_kotlin.
rules_kotlin_version = "v1.5.0-alpha-2"
rules_kotlin_sha = "6194a864280e1989b6d8118a4aee03bb50edeeae4076e5bc30eef8a98dcd4f07"
http_archive(
    name = "io_bazel_rules_kotlin",
    sha256 = rules_kotlin_sha,
    urls = ["https://github.com/bazelbuild/rules_kotlin/releases/download/%s/rules_kotlin_release.tgz" % rules_kotlin_version],
)

load("@io_bazel_rules_kotlin//kotlin:dependencies.bzl", "kt_download_local_dev_dependencies")

kt_download_local_dev_dependencies()

load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kotlin_repositories", "kt_register_toolchains")

kotlin_repositories()

kt_register_toolchains()

android_sdk_repository(
  name = "androidsdk",
  api_level = 30,
  build_tools_version = "29.0.2",
)


load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "androidx.appcompat:appcompat:1.2.0",
        "androidx.constraintlayout:constraintlayout:1.1.3",
    ],
    repositories = [
        "https://maven.fabric.io/public",
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)
