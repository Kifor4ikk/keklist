//import org.jooq.meta
import org.jooq.meta.kotlin.*
plugins {
    id("java")
    id("nu.studer.jooq") version "10.1.1"
}

group = "ru.kifor"
version = "1.0-SNAPSHOT"

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "src/main/generated")
        }
    }
}

repositories {
    mavenCentral()
}

jooq {
    version.set("3.19.28")  // the default (can be omitted)
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)  // the default (can be omitted)
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security:4.0.0")
    implementation("org.springframework.boot:spring-boot-starter-web:4.0.0")
    implementation("org.springframework.boot:spring-boot-starter-jooq:4.0.0")
    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.0")
    implementation("org.postgresql:postgresql:42.7.8")
    implementation("org.slf4j:slf4j-api:2.0.17")
        // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20250517")
    jooqGenerator("org.postgresql:postgresql:42.7.8")

    implementation("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")


}

buildscript {
    configurations["classpath"].resolutionStrategy.eachDependency {
        if (requested.group.startsWith("org.jooq") && requested.name.startsWith("jooq")) {
            useVersion("3.20.5")
        }
    }
}


tasks.test {
    useJUnitPlatform()
}
// Можно получить из перменных окружения или из файла gradle.properties
// Получение свойств: findProperty возвращает Any?, нужно привести к String,
// или использовать оператор Elvis (?:) для задания значения по умолчанию.
val dbUser: String = project.findProperty("dbUser") as? String
    ?: System.getenv("JOOQ_DB_USER")
    ?: "postgres"

val dbPassword: String = project.findProperty("dbPassword") as? String
    ?: System.getenv("JOOQ_DB_PASSWORD")
    ?: "1"

val dbUrl: String = project.findProperty("dbUrl") as? String
    ?: System.getenv("JOOQ_DB_URL")
    ?: "jdbc:postgresql://127.0.0.1:5432/kekbase"

jooq {
    version.set("3.20.5")
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

    configurations {
        create("main") {
            //Отключаем генерацию при компиляции, так как код фиксируется в Git
            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc {
                    driver = "org.postgresql.Driver"
                    url = dbUrl
                    user = dbUser
                    password = dbPassword
                }
                generator {
                    name = "org.jooq.codegen.DefaultGenerator"
                    target {
                        // 1. Указываем базовый пакет для сгенерированных классов
                        packageName = "ru.kifor.kek" // <--- Укажите ваш корневой пакет
                        // 2. Указываем, что генерировать нужно в папку с исходниками
                        // (Плагин создаст путь {directory}/{packageName})
                        directory = "src/main/generated"
                        // WARNING 🧨 🧨 🧨 🧨 🧨 🧨 🧨 🧨 🧨 🧨 🧨 🧨
                        // directory jooq считает своим рабочим каталогом и очищает его при генерации кода.
                        // поэтому указываем путь для безопасной папки. Аккуратнее смотрите пример!
                        // если бы directory = "src/main/java" а  packageName = "by.itgas.demo1.db.generated"
                        // Это бы удалило проект
                    }
                    database {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        forcedTypes {
                            forcedType {
                                name = "varchar"
                                includeExpression = ".*"
                                includeTypes = "JSONB?"
                            }
                            forcedType {
                                name = "varchar"
                                includeExpression = ".*"
                                includeTypes = "INET"
                            }
                        }
                    }
                    generate {
                        isDeprecated = false
                        isRecords = false
                        isImmutablePojos = false
                        isFluentSetters = false
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

tasks.named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq") { allInputsDeclared.set(true) }