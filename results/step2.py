import json

from util import *

fname = get_most_recent_file_name("data", "step2_")
with open(fname, "r") as f:
    d = json.loads(f.read())['results']
    results = {int(x): d[x] for x in d}
    xs = [int(k) for k in results]
    xs.sort()
    ys = [np.mean(results[x]) for x in xs]
    stds = [np.std(results[x]) for x in xs]

    from matplotlib import pyplot as plt

    fig = plt.figure()
    ax = fig.add_subplot(111)

    ax.errorbar(xs, ys, yerr=stds, elinewidth=1, linewidth=2, ecolor='b', color='r')
    ax.set_xlabel("Number of Threads")
    ax.set_ylabel("Time to Complete (ms)")

    ax.grid(True)

    plt.show()
