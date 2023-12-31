package com.bhachu.farmica;

import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.belongToAnyOf;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packagesOf = FarmicaApp.class, importOptions = DoNotIncludeTests.class)
class TechnicalStructureTest {

    // prettier-ignore
    @ArchTest
    static final ArchRule respectsTechnicalArchitectureLayers = layeredArchitecture()
            .consideringAllDependencies()

            .layer("Config").definedBy("..config..")
            .layer("Custom").definedBy("..custom..")
            .layer("Web").definedBy("..web..", "..custom.resource")
            .optionalLayer("Service").definedBy("..service..", "..custom.service")
            .layer("Security").definedBy("..security..")
            .optionalLayer("Persistence").definedBy("..repository..", "..custom.repository")
            .layer("Domain").definedBy("..domain..")

            .whereLayer("Config").mayNotBeAccessedByAnyLayer()
            .whereLayer("Web").mayOnlyBeAccessedByLayers("Config")
            .whereLayer("Custom").mayOnlyBeAccessedByLayers("Persistence", "Service", "Web", "Config")
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Web", "Config")
            .whereLayer("Security").mayOnlyBeAccessedByLayers("Config", "Service", "Web")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service", "Security", "Web", "Config")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Persistence", "Service", "Security", "Web", "Config")

            .ignoreDependency(belongToAnyOf(FarmicaApp.class), alwaysTrue())
            .ignoreDependency(alwaysTrue(), belongToAnyOf(
                    com.bhachu.farmica.config.Constants.class,
                    com.bhachu.farmica.config.ApplicationProperties.class));
}
