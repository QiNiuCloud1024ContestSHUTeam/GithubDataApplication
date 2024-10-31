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

if __name__ == '__main__':
    # 构造请求头，github token
    headers = {'User-Agent': 'Mozilla/5.0',
               'Authorization': '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',
               'Content-Type': 'application/json',
               'Accept': 'application/json'}
    search = input("Enter search content: ")
    count = 1
    stars = 1e7
    repos_list = []
    stars_list = []
    for page in range(1, 6):
        results = get_results(search, headers, page, stars)
        for item in results['items']:
            repos_list.append([count, item['name'], item["clone_url"]])
            stars_list.append(item["stargazers_count"])
            count += 1
        print(len(repos_list))
    stars = stars_list[-1]
    with open("./crawlData/top500Repos.txt", "a", encoding="utf-8") as f:
        for i in range(len(repos_list)):
            f.write(str(repos_list[i][0]) + "," + repos_list[i][1] + "," + repos_list[i][2] + ","
                    + str(stars_list[i]) + "\n")

