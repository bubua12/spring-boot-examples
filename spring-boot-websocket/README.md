关于 WebSocket
Q：这里的连接建立以及断开，是什么触发的？实际生产中帮我举几个例子，靠前端打开关闭页面来触发？？？

好问题，实际生产中触发方式比"开页面关页面"丰富得多。

### 一、连接建立的触发方式

#### 1. 页面加载时（最常见的前端触发）

```javascript
// 用户打开页面，JS 执行到这里就自动连接了
const ws = new WebSocket('ws://your-server/ws/chat');
```

#### 2. 用户登录后才连接（推荐做法）

```javascript
// 不是打开页面就连，而是登录成功后才连
async function login(username, password) {
    const token = await http.post('/auth/login', { username, password });
    // 登录成功后，带着 token 建立 WebSocket
    const ws = new WebSocket(`ws://your-server/ws/chat?token=${token}`);
}
```

#### 3. 前端主动重连（断线恢复）

```javascript
const ws = new WebSocket('ws://your-server/ws/chat');

ws.onclose = () => {
    // 连接断了，3 秒后自动重连
    setTimeout(() => {
        ws = new WebSocket('ws://your-server/ws/chat');
    }, 3000);
};
```

#### 4. 后端服务调用触发（服务间通信）

```java
// 不一定是浏览器，Java 后端之间也能用 WebSocket 互连
WebSocketClient client = new WebSocketClient();
client.connect("ws://another-service/ws/notify");
client.send("订单已创建");
```

---

### 二、连接断开的触发方式

远不止关页面这么简单，**实际生产中大量断开是意外的**：

| 触发场景 | 谁触发的 | 可预测？ |
|---------|---------|---------|
| 用户关闭浏览器标签页 | 用户主动 | 是 |
| 用户刷新页面（F5） | 用户主动 | 是 |
| 用户切换网络（WiFi → 4G） | 系统/网络 | 否 |
| 手机锁屏 / App 切后台 | 操作系统 | 部分 |
| 用户长时间无操作，Nginx 超时 | 中间件 | 是 |
| 服务器重启 | 运维 | 是 |
| 网络抖动 / 弱网 | 运营商 | 否 |
| Nginx / 网关断开长连接 | 中间件 | 是 |

---

### 三、真实生产场景举例

#### 场景1：外卖 App 订单推送

```
用户打开外卖App
    ↓
App 连接 WebSocket: ws://api.eshop.com/ws/order?userId=123
    ↓
用户下单成功
    ↓
后端通过 WebSocket 推送: "骑手已接单"
    ↓
3 分钟后推送: "骑手距您还有 500 米"
    ↓
用户锁屏了 → WebSocket 可能断开
    ↓
用户重新打开App → 自动重连 → 继续收消息
```

#### 场景2：在线协作编辑（类似飞书文档）

```
A 打开文档 → 建立 WebSocket 连接
B 打开同一个文档 → 建立 WebSocket 连接

A 输入"你好" → 消息通过 WebSocket 发到服务器 → 服务器广播给 B
B 实时看到 A 在打字（光标位置都能同步）

A 关掉文档 → 连接断开 → B 的界面上 A 的光标消失
```

#### 场景3：后台管理系统通知

```
管理员在后台管理系统挂着
    ↓
有新用户注册 → 后端通过 WebSocket 推送: "新用户 xxx 已注册"（右上角弹通知）
    ↓
管理员长时间没操作，浏览器标签页在后台
    ↓
Nginx 默认 60s 没数据就断开 → 触发 afterConnectionClosed
    ↓
前端检测到断开 → 自动重连 → afterConnectionEstablished
    ↓
之后又能收到通知了
```

---

### 四、生产中必须处理的问题

因为连接**随时可能断**，所以实际项目里还需要加心跳和重连：

**后端心跳检测（服务端发现死连接）：**
```java
@Override
public void afterConnectionEstablished(WebSocketSession session) {
    sessions.add(session);
    // 每 30 秒发一次 ping，对方必须回 pong，否则认为断了
    session.sendMessage(new PingMessage());
}
```

**前端自动重连（客户端主动恢复）：**
```javascript
function connect() {
    const ws = new WebSocket('ws://your-server/ws/chat');
    
    ws.onopen = () => console.log('已连接');
    
    ws.onclose = () => {
        console.log('连接断开，3秒后重连...');
        setTimeout(connect, 3000);  // 递归调用自己，自动重连
    };
}
connect();
```

---

### 五、总结

```
触发连接建立：不只开页面，还有登录、手动重连、服务间调用
触发连接断开：不只关页面，还有网络抖动、超时、锁屏、服务器重启

生产中的核心理念：
    连接随时可能断 → 所以要有 心跳检测 + 自动重连
    不是建立一次就永久可靠 → 而是断了能自动恢复
```


---





















