"""
https://mkdocs-macros-plugin.readthedocs.io/en/latest/macros/
"""

JAVADOC_URL_PREFIX = "https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest"

def define_env(env) -> None:

    def _normalize_package_path(package_path: str) -> str:
        if not package_path.startswith("pi."):
            return "pi." + package_path
        return package_path


    def _to_url(package_path: str) -> str:
        """Convert a package path to a URL with slashes"""
        return package_path.replace(".", "/")

    @env.macro
    def class_name(class_path: str) -> str:
        """
        {{ class_name('actor.Actor') }} {{ class_name('pi.actor.Actor') }}
        """
        class_path = _normalize_package_path(class_path)
        class_name = class_path.split('.')[-1]
        return f"[{class_name}]({JAVADOC_URL_PREFIX}/{_to_url(class_path)}.html)"

    @env.macro
    def package_summary(package_path: str) -> str:
        package_path = _normalize_package_path(package_path)
        return f"[{package_path}]({JAVADOC_URL_PREFIX}/{_to_url(package_path)}/package-summary.html)"

    @env.macro
    def demo(relpath: str, hash: str = "main", lines: str | None = None) -> str:
        if lines is None:
            lines = ""
        if not lines.startswith("#") and lines != "":
            lines = '#' + lines
        return f"<small>Zum Java-Code: [demos/{relpath}.java](https://github.com/engine-pi/engine-pi/blob/{hash}/subprojects/demos/src/main/java/demos/{relpath}.java{lines})</small>"

    @env.macro
    def image(relpath: str) -> str:
        return f"![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/{relpath})"
