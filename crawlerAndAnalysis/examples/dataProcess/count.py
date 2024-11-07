import os
import json
import time
import re
import numpy as np
import matplotlib.pyplot as plt
import requests
from urllib.request import urlopen
from urllib.request import Request
from urllib.error import HTTPError, URLError
from datetime import datetime
import random

current_directory = os.path.dirname(os.path.abspath(__file__))
print(current_directory)
os.chdir(os.path.dirname(__file__))

with open("./Repos/operating-system/AllReposUnderoperating-system.json", 'r') as file:
    data = json.load(file)  # 将 JSON 数据解析为 Python 对象
    size = min(10, data['total_count'])   # 限制每个topic下的repo的最大数量为 10
    repo_data_list = data['items'][0:size]
    # 计算所有仓库的总star数，fork数，open_issues数
    ALLSTARS = 0
    ALLFORKS = 0
    ALLOPENISSUES = 0
    for i in range(0, size, 1):
        ALLSTARS += repo_data_list[i]['stargazers_count']
        ALLFORKS += repo_data_list[i]['forks_count']
        ALLOPENISSUES += repo_data_list[i]['open_issues_count']
    for repo in repo_data_list:
        print(repo['id'])
        print(repo['name'])
        print(f'starrate: {repo["stargazers_count"] / ALLSTARS * 10}')
        print(f'forkrate: {repo["forks_count"] / ALLFORKS * 10}')
        print(f'open_issues_rate: {repo["open_issues_count"] / ALLOPENISSUES * 10}')