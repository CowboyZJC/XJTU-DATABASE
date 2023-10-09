import pandas as pd

# df = pd.read_csv('./C040_raw.csv', dtype={
#                  "C#": str, "CNAME": str, "PERIOD": float, "CREDIT": float, "TEACHER": str}, index_col=["index"])

# df = df.drop_duplicates('C#', keep='first')

# print(df)

# df.to_csv("C040.csv", index=False)

df = pd.read_csv('./SC040_raw.csv', dtype={
                 "S#": str, "C#": str, "GRADE": float})

df = df.drop_duplicates(subset=['S#','C#'], keep='first')

print(df)

df.to_csv("SC040.csv", index=False)
