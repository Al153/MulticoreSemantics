import re
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


def plot_progressions(datablob, name, array_sizes):
    fig1 = plt.figure()
    ax1 = fig1.add_subplot(111)

    by_array_size = datablob.results[name].result_by_size
    print([key for key in by_array_size])
    for size in array_sizes:
        grid = by_array_size[size].results_by_thread_count
        zipped = [{"count": count, "mu": np.mean(grid[count].values), "sig": np.std(grid[count].values)} for count in
                  grid]

        zipped.sort(key=lambda triple: triple["count"])

        x_axis = [t["count"] for t in zipped]
        means = [t["mu"] for t in zipped]
        std_devs = [t["sig"] for t in zipped]

        ax1.plot(x_axis, means)
        ax1.errorbar(x_axis, means, std_devs, fmt='o')


if __name__ == "__main__":
    import matplotlib.pyplot as plt
    import json

    dat_file = open("data/run_2018-10-22T14-51-41")
    data = json.loads(dat_file.read())
    print(data)
    datablob = DataBlob(data)

    # datablob.results["TATAS"].result_by_size[500].results_by_thread_count[5].values
    plot_progressions(datablob, "Flags", [500, 1000, 2000, 3000, 4000, 5000])

    plt.show()
