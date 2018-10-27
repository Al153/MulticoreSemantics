import json

import matplotlib.pyplot as plt

from records import DataBlob
from util import get_most_recent_file_name, plot_different_size_different_lock


def main(datablob: DataBlob):
    plot_different_size_different_lock("../report/step6_1.png", datablob, [5, 1000, 5000], ["Mutex", "Flags"])
    plot_different_size_different_lock("../report/step6_2.png", datablob, [5, 1000, 5000], ["Mutex", "Flags"],
                                       error_bars=False)


if __name__ == "__main__":
    plt.show()
    fname = get_most_recent_file_name("data", "run_")
    dat_file = open(fname, "r")
    data = json.loads(dat_file.read())
    print(data)
    d = DataBlob(data)
    main(d)
