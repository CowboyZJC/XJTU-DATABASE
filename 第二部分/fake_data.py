from faker import Faker
import pandas as pd
import random
import numpy as np

def get_data():
    key_list = ["S#", "SNAME", "SEX", "BDATE", "HEIGHT", "DORM"]
    # 用0补齐，默认位数8位，d代表整数
    # 注意作为CK不能重复
    num = random.random()
    if num < 0.25:
        sno = "222"+"%07d" % random.randint(0, 9999999)
    elif num >=0.25 and num < 0.50:
        sno = "221"+"%07d" % random.randint(0, 9999999)
    elif num >=0.50 and num <0.75:
        sno = "220"+"%07d" % random.randint(0, 9999999)
    elif num >=0.75 and num < 1.0:
        sno = "219"+"%07d" % random.randint(0, 9999999)
    # 按照男女性别的不同生成姓名
    sex = random.choice(['男', '女'])
    if sex == '男':
        sname = fake.name_male()
    if sex == '女':
        sname = fake.name_female()
    # 限制本科学生的年龄范围为16到24岁
    id_card = fake.ssn(min_age = 16, max_age = 24)
    # 身份号7到15位是出生年月日
    bdate = id_card[6:10] + "-" + id_card[10:12] + "-" + id_card[12:14]
    # 查阅资料得我国男性平均身高1.69m，女性平均身高1.58m，按照正态分布生成身高
    height = 0
    if sex == '男':
        while(height < 1.40 or height > 2.10):
            height = np.random.normal(loc = 1.69, scale = 0.1)
            height = np.around(height, 2)
    if sex == '女':
        while(height < 1.30 or height > 1.90):
            height = np.random.normal(loc = 1.58, scale = 0.1)
            height = np.around(height, 2)
    direction = random.choice(['东', '西'])
    dorm = direction + str(random.randint(1, 22)) + "舍" + str(random.randint(1, 9)) + str("%02d" % random.randint(1, 30))
    info_list = [sno, sname, sex, bdate, height, dorm]
    person_info = dict(zip(key_list, info_list))
    return person_info

def get_data_foreign():
    key_list = ["S#", "SNAME", "SEX", "BDATE", "HEIGHT", "DORM"]
    # 用0补齐，默认位数6位，d代表整数
    # 注意作为CK不能重复
    num = random.random()
    if num < 0.25:
        sno = "222"+"%05d" % random.randint(0, 99999)
    elif num >= 0.25 and num < 0.50:
        sno = "221"+"%05d" % random.randint(0, 99999)
    elif num >= 0.50 and num < 0.75:
        sno = "220"+"%05d" % random.randint(0, 99999)
    elif num >= 0.75 and num < 1.0:
        sno = "219"+"%05d" % random.randint(0, 99999)
    sno = 'JL'+ sno
    # 按照男女性别的不同生成姓名
    sex = random.choice(['男', '女'])
    if sex == '男':
        sname = fake.name_male()
    if sex == '女':
        sname = fake.name_female()
    # 美国学生的出生时间改用随机时间生成，仍是16到24岁
    bdate = '00000000'
    while(int(bdate[0:4]) < 1998 or int(bdate[0:4]) > 2006):
        bdate = fake.date(pattern = "%Y-%m-%d")
    # 查阅资料得美国男性平均身高1.77m，女性平均身高1.69m，按照正态分布生成身高
    height = 0
    if sex == '男':
        while(height < 1.40 or height > 2.10):
            height = np.random.normal(loc = 1.77, scale = 0.1)
            height = np.around(height, 2)
    if sex == '女':
        while(height < 1.30 or height > 1.90):
            height = np.random.normal(loc = 1.69, scale = 0.1)
            height = np.around(height, 2)
    direction = random.choice(['东', '西'])
    dorm = direction + str(random.randint(1, 22)) + "舍" + str(random.randint(1, 9)) + str("%02d" % random.randint(1, 30))
    info_list = [sno, sname, sex, bdate, height, dorm]
    person_info = dict(zip(key_list, info_list))
    return person_info

df = pd.DataFrame(columns=["S#", "SNAME", "SEX", "BDATE", "HEIGHT", "DORM"])

fake = Faker(["zh_CN"])
Faker.seed(0)
for i in range(900):
    person_info = [get_data()]
    df1 = pd.DataFrame(person_info)
    df = pd.concat([df, df1])

# 加入100条留学生的数据
fake = Faker(["en_US"])
Faker.seed(0)
for i in range(100):
    person_info = [get_data_foreign()]
    df1 = pd.DataFrame(person_info)
    df = pd.concat([df, df1])

# 将数据写入Excel文件
df.to_csv("S040_1000.csv", index=False)