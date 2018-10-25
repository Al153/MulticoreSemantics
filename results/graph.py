from records import DataBlob
from util import *

if __name__ == "__main__":
    import matplotlib.pyplot as plt
    import json

    fname = get_most_recent_file_name("data", "run_")
    dat_file = open("data/ReadOnly", "r")
    data = json.loads(dat_file.read())
    print(data)
    datablob = DataBlob(data)

    # datablob.results["TATAS"].result_by_size[500].results_by_thread_count[5].values
    plot_array_size_progressions(datablob, "Unsafe")

    plot_same_size_different_lock(datablob, 5000, ["Unsafe", "Mutex", "TATAS", "ReaderWriter", "Flags"])
    plt.show()
