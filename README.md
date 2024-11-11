"Fight" Java competitive programming framework personal project

It helps a lot when using Java to solve challenges on platforms such as:
- CodinGame
- CodeChef
- IsoContest
- Advent of Code
- ...

It provides 3 main benefits:
- A comprehensive API with a lot of easy to use and fast to use founctions. E.g.:
  - You'll use L instead of ArrayList<Integer>, with dozens of additional methods (such as min(), max(), and everything you can think of)
  - Scanner is replaced by other much faster and useful classes (Scan, ScanL, ScanLs and so on, see below why there are plenty of them)
  - Lots and lots of useful Mathematic and algorithmic tools
- A 'magic' tool that takes your code, adds whatever API source code is missing, and copy it to your clipboard, ready to be pasted to the challenge platform. Hooray!
- An easy way to debug

Example:
https://www.codingame.com/ide/puzzle/magic-count-of-numbers
Just write in your IDE (such as Eclipse):
public class CGS_GitHubExample {
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

Press a configured key (I use F12), and a "Solution" class will be created in your clipboard, containing your code, the Scan, ScanL, IntCombinations, S class sources and all sub-dependencies.
The generated class is named "Solution" because your class name starts with CGS, and the tool understand the target mode is "CodinGame Solution".

It contains 2 interesting Eclipse sub projects
- Aggro the "magic" aggregation tool
- Tools the competitive API

The rest is my own stuff, not very useful.

Debugging:
When your class Foo.java is using Scan, ScanLs, ScanLn..., it will look for Foo.txt in the same directory. If it is not present, the file will be automatically generated.
If the file is present, Scan looks for input from this file. Hooray you can debug!
When Aggro aggregates your code, the resulting code uses System.in instead of a file. Still, there is an option to always use a file if needed.

FAQ:
Q: What is the project license?
A: Apache 2.0.

Q: How do I configure F12 in Eclipse?
A: In Preferences/Keys, assign F12 to "Run Last Launched External Tool", and create a single external tool with for instance:
Location: C:\Program Files\Eclipse Adoptium\jdk-21.0.1.12-hotspot\bin\javaw.exe
Working Directory: ${workspace_loc:/Aggro}
Arguments: -classpath ${workspace_loc:/Aggro/target/classes};${workspace_loc:/Aggro/target/dependency}\* pro.juin.aggro.Aggro ${selected_resource_loc}

Q: Should I use the whole "Fight" repository?
A: Depends on the sub-project:
- Aggro: definitively
- Tools: create your own tools! You will not use other people tools as they won't feel natural to you. But feel free to cherry peek whatever good idea you find in this project.
- Others: no need

Q: What are "Aggro" and "Tools" limitations?
A: There are several limitations, including:
- You can't refer to the main class name. Dont use "CGS_GitHubExample::foo", use "x -> foo(x)" instead, as CGS_GitHubExample will be renamed.
- Tools cannot refer to classes in the same package. Aggro finds dependencies using the "import" statements. No import, no dependency! That's why for instance class WGraph and WNode are not in the same package: WGraph uses WNode.
- Beware of dependency clogging when creating your API. Aggro is not smart and recursively imports what's defined in import statements. That's why:
  - Scan only retrieves Java types, ScanL retrieves L, ScanLs retrieves Ls, ScanLn retrieves Ln, etc. If Scan could retrieve anything, it would import everything.
  - MapLs maps Ls from/to L. If there was a mappedToL() function in Ls, there would also need a mappedToLl(), mappedToLd()... and Ls would import everything.
- Aggro tries to remove unused static methods from the imported API, but it is not very intelligent (its logic is text based). It will always keep all instance methods and all other members.

Q: Can I use several classes in my project?
A: Yes. All classes in the current class hierarchy will be imported, except the ones which name contains an underscore (the main class may contain an underscore, though).
So for instance if you take part in an IsoContest challenge, you can prepare a package with ISO_1.java, ISO_2.java, ISO_3.java...

Q: Is the codebase stable?
A: Aggro is ready for production. Tools is always evolving: there are bugs, missing features, and good ideas from last week that I find not so good todays. Use it as a reference to create your own toolset.

Q: How can I configure Aggro ?
A: There is an aggro.properties file, and you can add your own plugin in the "plugins" directory to add a code competition site. That's easy.
