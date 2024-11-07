import time
from urllib.request import urlopen
from urllib.request import Request
import json
import requests
import os
import re

# 请求头
TopicHeaders = {
    'Cookie': '_octo=GH1.1.547050398.1730121222; _device_id=73a715dea9188e9052280e9ba8f00a3d; saved_user_sessions=124904356%3AFGDwEe-yLWPAB9iCFsxyTgm-Ij8frauGVZNDils0DyQ_FDi4; user_session=FGDwEe-yLWPAB9iCFsxyTgm-Ij8frauGVZNDils0DyQ_FDi4; __Host-user_session_same_site=FGDwEe-yLWPAB9iCFsxyTgm-Ij8frauGVZNDils0DyQ_FDi4; logged_in=yes; dotcom_user=yizhilsy; color_mode=%7B%22color_mode%22%3A%22auto%22%2C%22light_theme%22%3A%7B%22name%22%3A%22light%22%2C%22color_mode%22%3A%22light%22%7D%2C%22dark_theme%22%3A%7B%22name%22%3A%22dark%22%2C%22color_mode%22%3A%22dark%22%7D%7D; preferred_color_mode=light; tz=Asia%2FShanghai; _gh_sess=wgcGGu1teQcioqHPiWBCx0tVV%2BgI4OSl5S1WSmwI4%2BLlrg7DS2GFaw1Z%2BTJRYq4SKGHp%2Fm4b9ZkIfhQqgACyapyAa8XZtdkOIGF%2Fkk%2BTwIj9ibdWec5%2B8oofaPsQipgRe7Jmfv3LPdqazTY%2BxAj8IujwSpfd%2BSaJqI3QPiZucUbs%2FYPRwWQfcz7qWrb0%2Ftzbd0eeLfAQg75s1uxusz67iprHm%2Fl0rFU6ZOrzMB1Gl4uVPDXP%2FkOwpiCLFvgWUaKEgTAEy2C4T%2BMTFL285JZ1cDY1rM0TPhOkU89zIezfnw6YflOnC%2FE%2FhgRne3QOWKI1mAvd%2BleJNC3EE0nVhVZwC5I%2FQw0%2BuJGikotXbvSc0MWRrHsaD1Nen3gR6WAchiW6--2m9XRat%2B2oaDceNY--FmGSxVluLBuvzl1Y7CQe5A%3D%3D',
    'Sec-Ch-Ua-Platform': '"Windows"',
    'X-Requested-With': 'XMLHttpRequest',
    'Accept-Language': 'zh-CN,zh;q=0.9',
    'Accept': 'text/html',
    'Sec-Ch-Ua': '"Not?A_Brand";v="99", "Chromium";v="130"',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.6723.59 Safari/537.36',
    'Sec-Ch-Ua-Mobile': '?0',
    'Sec-Fetch-Site': 'same-origin',
    'Sec-Fetch-Mode': 'cors',
    'Sec-Fetch-Dest': 'empty',
    'Referer': 'https://github.com/topics',
    'Accept-Encoding': 'gzip, deflate, br'
}

def get_Topics(page):
    # URL
    url = 'https://github.com/topics?page={page}'.format(page=page)
    response = requests.get(url, headers=TopicHeaders)
    # 检查请求是否成功
    if response.status_code == 200:
        return response.text  # 返回 HTML 内容
    else:
        print(f"请求失败，状态码：{response.status_code}")
        return None


RepoHeaders = {'User-Agent': 'Mozilla/5.0',
               'Authorization': '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',
               'Content-Type': 'application/json',
               'Accept': 'application/vnd.github.text-match+json'}

# 获取topic搜索条件下的所有仓库
def get_ReposByTopic(topic):
    targetPath = './Repos/{}/AllReposUnder{}.json'.format(topic, topic)
    # 检测文件是否存在
    if os.path.exists(targetPath):
        print(f"{topic}的仓库信息已存在于:{targetPath}")
        return

    repoItems_list = []
    # 请求topic下的第一页数据
    page = 1
    per_page = 100
    url = 'https://api.github.com/search/repositories?q=topic:{topic}&page={page}&per_page={per_page}&sort=stars&order=desc'.format(
        topic=topic, page=page, per_page=per_page)
    req = Request(url, headers=RepoHeaders)
    response = urlopen(req).read()
    result = json.loads(response.decode())
    # 计算总条数以及总共需要请求的页数
    total_count = result['total_count']
    total_page = total_count // per_page + 1
    # 上一页的最后一个仓库的star数
    starThreshold = result['items'][len(result['items']) - 1]['stargazers_count']
    for item in result['items']:
        repoItems_list.append(item)
    print(f"topic: {topic}下的第{page}页数据已请求，共{total_page}页")
    time.sleep(5)

    for page in range(2, total_page + 1):
        # 后续页数的请求
        urlFolllow = 'https://api.github.com/search/repositories?q=topic:{topic}%20stars:<={starThreshold}&page={page}&per_page={per_page}&sort=stars&order=desc'.format(
            topic=topic, starThreshold=starThreshold, page=1, per_page=per_page)
        req = Request(urlFolllow, headers=RepoHeaders)
        response = urlopen(req).read()
        result = json.loads(response.decode())
        for item in result['items']:
            repoItems_list.append(item)
        print(f"topic: {topic}下的第{page}页数据已请求，共{total_page}页")
        starThreshold = result['items'][len(result['items']) - 1]['stargazers_count']
        time.sleep(5)

    # 封装成字典
    datadictionary = {'total_count': total_count, 'items': repoItems_list}

    # 将repoItems_list中的信息保存到本地json文件中
    with open(targetPath, 'w', encoding='utf-8') as json_file:
        json.dump(datadictionary, json_file, ensure_ascii=False, indent=4)

# 获取所有官方topic的html文件（6页page）
for page in range(1, 7):
    topic_html = get_Topics(page)
    if os.path.exists("./topicPages/topics_page_{}.html".format(page)):
        print("./topicPages/topics_page_{}.html文件已存在".format(page))
        continue
    else:
        with open("./topicPages/topics_page_{}.html".format(page), "w", encoding="utf-8") as f:
            f.write(topic_html)

# 读取爬取的html文件分割出官方topic字符串存入文件中
# 设置文件目录
directory = "./topicPages/"
# 正则表达式模式
pattern = r'<a href="/topics/(.*?)"'
topics_list = []
for page in range(1, 7):
    filename = f"topics_page_{page}.html"
    filepath = os.path.join(directory, filename)
    with open(filepath, "r", encoding="utf-8") as f:
        content = f.read()
        # 查找所有匹配的字符串
        matches = re.findall(pattern, content)
        if matches:
            matchesLen = len(matches)
            for index in range(0, matchesLen, 2):
                topics_list.append(matches[index])

# 检查文件是否存在
if os.path.exists("./topicPages/govTopics.txt"):
    print(f"存储所有官方topic的txt文件已存在于:./topicPages/govTopics.txt")
else:
    # 写入文件
    with open("./topicPages/govTopics.txt", "a", encoding="utf-8") as f:
        count = 1
        for topic in topics_list:
            f.write(str(count) + ":" + topic + "\n")
            count = count + 1
    print(count-1)

# 爬取官方topic下的所有仓库 日期2024/10/31
govTopicFile = "./topicPages/govTopics.txt"
repo_folder = "./Repos"
with open(govTopicFile, "r", encoding="utf-8") as f:
    lines = f.readlines()
    for line in lines:
        govTopic = line.split(":")[1].strip()
        folder_name = f"{govTopic}"
        folder_path = os.path.join(repo_folder, folder_name)
        os.makedirs(folder_path,exist_ok=True)
        print(f"新文件夹 `{folder_path}` 已创建")
        get_ReposByTopic(govTopic)
        print(f"{govTopic}的仓库信息已保存至: {folder_path}")


