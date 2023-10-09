import random
import pandas as pd
import numpy as np


np.set_printoptions(precision=1)
S040 = pd.read_csv('S040_1000.csv')
ar_S040 = np.array(S040)
print(ar_S040[:, 0])

C040 = pd.read_csv('C040_100.csv')
ar_C040 = np.array(C040)
print(ar_C040[:,0])

def make_data():
    key_list = ["S#", "C#", "GRADE"]
    sno_id = np.random.choice(np.arange(len(ar_S040[:,0])))
    sno = ar_S040[:,0][sno_id]
    cno_id = np.random.choice(np.arange(len(ar_C040[:,0])))
    cno = ar_C040[:,0][cno_id]
    grade = -1
    while(grade < 0 or grade >100):
        # 平均分80分的正态分布
        grade = np.random.normal(loc=80, scale=20)
        grade = np.around(grade, 1)
    if random.random() < 0.85:
        info_list = [sno, cno, grade]
    else:
        info_list = [sno, cno, 'null']   
    person_info = dict(zip(key_list, info_list))
    return person_info


df = pd.DataFrame(columns=["S#", "C#", "GRADE"])


for i in range(24000):
    person_info = [make_data()]
    df1 = pd.DataFrame(person_info)
    df = pd.concat([df, df1])

# 将数据写入Excel文件
df.to_csv("SC040_20000_raw.csv", index=None)