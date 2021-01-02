# CleoPetra

![](demo.png)

CleoPetra aims to create a custom tournament runner for the [RLBot-community](https://www.rlbot.org) that help host, manage and run a tournament. The system supports multiple formats such as single elimination, double elimination, Swiss-system and round robin. It supports multiple stages and seeding of participants, allowing for a easy transfer of the best participants within stages.
Apart from these features the system also adds support specifically for the [RLBot-framework](https://github.com/RLBot/RLBot). It can modify the `rlbot.cfg` file based on the bots of a match and can also start a match using the RLBot-framework.

#### Prerequisites
- Java 14+ JDK installed.
- Optional: RLBot-framework installed.

#### Quick Start

If you just want to use the tournament runner, see the jar file in the [latest release](https://github.com/ds306e18/cleopetra/releases).

## Setup IntelliJ project
For using the IDE-environment to compile, debug and build artifacts, the following setup must be carried out. The project is running with Gradle and this will be used to build and run both the tests and the project.

1. Install [IntelliJ](https://www.jetbrains.com/idea/). The community edition is free.
1. Clone repository to your computer.
1. Start IntelliJ and open the CleoPetra project folder.
1. When the project has been loaded a pop-up message will appear in the lower right corner of the screen. Click the *Import Gradle Project*.
1. Keep the default settings and press *OK* and wait for the import to finish.
1. The project is now imported and can be run within IntelliJ through Gradle.

#### Run application and tests.
The above setup is required in order to run the application using Gradle. The following functionality is accessed using the Gradle panel found in the right side of IntelliJ.

To **run the application** use the task: ``\application\run``.<br>
To **run the tests** use the task: ``\verification\test``.<br>

### Installing Cleopetra with jpackage / minimal JRE

1. Install Wix from https://github.com/wixtoolset/wix3/releases/tag/wix3112rtm
1. You might need .NET 3.5 https://dotnet.microsoft.com/download/dotnet-framework/net35-sp1 for Wix
1. Get JDK 14 or higher, and point JAVA_HOME to it. Restart IntelliJ.
1. Execute the gradle task 'jpackage'
1. Look in build/jpackage and you should have cleopetra-1.0.msi
1. Test it out
   - Run the msi
   - Hit the Windows key, search cleopetra, and run it.
