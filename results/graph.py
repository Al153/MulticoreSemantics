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


def plot_array_size_progressions(datablob: DataBlob, name: str, array_sizes: list):
    fig1 = plt.figure()
    ax1 = fig1.add_subplot(111)

    by_array_size = datablob.results[name].result_by_size
    print([key for key in by_array_size])
    handles = []
    legend_names = []
    for size in array_sizes:
        grid = by_array_size[size].results_by_thread_count

        def to_ms(nss):
            return [ns / 1_000_000 for ns in nss]


        zipped = [{"count": count, "mu": np.mean(to_ms(grid[count].values)), "sig": np.std(to_ms(grid[count].values))}
                  for count in
                  grid]

        zipped.sort(key=lambda triple: triple["count"])

        xs = [t["count"] for t in zipped]
        ys = [t["mu"] for t in zipped]
        std_devs = [t["sig"] for t in zipped]

        (handle, caps, _) = ax1.errorbar(xs, ys, yerr=std_devs, fmt="-o", elinewidth=1, linewidth=2, capsize=10)
        handles.append(handle)
        legend_names.append(size)

        for cap in caps:
            cap.set_markeredgewidth(1)
        ax1.set_xlabel("Number of Threads")
        ax1.set_ylabel("Time to Complete (ms)")

    plt.legend(handles=handles, labels=legend_names)


def plot_same_size_different_lock(datablob, size: int, names: list):
    fig1 = plt.figure()
    ax1 = fig1.add_subplot(111)

    handles = []
    print(datablob.results.keys())
    legend_names = []
    for name in names:
        by_array_size = datablob.results[name].result_by_size
        grid = by_array_size[size].results_by_thread_count

        def to_ms(nss):
            return [ns / 1_000_000 for ns in nss]

        zipped = [{"count": count, "mu": np.mean(to_ms(grid[count].values)), "sig": np.std(to_ms(grid[count].values))}
                  for count in
                  grid]

        zipped.sort(key=lambda triple: triple["count"])

        xs = [t["count"] for t in zipped]
        ys = [t["mu"] for t in zipped]
        std_devs = [t["sig"] for t in zipped]

        (handle, caps, _) = ax1.errorbar(xs, ys, yerr=std_devs, fmt="-o", elinewidth=1, linewidth=2, capsize=10)
        handles.append(handle)
        legend_names.append(name)

        for cap in caps:
            cap.set_markeredgewidth(1)
        ax1.set_xlabel("Number of Threads")
        ax1.set_ylabel("Time to Complete (ms)")

    plt.legend(handles=handles, labels=legend_names)


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
