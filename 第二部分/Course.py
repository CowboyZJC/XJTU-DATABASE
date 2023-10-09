from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select
import pandas as pd
import time

class XJTU_ehall():
    def __init__(self):
        self.driver = webdriver.Chrome()
        self.button_1 = '/html/body/div[1]/article[1]/article/div[1]/div[1]'   #进入登录界面按钮
        self.button_2 = '//*[@id="account_login"]' #登录按钮
        self.user = '2203613040'      #学生账户
        self.pwd = 'zjc879544688'    #学生密码
        self.page1 = '//*[@id = "widget-recommendAndNew-01"]/div[1]/widget-app-item[2]/div/div/div[2]'   #全校课表查询界面
        self.path = './C040_raw.csv'
        
    def begin(self):
        self.driver.get("http://ehall.xjtu.edu.cn")
        self.driver.find_element(
            by=By.XPATH, value='/html/body/div[1]/article[1]/article/div[1]/div[1]').click()
        self.driver.find_element('name', 'username').send_keys(self.user)
        self.driver.find_element('name', 'pwd').send_keys('zjc879544688')
        time.sleep(2)
        self.driver.find_element(
            by=By.XPATH, value='//*[@id="account_login"]').click()
        time.sleep(2)
        self.driver.find_element(
            by=By.XPATH, value='//*[@id="widget-recommendAndNew-01"]/div[1]/widget-app-item[2]/div/div/div[2]').click()
        time.sleep(2)
        self.driver.switch_to.window(self.driver.window_handles[-1])
        time.sleep(2)

    def get(self):
        self.begin()
        while True:
            id = 0
            while id < 10:           
                CNO = self.driver.find_element(
                    by=By.XPATH, value='//*[@id="row'+str(id)+'qxkcb-index-table"]/td[3]/span').get_attribute('textContent')
                CNAME = self.driver.find_element(
                    by=By.XPATH, value='//*[@id="row'+str(id)+'qxkcb-index-table"]/td[4]/span').get_attribute('textContent')
                PERIOD = self.driver.find_element(
                    by=By.XPATH, value='//*[@id="row'+str(id)+'qxkcb-index-table"]/td[8]').get_attribute('textContent')
                CREDIT = self.driver.find_element(
                    by=By.XPATH, value='//*[@id="row'+str(id)+'qxkcb-index-table"]/td[9]').get_attribute('textContent')
                TEACHER = self.driver.find_element(
                    by=By.XPATH, value='//*[@id="row'+str(id)+'qxkcb-index-table"]/td[10]/span').get_attribute('textContent')
                print(CNO)
                print(CNAME)
                print(PERIOD)
                print(CREDIT)
                print(TEACHER)
                id = id + 1
                self.save(CNO, CNAME, PERIOD, CREDIT, TEACHER)
            time.sleep(2)
            self.driver.find_element(
                by=By.XPATH, value='/html/body/main/article/section/div/div[2]/div[1]/div/div[3]/div[2]/div/div[10]/div/div/div[1]/a[2]').click()
            time.sleep(2)
            self.driver.switch_to.window(self.driver.window_handles[-1])
            time.sleep(2)
        
    def save(self,CNO,CNAME,PERIOD,CREDIT,TEACHER):
        df = pd.read_csv(self.path, dtype={
            "C#": str, "CNAME": str, "PERIOD": float, "CREDIT": float, "TEACHER": str}, index_col=["index"])
        df.loc[len(df)] = {'index': len(df), "C#": CNO, "CNAME": CNAME,"PERIOD": PERIOD, "CREDIT": CREDIT, "TEACHER": TEACHER}
        df.to_csv(self.path)

if __name__ == '__main__':
    crawler = XJTU_ehall()
    crawler.get()




