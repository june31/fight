Copier les jars dans le projet en s�lectionnant pom.xml -> Run as -> Maven install

Puis cr�er une t�che "External tool"

Location (selon son path java, utiliser javaw plut�t que Java) :
C:\Program Files\Eclipse Adoptium\jdk-21.0.1.12-hotspot\bin\javaw.exe

Working directory :
${workspace_loc:/Aggro}

Arguments :
-classpath ${workspace_loc:/Aggro/target/classes};${workspace_loc:/Aggro/target/dependency}\* pro.juin.aggro.Aggro ${selected_resource_loc}

Puis associer F12 � "Run last launched External Tool"