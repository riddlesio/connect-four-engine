apply plugin: 'java'
apply plugin: 'groovy'
apply plugin: 'application'

// Include dependent libraries in archive.
mainClassName = 'io.riddles.connectfour.Connectfour'

sourceCompatibility = 1.8
version = '1.0.0'

sourceSets {
    main {
        java {
            srcDir 'src/java'
        }

        resources {
            srcDir 'src/resources'
        }
    }

    test {
        groovy {
            srcDir 'test/groovy'
        }

        resources {
            srcDir 'test/resources'
        }
    }
}

jar {
    manifest {
        attributes 'Implementation-Title': 'Connectfour Game Engine',
                'Implementation-Version': version,
                'Main-Class': mainClassName
    }

    from {
        configurations.compile.collect { it.isDirectory() ? it : zipTree(it) }
    }
}

repositories {
    mavenCentral()
}

dependencies {
	compile "org.mongodb:mongodb-driver:3.2.2"
	compile "com.amazonaws:aws-java-sdk:1.7.15"
	compile "org.json:json:20160212"
    compile "org.codehaus.groovy:groovy-all:2.4.1"
    compile "cglib:cglib:2.2"
    testCompile "org.spockframework:spock-core:1.0-groovy-2.4"

}