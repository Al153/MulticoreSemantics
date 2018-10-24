import re


class DataBlob:
    def __init__(self, json_blob):
        d = json_blob["results"]
        self.results = {key: NamedResults(d[key]) for key in d}


class NamedResults:
    def __init__(self, json_blob):
        self.name = json_blob["testName"]
        d = json_blob["results"]
        self.result_by_size = {convert_array_size(k): ArraySizeResult(d[k]) for k in d}


class ArraySizeResult:
    def __init__(self, json_blob):
        d = json_blob['results']
        self.results_by_thread_count = {convert_thread_Count(k): ThreadCountResult(d[k]) for k in d}


class ThreadCountResult:
    def __init__(self, json_blob):
        self.values = json_blob["results"]


def convert_array_size(s):
    regex = re.compile("ArraySize\\(size=(\d*)\\)")
    return int(regex.match(s).group(1))


def convert_thread_Count(s):
    regex = re.compile("ThreadCount\\(count=(\d*)\\)")
    return int(regex.match(s).group(1))
