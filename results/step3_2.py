from records import DataBlob
from util import get_most_recent_file_name, plot_array_size_progressions

import matplotlib.pyplot as plt
import json

fname = get_most_recent_file_name("data", "run_")
dat_file = open(fname, "r")
data = json.loads(dat_file.read())
print(data)
datablob = DataBlob(data)

plot_array_size_progressions("../report/step3_2.png", datablob, "Mutex")

if __name__ == "__main__":
    plt.show()
