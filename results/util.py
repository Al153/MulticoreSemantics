import fnmatch
import os
import re


def get_most_recent_file_name(dir: str, prefix):
    def file_names_key(fname: str):
        match = re.match(prefix + "(\\d*)-(\\d*)-(\\d*)T(\\d*)-(\\d*)", fname)
        return (((((match.group(1) * 10 + match.group(2)) * 10 + match.group(3) * 10) + match.group(
            4)) * 10) + match.group(5))

    matches = fnmatch.filter(os.listdir(dir), prefix + "*")
    matches.sort(key=file_names_key, reverse=True)

    return os.path.join(dir, matches[0])