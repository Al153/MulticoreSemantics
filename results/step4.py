from records import DataBlob
from util import get_most_recent_file_name, plot_same_size_different_lock

import matplotlib.pyplot as plt
import json

fname = get_most_recent_file_name("data", "run_")
dat_file = open(fname, "r")
data = json.loads(dat_file.read())
print(data)
datablob = DataBlob(data)

plot_same_size_different_lock("../report/step4_1.png", datablob, 5000, ["Mutex", "TATAS"])
if __name__ == "__main__":
    plt.show()
