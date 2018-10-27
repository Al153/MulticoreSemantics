import json

import matplotlib.pyplot as plt

from records import DataBlob
from util import get_most_recent_file_name, plot_same_size_different_lock


def main(datablob: DataBlob):
    plot_same_size_different_lock("../report/step3_3.png", datablob, 5000, ["Mutex", "Unsafe"])


if __name__ == "__main__":
    fname = get_most_recent_file_name("data", "run_")
    dat_file = open(fname, "r")
    data = json.loads(dat_file.read())
    print(data)
    d = DataBlob(data)
    main(d)
    plt.show()
