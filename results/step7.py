from graph import DataBlob
from util import get_most_recent_file_name, plot_array_size_progressions, plot_same_size_different_lock, \
    plot_different_size_different_lock

if __name__ == "__main__":
    import matplotlib.pyplot as plt
    import json

    fname = get_most_recent_file_name("data", "run_")
    dat_file = open("data/ReadOnly", "r")
    data = json.loads(dat_file.read())
    print(data)
    datablob = DataBlob(data)

    plot_same_size_different_lock(datablob, 1000,
                                  [
                                      "Write_Unsafe_10k",
                                      "Write_Unsafe_100",
                                      "Write_Safe_10k",
                                      "Write_Safe_100",

                                      "Write_TATAS_10k",
                                      "Write_TATAS_100",
                                      "Write_RW_10k",
                                      "Write_RW_100"


                                      "Write_Flags_10k",
                                      "Write_Flags_100"
                                  ])

    plt.show()

