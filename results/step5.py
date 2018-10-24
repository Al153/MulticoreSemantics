from records import DataBlob
from util import get_most_recent_file_name, plot_different_size_different_lock

if __name__ == "__main__":
    import matplotlib.pyplot as plt
    import json

    fname = get_most_recent_file_name("data", "run_")
    dat_file = open(fname, "r")
    data = json.loads(dat_file.read())
    print(data)
    datablob = DataBlob(data)

    plot_different_size_different_lock(datablob, [5, 1000, 5000], ["Safe", "ReaderWriter"])
    plt.show()

    plot_different_size_different_lock(datablob, [5, 1000, 5000], ["Safe", "ReaderWriter"], error_bars=False)
    plt.show()
