import re
from util import *
import numpy as np


class DataBlob:
    def __init__(self, json_blob):
        d = json_blob["results"]
        self.results = {key: NamedResults(d[key]) for key in d}


class NamedResults:
    def __init__(self, json_blob):
        self.name = json_blob["testName"]
        d = json_blob["results"]
        self.result_by_size = {convert_array_size(k): ArraySizeResult(d[k]) for k in d}


def convert_array_size(s):
    regex = re.compile("ArraySize\\(size=(\d*)\\)")
    return int(regex.match(s).group(1))


def convert_thread_Count(s):
    regex = re.compile("ThreadCount\\(count=(\d*)\\)")
    return int(regex.match(s).group(1))


class ArraySizeResult:
    def __init__(self, json_blob):
        d = json_blob['results']
        self.results_by_thread_count = {convert_thread_Count(k): ThreadCountResult(d[k]) for k in d}


class ThreadCountResult:
    def __init__(self, json_blob):
        self.values = json_blob["results"]



if __name__ == "__main__":
    import matplotlib.pyplot as plt
    import json

    fname = get_most_recent_file_name("data", "run_")
    dat_file = open("data/ReadOnly", "r")
    data = json.loads(dat_file.read())
    print(data)
    datablob = DataBlob(data)

    # datablob.results["TATAS"].result_by_size[500].results_by_thread_count[5].values
    plot_array_size_progressions(datablob, "Unsafe", [5, 1000, 5000])

    plot_same_size_different_lock(datablob, 5000, ["Unsafe", "Safe", "TATAS", "ReaderWriter", "Flags"])
    plt.show()
