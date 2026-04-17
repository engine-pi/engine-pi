# `packages` (Pakete)

Um Pakete mit gleichem Namen zu vermeiden, haben sich in der Java-Welt folgende
Konvention für Paketnamen herausgebildet:[^baeldung]

- Paketnamen bestehen nur aus *Kleinbuchstaben* und *Unterstrichen* (`_`), um sie von Klassen zu unterscheiden.
- Paketnamen sind durch *Punkte* getrennt.
- Der Anfang des Paketnamens wird durch die *Organisation* bestimmt, die sie erstellt.

Um den Paketnamen auf der Grundlage einer Organisation zu bestimmen, wird die
URL der Organisation umgedreht.[^docs-oracle] Beispielsweise wird aus der URL

    https://pirckheimer-gymnasium.de/engine-pi

der Paketname:

    de.pirckheimer_gymnasium.engine_pi


[^docs-oracle]: https://docs.oracle.com/javase/specs/jls/se21/html/jls-6.html#d5e8762

    # Package Names and Module Names

    Programmers should take steps to avoid the possibility of two published
    packages having the same name by choosing unique package names for packages
    that are widely distributed. This allows packages to be easily and
    automatically installed and catalogued. This section specifies a suggested
    convention for generating such unique package names. Implementations of the
    Java SE Platform are encouraged to provide automatic support for converting
    a set of packages from local and casual package names to the unique name
    format described here.

    If unique package names are not used, then package name conflicts may arise
    far from the point of creation of either of the conflicting packages. This
    may create a situation that is difficult or impossible for the user or
    programmer to resolve. The classes `ClassLoader` and `ModuleLayer` can be
    used to isolate packages with the same name from each other in those cases
    where the packages will have constrained interactions, but not in a way that
    is transparent to a naïve program.

    You form a unique package name by first having (or belonging to an
    organization that has) an Internet domain name, such as `oracle.com`. You
    then reverse this name, component by component, to obtain, in this example,
    `com.oracle`, and use this as a prefix for your package names, using a
    convention developed within your organization to further administer package
    names. Such a convention might specify that certain package name components
    be division, department, project, machine, or login names.

    Example 6.1-1. Unique Package Names

    ```
    com.nighthacks.scrabble.dictionary
    org.openjdk.compiler.source.tree
    net.jcip.annotations
    edu.cmu.cs.bovik.cheese
    gov.whitehouse.socks.mousefinder
    ```

    The first component of a unique package name is always written in
    all-lowercase ASCII letters and should be one of the top level domain names,
    such as `com`, `edu`, `gov`, `mil`, `net`, or `org`, or one of the English
    two-letter codes identifying countries as specified in ISO Standard 3166.

    In some cases, the Internet domain name may not be a valid package name.
    Here are some suggested conventions for dealing with these situations:

    - If the domain name contains a hyphen, or any other special character not
      allowed in an identifier (§3.8), convert it into an underscore.
    - If any of the resulting package name components are keywords (§3.9), append
      an underscore to them.
    - If any of the resulting package name components start with a digit, or any
      other character that is not allowed as an initial character of an
      identifier, have an underscore prefixed to the component.

[^baeldung]: https://www.baeldung.com/java-packages#1-naming-conventions

    # 3.1. Naming Conventions

    In order to avoid packages with the same name, we follow some naming conventions:

    - we define our package *names in all lower case*
    - package names are period-delimited
    - names are also determined by the *company or organization that creates them*

    To determine the package name based on an organization, we’ll typically
    start by reversing the company URL. After that, the naming convention is
    defined by the company and may include division names and project names.

    For example, to make a package out of www.baeldung.com, let’s reverse it:

        com.baeldung

    We can then further define sub-packages of this, like `com.baeldung.packages`
    or `com.baeldung.packages.domain`.
