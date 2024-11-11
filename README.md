# "Fight" Java Competitive Programming Framework (Personal Project)

This project is a Java framework designed to assist in solving competitive programming challenges on platforms such as:

- CodinGame
- CodeChef
- IsoContest
- Advent of Code
- ...

## Key Benefits

The framework provides three main benefits:

1. **Comprehensive API** with a variety of easy-to-use and high-performance functions. For example:
   - Use `L` instead of `ArrayList<Integer>`, with numerous additional methods (like `min()`, `max()`, and more).
   - `Scanner` is replaced by faster and more useful classes (`Scan`, `ScanL`, `ScanLs`, etc.).
   - A wealth of mathematical and algorithmic tools.

2. **"Magic" Tool** that integrates your code with any missing API source code and copies it to your clipboard, ready to paste into the challenge platform. Hooray!

3. **Simplified Debugging**.

### Example

When solving [this](https://www.codingame.com/ide/puzzle/magic-count-of-numbers) challenge on the [Codingame](https://www.codingame.com/) platform, the solution I typed in Eclipse is:

```java
public class CGS_Current {
    public static void main(String[] args) {
        long n = Scan.readLong();
        int k = Scan.readInt();
        L l = ScanL.read(k);
        long sum = 0;
        for (int i = 1; i <= k; i++) {
            for (L c : new IntCombinations(l, i)) {
                long prod = c.mulLong();
                sum += n / prod * ((i & 1) * 2 - 1);
            }
        }
        S.o(sum);
    }
}
```
Then I  press a configured key (F12), and a "Solution" class is created in my clipboard. This class contains my code along with the `Scan`, `ScanL`, `IntCombinations`, `S` class sources, and all dependencies. The class is named "Solution" because my class name starts with "CGS," indicating the target mode is "CodinGame Solution".

## Debugging

When your class (e.g., `Foo.java`) uses `Scan`, `ScanLs`, `ScanLn`, etc., it checks for a `Foo.txt` file in the same directory. If the file is missing, it generates it automatically. If present, `Scan` reads input from the file, enabling easy debugging! When Aggro aggregates your code, it switches to `System.in`, but there is an option to always use a file if desired.

## Project Structure

The project includes two main Eclipse sub-projects:

- **Aggro**: The "magic" aggregation tool
- **Tools**: The competitive API

The rest is personal code that may not be widely useful.

## FAQ

**Q: What is the project license?**  
**A:** Apache 2.0.

**Q: How do I configure F12 in Eclipse?**  
**A:** In `Preferences/Keys`, assign F12 to "Run Last Launched External Tool" and create an external tool configuration, for example:
- **Location**: `C:\Program Files\Eclipse Adoptium\jdk-21.0.1.12-hotspot\bin\javaw.exe`
- **Working Directory**: `${workspace_loc:/Aggro}`
- **Arguments**: `-classpath ${workspace_loc:/Aggro/target/classes};${workspace_loc:/Aggro/target/dependency}\* pro.juin.aggro.Aggro ${selected_resource_loc}`

**Q: Should I use the whole "Fight" repository?**  
**A:** Depends on the sub-project:
   - **Aggro**: Definitely.
   - **Tools**: Customize and build your own tools. You’re encouraged to adapt or cherry-pick ideas from this project.
   - **Others**: Not needed.

**Q: What are the limitations of "Aggro" and "Tools"?**  
**A:** Known limitations include:
   - Avoid referencing the main class name. Use lambdas (e.g., `x -> foo(x)`) instead of `CGS_GitHubExample::foo`.
   - Tools cannot reference classes in the same package because Aggro uses "import" statements to find dependencies.
   - Avoid dependency clutter in your API as Aggro imports recursively. This is why:
     - `Scan` retrieves only Java types; `ScanL` retrieves `L`, `ScanLs` retrieves `Ls`, and so on. If `Scan` could retrieve everything, it would need to import everything.
     - `MapLs` maps `Ls` from/to `L`. If `Ls` had a `mappedToL()` method, it would also have `mappedToLl()`, `mappedToLd()`, etc. It would need to import everything for the same reason.

**Q: Can I use multiple classes in my project?**  
**A:** Yes, all classes in the current class hierarchy are imported except those with an underscore in their name (except the main class). So for instance if you take part in an IsoContest challenge, you can prepare a package with `ISO_1.java`, `ISO_2.java`, `ISO_3.java`...

**Q: Is the codebase stable?**  
**A:** `Aggro` is production-ready. `Tools` is evolving; it may have bugs, missing features, and recent ideas that could change. Treat it as a reference to create your own toolset.

**Q: How can I configure Aggro?**  
**A:** Use the `aggro.properties` file, and add plugins in the "plugins" directory to support additional competition sites.
