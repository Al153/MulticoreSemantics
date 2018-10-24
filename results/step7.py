from records import DataBlob
from util import get_most_recent_file_name, plot_same_size_different_lock

if __name__ == "__main__":
    import matplotlib.pyplot as plt
    import json

    fname = get_most_recent_file_name("data", "run_")
    dat_file = open(fname, "r")
    data = json.loads(dat_file.read())
    print(data)
    datablob = DataBlob(data)

    print(datablob.results.keys())

    plot_same_size_different_lock(datablob, 1000,
                                  [
                                      "Write_Unsafe_10k",
                                      "Write_Safe_10k",
                                      "Write_TATAS_10k",
                                      "Write_RW_10k",
                                      "Write_Flags_10k",
                                  ])

    plot_same_size_different_lock(datablob, 1000,
                                  [
                                      "Write_Unsafe_10k",
                                      "Write_Safe_10k",
                                      "Write_TATAS_10k",
                                      "Write_RW_10k",
                                      "Write_Flags_10k",
                                  ], error_bars=False)

    plot_same_size_different_lock(datablob, 1000,
                                  ["Write_Unsafe_100",
                                   "Write_Safe_100",
                                   "Write_TATAS_100",
                                   "Write_RW_100",
                                   "Write_Flags_100"])

    plot_same_size_different_lock(datablob, 1000,
                                  ["Write_Unsafe_100",
                                   "Write_Safe_100",
                                   "Write_TATAS_100",
                                   "Write_RW_100",
                                   "Write_Flags_100"],
                                  error_bars=
                                  False
                                  )

    plt.show()
