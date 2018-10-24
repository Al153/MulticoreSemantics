from graph import DataBlob
from util import get_most_recent_file_name, plot_array_size_progressions, plot_same_size_different_lock

if __name__ == "__main__":
    import matplotlib.pyplot as plt
    import json

    fname = get_most_recent_file_name("data", "run_")
    dat_file = open("data/ReadOnly", "r")
    data = json.loads(dat_file.read())
    print(data)
    datablob = DataBlob(data)

    plot_same_size_different_lock(datablob, 5000, ["Safe", "TATAS"])

    plt.show()

