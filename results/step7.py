from records import DataBlob
from util import get_most_recent_file_name, plot_same_size_different_lock

import matplotlib.pyplot as plt
import json

fname = get_most_recent_file_name("data", "run_")
dat_file = open(fname, "r")
data = json.loads(dat_file.read())
print(data)
datablob = DataBlob(data)

print(datablob.results.keys())

plot_same_size_different_lock(
    "report/step7_1.png",
    datablob,
    1000,
    [
        "Write_Unsafe_10k",
        "Write_Mutex_10k",
        "Write_TATAS_10k",
        "Write_RW_10k",
        "Write_Flags_10k",
    ])

plot_same_size_different_lock(
    "report/step7_2.png",
    datablob,
    1000,
    [
        "Write_Unsafe_10k",
        "Write_Mutex_10k",
        "Write_TATAS_10k",
        "Write_RW_10k",
        "Write_Flags_10k",
    ], error_bars=False)

plot_same_size_different_lock(
    "report/step7_3.png",
    datablob,
    1000,
    [
        "Write_Unsafe_100",
        "Write_Mutex_100",
        "Write_TATAS_100",
        "Write_RW_100",
        "Write_Flags_100"])

plot_same_size_different_lock(
    "report/step7_4.png",
    datablob,
    1000,
    [
        "Write_Unsafe_100",
        "Write_Mutex_100",
        "Write_TATAS_100",
        "Write_RW_100",
        "Write_Flags_100"],
    error_bars=
    False
)
if __name__ == "__main__":
    plt.show()
