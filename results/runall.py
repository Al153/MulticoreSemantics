import json

import matplotlib.pyplot as plt

import step3_1
import step3_2
import step3_3
import step4
import step5
import step6
import step7
from records import DataBlob
from util import get_most_recent_file_name

with open(get_most_recent_file_name("data", "run_")) as f:
    datablob = DataBlob(json.loads(f.read()))
    step3_1.main(datablob)
    step3_2.main(datablob)
    step3_3.main(datablob)
    step4.main(datablob)
    step5.main(datablob)
    step6.main(datablob)
    step7.main(datablob)

    plt.show()
