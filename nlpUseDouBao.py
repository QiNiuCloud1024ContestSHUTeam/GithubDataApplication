# 使用豆包大模型api来实现模糊匹配时的nlp
import os
from volcenginesdkarkruntime import Ark
import yaml
def read_config():
    with open("config.yaml", encoding='utf-8') as yaml_file:
        config = yaml.safe_load(yaml_file)
    return config

config = read_config()

# 从config文件中获取配置信息
authorization = config['doubaoAPI']['Headers']['Authorization']
model = config['doubaoAPI']['Body']['model']
role1 = config['doubaoAPI']['Body']['messages']['role1']
content1 = config['doubaoAPI']['Body']['messages']['content1']ad

client = Ark(
    api_key=authorization,
    base_url="https://ark.cn-beijing.volces.com/api/v3",
    timeout=120,
    max_retries=2
)

print("----- standard request -----")
content = input("Enter String\n")
completion = client.chat.completions.create(
    model=model,
    messages = [
        {"role": role1, "content": content1},
        {"role": role2, "content": content},
    ],
)
print(completion.choices[0].message.content)



