import time
from urllib.request import urlopen
from urllib.request import Request
import json

# 根据github api获取数据
def get_results(search, headers, page, stars):
    url = 'https://api.github.com/search/repositories?q={search}%20stars:<={stars}&page={num}&per_page=100&sort=stars' \
          '&order=desc'.format(search=search, num=page, stars=stars)
    req = Request(url, headers=headers)
    response = urlopen(req).read()
    result = json.loads(response.decode())
    return result