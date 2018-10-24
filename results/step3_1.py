import numpy as np

from graph import DataBlob, ArraySizeResult
from util import get_most_recent_file_name, draw_thread_progression, plot_array_size_progressions
import matplotlib.pyplot as plt





if __name__ == "__main__":
    import matplotlib.pyplot as plt
    import json

    fname = get_most_recent_file_name("data", "run_")
    dat_file = open("data/ReadOnly", "r")
    data = json.loads(dat_file.read())
    print(data)
    datablob = DataBlob(data)


    plot_array_size_progressions(datablob, "Unsafe")


    plt.show()
