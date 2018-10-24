import fnmatch
import os
import re
import matplotlib.pyplot as plt
import numpy as np

from graph import ArraySizeResult, DataBlob


def get_most_recent_file_name(dir: str, prefix):
    def file_names_key(fname: str):
        match = re.match(prefix + "(\\d*)-(\\d*)-(\\d*)T(\\d*)-(\\d*)", fname)
        return (((((match.group(1) * 10 + match.group(2)) * 10 + match.group(3) * 10) + match.group(
            4)) * 10) + match.group(5))

    matches = fnmatch.filter(os.listdir(dir), prefix + "*")
    matches.sort(key=file_names_key, reverse=True)

    return os.path.join(dir, matches[0])


def plot_same_size_different_lock(datablob, size: int, names: list):
    fig1 = plt.figure()
    ax1 = fig1.add_subplot(111)

    handles = []
    print(datablob.results.keys())
    legend_names = []
    for name in names:
        by_array_size = datablob.results[name].result_by_size
        grid = by_array_size[size].results_by_thread_count
        draw_thread_progression(ax1, grid, handles, legend_names, name)
    format_axes(ax1, handles, legend_names)


def plot_array_size_progressions(datablob: DataBlob, name: str):
    fig1 = plt.figure()
    ax1 = fig1.add_subplot(111)
    by_array_size = datablob.results[name].result_by_size
    handles = []
    legend_names = []
    for size in by_array_size:
        draw_thread_progression(ax1, by_array_size[size], handles, legend_names, name + "_ " + size, )
    format_axes(ax1, handles, legend_names)


def draw_thread_progression(ax: plt.axes, grid: ArraySizeResult, handles, legend_names, label: str, fmt: str = "-o",
                            colour: str = None):
    def to_ms(nss):
        return [ns / 1_000_000 for ns in nss]

    zipped = [{"count": count, "mu": np.mean(to_ms(grid[count].values)), "sig": np.std(to_ms(grid[count].values))}
              for count in
              grid]

    zipped.sort(key=lambda triple: triple["count"])

    xs = [t["count"] for t in zipped]
    ys = [t["mu"] for t in zipped]
    std_devs = [t["sig"] for t in zipped]

    (handle, caps, _) = ax.errorbar(xs, ys, yerr=std_devs, fmt=fmt, color=colour, elinewidth=1, linewidth=2, capsize=10)
    handles.append(handle)
    legend_names.append(label)

    for cap in caps:
        cap.set_markeredgewidth(1)


def plot_different_size_different_lock(datablob, sizes: list, names: list):
    fig1 = plt.figure()
    ax1 = fig1.add_subplot(111)

    handles = []
    print(datablob.results.keys())
    legend_names = []
    for name in names:
        for size in sizes:
            by_array_size = datablob.results[name].result_by_size
            grid = by_array_size[size].results_by_thread_count
            draw_thread_progression(ax1, grid, handles, legend_names, name)
    format_axes(ax1, handles, legend_names)


def format_axes(ax: plt.axes, handles: list, legend_names: list):
    ax.set_xlabel("Number of Threads")
    ax.set_ylabel("Time to Complete (ms)")
    plt.legend(handles=handles, labels=legend_names)
