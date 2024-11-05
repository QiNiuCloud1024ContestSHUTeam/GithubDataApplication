import yaml
import os
def read_config():
    with open("config.yaml", encoding='utf-8') as yaml_file:
        config = yaml.safe_load(yaml_file)
    return config
config = read_config()
print(config['crawler']['RepoHeaders']['User-Agent'])