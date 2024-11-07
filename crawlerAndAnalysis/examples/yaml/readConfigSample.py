# 从本地config.yaml文件中读取包含敏感数据的配置
import yaml
import os

os.chdir(os.path.dirname(__file__))
def read_config():
    with open("config.yaml", encoding='utf-8') as yaml_file:
        config = yaml.safe_load(yaml_file)
    return config

config = read_config()
print(config)
print(config['defaults'])
