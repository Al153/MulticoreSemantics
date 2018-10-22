import matplotlib.pyplot as plt
import json

with open("data/run_2018-10-22T14-51-41", "r") as dat_file:
    data = json.loads(dat_file.read())
    print(data)


class DataBlob:
    pass