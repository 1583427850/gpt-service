# gpt-service

## 使用
引入项目，配置yml文件里面的apikey即可

通过调用http://localhost:9090/send 并请求体携带
```
[
    {
        "role": "user",
        "content": "你的问题"
    },
]
```
**如果要连续对话**
那么需要将每次请求成功返回的结果带上，然后再添加上问题
```
[
    {
        "role": "user",
        "content": "你的问题1"
    },
    {
        "role": "assistant",
        "content": "gpt回答1"
    },
    {
        "role": "user",
        "content": "你的问题2"
    },
]
```
