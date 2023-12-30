Copier les jars dans le projet en sélectionnant pom.xml -> Run as -> Maven install

Puis créer une tâche "External tool"

Location (selon son path java, utiliser javaw plutôt que Java) :
C:\Program Files\Eclipse Adoptium\jdk-21.0.1.12-hotspot\bin\javaw.exe

Working directory :
${workspace_loc:/Aggro}

Arguments :
-classpath ${workspace_loc:/Aggro/target/classes};${workspace_loc:/Aggro/target/dependency}\* pro.juin.aggro.Aggro ${selected_resource_loc}

Puis associer F12 à "Run last launched External Tool"