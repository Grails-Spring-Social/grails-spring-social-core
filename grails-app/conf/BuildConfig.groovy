/* Copyright 2013 Domingo Suarez Torres.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"
//grails.project.war.file = "target/${appName}.war"
grails.project.war.file = "target/ROOT.war"

grails.project.repos.sindicato.url = "http://repo.sindica.to/nexus/content/repositories/snapshots/"
grails.project.repos.default = "sindicato"

grails.project.fork = [
  // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
  //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

  // configure settings for the test-app JVM, uses the daemon by default
  //test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon: false],
  // configure settings for the run-app JVM
  run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
  // configure settings for the run-war JVM
  war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
  // configure settings for the Console UI JVM
  console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
  // inherit Grails' default dependencies
  inherits("global") {
    // specify dependency exclusions here; for example, uncomment this to disable ehcache:
    // excludes 'ehcache'
  }
  log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
  checksums true // Whether to verify checksums on resolve
  legacyResolve false
  // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

  repositories {
    inherits true // Whether to inherit repository definitions from plugins

    grailsPlugins()
    grailsHome()
    mavenLocal()
    grailsCentral()
    mavenCentral()
    // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
    mavenRepo "http://repo.spring.io/milestone/"
    mavenRepo "http://oss.sonatype.org/content/repositories/snapshots/"
    mavenRepo "http://repo.sindica.to/nexus/content/repositories/public-milestones/"
  }

  def gebVersion = "0.9.3"

  dependencies {
    def springSocialVersion = "1.1.0.RELEASE"

    compile("org.springframework.social:spring-social-core:${springSocialVersion}") { transitive = false }
    compile("org.springframework.social:spring-social-config:${springSocialVersion}") { transitive = false }
    compile("org.springframework.social:spring-social-web:${springSocialVersion}") { transitive = false }

    compile("org.springframework.security:spring-security-crypto:3.1.4.RELEASE") { transitive = false }
    compile("javax.inject:javax.inject:1")

    test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    test "org.gebish:geb-spock:${gebVersion}"
  }
  plugins {
    // plugins for the build system only
    build ":tomcat:7.0.52.1"
    build (':release:3.0.1') {
      export = false
    }
    compile(":spring-security-core:2.0-RC2")
    compile ":platform-core:1.0.0"
    compile(":hibernate:3.6.10.11") {
      export = false
    }

    test(":geb:${gebVersion}", ":codenarc:0.20", ":code-coverage:1.2.7") {
      export = false
    }
    test(":spock:0.7") {
      exclude "spock-grails-support"
      export = false
    }
  }
}

grails.release.scm.enabled = false
grails.project.repos.default = "grailsCentral"

coverage {
  exclusions = [
    "DefaultSpringSocialConfig*",
    "SpringSocialCoreDefaultConfig*"
  ]
  enabledByDefault = false
  xml = true
  html = true
}
