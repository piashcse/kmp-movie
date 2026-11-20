# Keep line numbers for crash reports; R8 will strip the rest.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Ktor and kotlinx.serialization publish their own consumer rules; add project-specific
# keep rules here if release builds report missing classes.
