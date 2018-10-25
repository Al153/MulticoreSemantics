import fnmatch
import os
import re

import matplotlib.pyplot as plt
import numpy as np

from records import ArraySizeResult, DataBlob


def get_most_recent_file_name(dir: str, prefix):
    def file_names_key(fname: str):
        match = re.match(prefix + "(\\d*)-(\\d*)-(\\d*)T(\\d*)-(\\d*)", fname)
        return (((((match.group(1) * 10 + match.group(2)) * 10 + match.group(3) * 10) + match.group(
            4)) * 10) + match.group(5))

    matches = fnmatch.filter(os.listdir(dir), prefix + "*")
    matches.sort(key=file_names_key, reverse=True)

    return os.path.join(dir, matches[0])


def plot_same_size_different_lock(fname: str, datablob, size: int, names: list, error_bars=True):
    fig1 = plt.figure()
    ax1 = fig1.add_subplot(111)
    ax1.grid(True)
    handles = []
    print(datablob.results.keys())
    legend_names = []
    for name in names:
        by_array_size = datablob.results[name].result_by_size
        grid = by_array_size[size]
        draw_thread_progression(ax1, grid, handles, legend_names, name, error_bars)
    format_axes(ax1, handles, legend_names)
    ax1.savefig(fname, bbox_inches='tight')


def plot_array_size_progressions(fname: str, datablob: DataBlob, name: str, error_bars=True):
    fig1 = plt.figure()
    ax1 = fig1.add_subplot(111)
    ax1.grid(True)
    by_array_size = datablob.results[name].result_by_size
    handles = []
    legend_names = []
    for size in by_array_size:
        draw_thread_progression(ax1, by_array_size[size], handles, legend_names, name + "_ " + str(size), error_bars)
    format_axes(ax1, handles, legend_names)
    ax1.savefig(fname, bbox_inches='tight')


def draw_thread_progression(ax: plt.axes, results: ArraySizeResult, handles, legend_names, label: str, error_bars: bool,
                            line_style: str = "-", point_style: str = "o", colour: str = None):
    def to_ms(nss):
        return [ns / 1_000_000 for ns in nss]

    grid = results.results_by_thread_count
    zipped = [{"count": count, "mu": np.mean(to_ms(grid[count].values)), "sig": np.std(to_ms(grid[count].values))}
              for count in
              grid]

    zipped.sort(key=lambda triple: triple["count"])

    xs = [t["count"] for t in zipped]
    ys = [t["mu"] for t in zipped]
    std_devs = [t["sig"] for t in zipped]
    fmt = line_style + point_style
    if error_bars:

        (handle, caps, _) = ax.errorbar(xs, ys, yerr=std_devs, fmt=fmt, color=colour, elinewidth=1, linewidth=2,
                                        capsize=10)
        for cap in caps:
            cap.set_markeredgewidth(1)
    else:
        handle, = ax.plot(xs, ys, fmt, color=colour, linewidth=2)
    handles.append(handle)
    legend_names.append(label)


def plot_different_size_different_lock(fname: str, datablob, sizes: list, names: list, error_bars: bool = True):
    fig1 = plt.figure()
    ax1 = fig1.add_subplot(111)
    ax1.grid(True)

    handles = []
    print(datablob.results.keys())
    legend_names = []
    for name in names:
        for size in sizes:
            by_array_size = datablob.results[name].result_by_size
            grid = by_array_size[size]
            draw_thread_progression(ax1, grid, handles, legend_names, name + "_" + str(size), error_bars)
    format_axes(ax1, handles, legend_names)
    ax1.savefig(fname, bbox_inches='tight')


def format_axes(ax: plt.axes, handles: list, legend_names: list):
    ax.set_xlabel("Number of Threads")
    ax.set_ylabel("Time to Complete (ms)")
    plt.legend(handles=handles, labels=legend_names)
