plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("pl.allegro.tech.build.axion-release") version "1.18.6"
}

scmVersion {
	repository {
		directory = "${layout.projectDirectory}"
	}
	tag {
		prefix.set("demo-")
	}
	releaseOnlyOnReleaseBranches = true
	versionCreator("simple")
	unshallowRepoOnCI.set(true)
	branchVersionIncrementer.putAll(
		mapOf(
			"breaking/.*" to "incrementMajor",
			"feature/.*" to "incrementMinor",
			"fix/.*" to "incrementPatch",
			"bugfix/.*" to "incrementPatch",
		),
	)
}

group = "com.example"
version = scmVersion.version

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
